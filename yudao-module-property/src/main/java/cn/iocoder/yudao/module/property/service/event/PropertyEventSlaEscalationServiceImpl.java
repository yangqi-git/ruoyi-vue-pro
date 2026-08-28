package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyWorkOrderMapper;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PropertyEventSlaEscalationServiceImpl implements PropertyEventSlaEscalationService {

    @Resource
    private PropertyEventMapper eventMapper;
    @Resource
    private PropertyWorkOrderMapper workOrderMapper;
    @Resource
    private PropertyEventTimelineMapper timelineMapper;
    @Resource
    private PropertyServiceRecoveryService recoveryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int processEscalations() {
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        for (PropertyEventDO event : eventMapper.selectSlaEscalationCandidates()) {
            StageClock clock = currentClock(event);
            if (clock == null || clock.deadline() == null
                    || ("CLOSE".equals(clock.stage()) && Boolean.TRUE.equals(event.getSlaPaused()))) {
                continue;
            }
            int desiredLevel = calculateLevel(event, clock.deadline(), now);
            int currentLevel = Objects.equals(clock.stage(), event.getSlaEscalationStage())
                    && event.getSlaEscalationLevel() != null ? event.getSlaEscalationLevel() : 0;
            if (desiredLevel <= currentLevel) {
                continue;
            }
            if (event.getStatus() == PropertyEventStatusEnum.PENDING_ACCEPT.getStatus() && desiredLevel == 2) {
                autoReturnPendingAccept(event, clock, now);
            } else {
                escalate(event, clock, desiredLevel, now);
            }
            count++;
        }
        return count;
    }

    private int calculateLevel(PropertyEventDO event, LocalDateTime deadline, LocalDateTime now) {
        if (!now.isBefore(deadline)) {
            return 2;
        }
        int threshold = event.getSlaEscalationMinutes() == null ? 15 : event.getSlaEscalationMinutes();
        return !now.isBefore(deadline.minusMinutes(threshold)) ? 1 : 0;
    }

    private void escalate(PropertyEventDO event, StageClock clock, int level, LocalDateTime now) {
        int fromStatus = event.getStatus();
        event.setSlaEscalationStage(clock.stage());
        event.setSlaEscalationLevel(level);
        event.setSlaEscalatedTime(now);
        if (eventMapper.updateById(event) == 0) {
            return;
        }
        appendTimeline(event, "SLA_ESCALATE", fromStatus, fromStatus,
                level == 1 ? "SLA 即将超时" : "SLA 已超时",
                clock.stage() + " 截止时间 " + clock.deadline());
        if (level == 2) {
            recoveryService.createAutomaticTask(event, 1, clock.stage() + " SLA 已超时");
        }
    }

    private void autoReturnPendingAccept(PropertyEventDO event, StageClock clock, LocalDateTime now) {
        int fromStatus = event.getStatus();
        event.setStatus(PropertyEventStatusEnum.PENDING_DISPATCH.getStatus());
        event.setResponsibleDeptId(null);
        event.setResponsibleUserId(null);
        event.setSlaEscalationStage(clock.stage());
        event.setSlaEscalationLevel(2);
        event.setSlaEscalatedTime(now);
        if (eventMapper.updateById(event) == 0) {
            return;
        }
        PropertyWorkOrderDO workOrder = workOrderMapper.selectLatestByEventId(event.getId());
        if (workOrder != null && workOrder.getStatus() == PropertyEventStatusEnum.PENDING_ACCEPT.getStatus()) {
            workOrder.setStatus(97);
            workOrder.setRejectReason("接单 SLA 超时，系统自动退回调度池");
            workOrderMapper.updateById(workOrder);
        }
        appendTimeline(event, "SLA_AUTO_RETURN", fromStatus, event.getStatus(),
                "接单 SLA 超时", "已自动升级并退回调度池，原 SLA 不重置");
        recoveryService.createAutomaticTask(event, 1, clock.stage() + " SLA 已超时并退回调度池");
    }

    private StageClock currentClock(PropertyEventDO event) {
        int status = event.getStatus();
        if (event.getRespondedTime() == null && status == PropertyEventStatusEnum.PENDING_CONFIRM.getStatus()) {
            return new StageClock("RESPONSE", event.getResponseDeadline());
        }
        if (event.getArrivedTime() == null && (status == PropertyEventStatusEnum.PENDING_ACCEPT.getStatus()
                || status == PropertyEventStatusEnum.ACCEPTED.getStatus())) {
            return new StageClock("ARRIVAL", event.getArrivalDeadline());
        }
        if (event.getRecoveredTime() == null && (status == PropertyEventStatusEnum.ARRIVED.getStatus()
                || status == PropertyEventStatusEnum.PROCESSING.getStatus()
                || status == PropertyEventStatusEnum.WAITING_COLLABORATION.getStatus()
                || status == PropertyEventStatusEnum.REOPENED.getStatus())) {
            return new StageClock("RECOVERY", event.getRecoveryDeadline());
        }
        if (event.getClosedTime() == null && status == PropertyEventStatusEnum.PENDING_ACCEPTANCE.getStatus()) {
            return new StageClock("CLOSE", event.getCloseDeadline());
        }
        return null;
    }

    private void appendTimeline(PropertyEventDO event, String action, Integer fromStatus, Integer toStatus,
            String reason, String detail) {
        timelineMapper.insert(PropertyEventTimelineDO.builder()
                .eventId(event.getId()).projectId(event.getProjectId()).action(action)
                .fromStatus(fromStatus).toStatus(toStatus).reason(reason).detail(detail)
                .eventVersion(event.getVersion()).build());
    }

    private record StageClock(String stage, LocalDateTime deadline) {
    }
}

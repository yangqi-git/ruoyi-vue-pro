package cn.iocoder.yudao.module.property.service.event;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventDetailRespVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventDispatchReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventEvidenceCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventMergeReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSplitReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventStatsRespVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventTransferReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventEvidenceDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaPauseDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventRelationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventEvidenceMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventCategoryMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaPauseMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaRuleMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventRelationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyServiceRecoveryTaskMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventNotificationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyWorkOrderMapper;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_ACCEPTANCE_REQUIRED;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CONCURRENT_UPDATE;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_EVIDENCE_REQUIRED;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOTIFICATION_PENDING;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_MERGE_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SPLIT_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CATEGORY_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_PAUSE_REASON_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_PAUSE_STATE_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_STATUS_TRANSITION_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_WORK_ORDER_NOT_EXISTS;

@Service
@Validated
public class PropertyEventServiceImpl implements PropertyEventService {

    private static final DateTimeFormatter NUMBER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Resource
    private PropertyEventMapper eventMapper;
    @Resource
    private PropertyWorkOrderMapper workOrderMapper;
    @Resource
    private PropertyEventTimelineMapper timelineMapper;
    @Resource
    private PropertyEventEvidenceMapper evidenceMapper;
    @Resource
    private PropertyEventCategoryMapper categoryMapper;
    @Resource
    private PropertyEventSlaPauseMapper slaPauseMapper;
    @Resource
    private PropertyEventSlaRuleMapper slaRuleMapper;
    @Resource
    private PropertyEventRelationMapper relationMapper;
    @Resource
    private PropertyServiceRecoveryTaskMapper recoveryTaskMapper;
    @Resource
    private PropertyEventNotificationMapper notificationMapper;
    @Resource
    private PropertyServiceRecoveryService recoveryService;
    @Resource
    private PropertyEventNotificationService notificationService;
    @Resource
    private PropertyProjectService projectService;
    @Resource
    private PropertyEventSlaService slaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEvent(PropertyEventCreateReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (StrUtil.isNotBlank(reqVO.getSourceSystem()) && StrUtil.isNotBlank(reqVO.getSourceRecordId())) {
            PropertyEventDO existing = eventMapper.selectBySource(reqVO.getSourceSystem(), reqVO.getSourceRecordId());
            if (existing != null) {
                return existing.getId();
            }
        }
        LocalDateTime now = LocalDateTime.now();
        PropertyEventDO event = BeanUtils.toBean(reqVO, PropertyEventDO.class);
        event.setEventNo(generateNumber("EVT"));
        event.setStatus(PropertyEventStatusEnum.PENDING_CONFIRM.getStatus());
        PropertyEventSlaService.SlaSnapshot sla = slaService.calculateSnapshot(reqVO.getProjectId(),
                reqVO.getCategoryCode(), reqVO.getUrgencyLevel(), now);
        event.setResponseDeadline(sla.responseDeadline());
        event.setArrivalDeadline(sla.arrivalDeadline());
        event.setRecoveryDeadline(sla.recoveryDeadline());
        event.setCloseDeadline(sla.closeDeadline());
        event.setSlaRuleId(sla.ruleId());
        event.setSlaRuleVersion(sla.ruleVersion());
        event.setSlaArrivalMinutes(sla.arrivalMinutes());
        event.setSlaStartTime(now);
        event.setSlaEscalationMinutes(sla.escalationMinutes());
        event.setSlaEscalationLevel(0);
        event.setSlaPaused(false);
        event.setSlaPausedSeconds(0L);
        event.setReopenCount(0);
        event.setVersion(0);
        eventMapper.insert(event);
        appendTimeline(event, "CREATE", null, event.getStatus(), null, "事件受理");
        notificationService.enqueueStatusNotification(event, "RECEIVED",
                "您的诉求已登记，事件编号 " + event.getEventNo() + "，等待工作人员确认。");
        return event.getId();
    }

    @Override
    public PageResult<PropertyEventDO> getEventPage(PropertyEventPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return eventMapper.selectPage(reqVO);
    }

    @Override
    public PropertyEventDetailRespVO getEventDetail(Long id, Long projectId) {
        PropertyEventDO event = validateEvent(id, projectId);
        return PropertyEventDetailRespVO.builder()
                .event(event)
                .workOrders(workOrderMapper.selectListByEventId(id))
                .evidences(evidenceMapper.selectListByEventId(id))
                .slaPauses(slaPauseMapper.selectListByEventId(id))
                .relations(relationMapper.selectListByEventId(id))
                .recoveryTasks(recoveryTaskMapper.selectListByEventId(id))
                .notifications(notificationMapper.selectListByEventId(id))
                .timeline(timelineMapper.selectListByEventId(id))
                .build();
    }

    @Override
    public PropertyEventStatsRespVO getEventStats(Long projectId) {
        projectService.validateProject(projectId);
        return PropertyEventStatsRespVO.builder()
                .pendingConfirm(eventMapper.selectCountByStatus(projectId, List.of(0)))
                .pendingDispatch(eventMapper.selectCountByStatus(projectId, List.of(10, 20)))
                .pendingAcceptance(eventMapper.selectCountByStatus(projectId, List.of(70)))
                .processing(eventMapper.selectCountByStatus(projectId, List.of(30, 40, 50, 60, 90)))
                .overdue(eventMapper.selectOverdueCount(projectId, LocalDateTime.now()))
                .majorRisk(eventMapper.selectMajorRiskCount(projectId))
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispatchEvent(PropertyEventDispatchReqVO reqVO) {
        PropertyEventDO event = validateEvent(reqVO.getId(), reqVO.getProjectId());
        validateVersion(event, reqVO.getVersion());
        validateTransition(event, PropertyEventActionEnum.DISPATCH);
        int fromStatus = event.getStatus();
        event.setStatus(PropertyEventStateMachine.nextStatus(fromStatus, PropertyEventActionEnum.DISPATCH));
        event.setResponsibleDeptId(reqVO.getAssignedDeptId());
        event.setResponsibleUserId(reqVO.getAssigneeUserId());
        if (event.getFirstDispatchedTime() == null) {
            LocalDateTime firstDispatchedTime = LocalDateTime.now();
            event.setFirstDispatchedTime(firstDispatchedTime);
            int arrivalMinutes = event.getSlaArrivalMinutes() == null ? 120 : event.getSlaArrivalMinutes();
            event.setArrivalDeadline(firstDispatchedTime.plusMinutes(arrivalMinutes));
        }
        resetEscalation(event);
        updateEvent(event);

        PropertyWorkOrderDO workOrder = PropertyWorkOrderDO.builder()
                .workOrderNo(generateNumber("WO"))
                .eventId(event.getId())
                .projectId(event.getProjectId())
                .assignedDeptId(reqVO.getAssignedDeptId())
                .assigneeUserId(reqVO.getAssigneeUserId())
                .supplierId(reqVO.getSupplierId())
                .status(PropertyEventStatusEnum.PENDING_ACCEPT.getStatus())
                .plannedArrivalTime(reqVO.getPlannedArrivalTime())
                .version(0)
                .build();
        workOrderMapper.insert(workOrder);
        appendTimeline(event, PropertyEventActionEnum.DISPATCH.name(), fromStatus, event.getStatus(),
                reqVO.getReason(), "派发工单 " + workOrder.getWorkOrderNo());
        notificationService.enqueueStatusNotification(event, "DISPATCHED",
                "事件 " + event.getEventNo() + " 已安排工作人员处理。");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferEvent(PropertyEventTransferReqVO reqVO) {
        PropertyEventDO event = validateEvent(reqVO.getId(), reqVO.getProjectId());
        validateVersion(event, reqVO.getVersion());
        validateTransition(event, PropertyEventActionEnum.TRANSFER);
        PropertyWorkOrderDO previous = workOrderMapper.selectLatestByEventId(event.getId());
        if (previous == null) {
            throw exception(EVENT_WORK_ORDER_NOT_EXISTS);
        }
        int fromStatus = event.getStatus();
        previous.setStatus(96);
        previous.setTransferReason(reqVO.getReason());
        if (workOrderMapper.updateById(previous) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
        event.setStatus(PropertyEventStateMachine.nextStatus(fromStatus, PropertyEventActionEnum.TRANSFER));
        event.setResponsibleDeptId(reqVO.getAssignedDeptId());
        event.setResponsibleUserId(reqVO.getAssigneeUserId());
        event.setBlockReason(null);
        resetEscalation(event);
        updateEvent(event);
        PropertyWorkOrderDO next = PropertyWorkOrderDO.builder()
                .workOrderNo(generateNumber("WO"))
                .eventId(event.getId()).parentWorkOrderId(previous.getId())
                .projectId(event.getProjectId()).assignedDeptId(reqVO.getAssignedDeptId())
                .assigneeUserId(reqVO.getAssigneeUserId()).supplierId(reqVO.getSupplierId())
                .status(PropertyEventStatusEnum.PENDING_ACCEPT.getStatus())
                .plannedArrivalTime(reqVO.getPlannedArrivalTime()).version(0).build();
        workOrderMapper.insert(next);
        appendTimeline(event, PropertyEventActionEnum.TRANSFER.name(), fromStatus, event.getStatus(),
                reqVO.getReason(), "转派工单 " + previous.getWorkOrderNo() + " -> " + next.getWorkOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeEvent(PropertyEventMergeReqVO reqVO) {
        if (Objects.equals(reqVO.getMainEventId(), reqVO.getRelatedEventId())) {
            throw exception(EVENT_MERGE_INVALID);
        }
        PropertyEventDO main = validateEvent(reqVO.getMainEventId(), reqVO.getProjectId());
        PropertyEventDO related = validateEvent(reqVO.getRelatedEventId(), reqVO.getProjectId());
        validateVersion(main, reqVO.getMainVersion());
        validateVersion(related, reqVO.getRelatedVersion());
        if (isTerminalForMerge(main) || isTerminalForMerge(related)
                || related.getMainEventId() != null) {
            throw exception(EVENT_MERGE_INVALID);
        }
        LocalDateTime mainStart = main.getSlaStartTime() != null ? main.getSlaStartTime() : main.getCreateTime();
        LocalDateTime relatedStart = related.getSlaStartTime() != null
                ? related.getSlaStartTime() : related.getCreateTime();
        if (mainStart != null && relatedStart != null && relatedStart.isBefore(mainStart)) {
            long earlierSeconds = ChronoUnit.SECONDS.between(relatedStart, mainStart);
            main.setSlaStartTime(relatedStart);
            main.setResponseDeadline(main.getResponseDeadline().minusSeconds(earlierSeconds));
            main.setRecoveryDeadline(main.getRecoveryDeadline().minusSeconds(earlierSeconds));
            main.setCloseDeadline(main.getCloseDeadline().minusSeconds(earlierSeconds));
        }
        main.setRespondedTime(earliest(main.getRespondedTime(), related.getRespondedTime()));
        main.setArrivedTime(earliest(main.getArrivedTime(), related.getArrivedTime()));
        main.setRecoveredTime(earliest(main.getRecoveredTime(), related.getRecoveredTime()));
        updateEvent(main);

        int relatedFromStatus = related.getStatus();
        related.setStatus(PropertyEventStatusEnum.MERGED.getStatus());
        related.setMainEventId(main.getId());
        related.setCancelReason(reqVO.getReason());
        updateEvent(related);
        for (PropertyWorkOrderDO order : workOrderMapper.selectListByEventId(related.getId())) {
            if (!Set.of(80, 95, 96, 97, 98).contains(order.getStatus())) {
                order.setStatus(98);
                order.setProcessResult("事件已合并至 " + main.getEventNo());
                workOrderMapper.updateById(order);
            }
        }
        relationMapper.insert(PropertyEventRelationDO.builder()
                .projectId(main.getProjectId()).mainEventId(main.getId()).relatedEventId(related.getId())
                .relationType("MERGED").reason(reqVO.getReason()).operatorUserId(getLoginUserId()).build());
        appendTimeline(main, "MERGE_IN", main.getStatus(), main.getStatus(), reqVO.getReason(),
                "合并来源事件 " + related.getEventNo());
        appendTimeline(related, "MERGED_TO", relatedFromStatus, PropertyEventStatusEnum.MERGED.getStatus(), reqVO.getReason(),
                "主事件 " + main.getEventNo());
        notificationService.enqueueStatusNotification(main, "MERGED",
                "您的诉求已与同一问题合并，统一事件编号 " + main.getEventNo() + "，处理进度将统一同步。");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long splitEvent(PropertyEventSplitReqVO reqVO) {
        PropertyEventDO source = validateEvent(reqVO.getSourceEventId(), reqVO.getProjectId());
        validateVersion(source, reqVO.getSourceVersion());
        if (source.getStatus() == PropertyEventStatusEnum.MERGED.getStatus()
                || source.getStatus() == PropertyEventStatusEnum.CANCELLED.getStatus()) {
            throw exception(EVENT_SPLIT_INVALID);
        }
        if (categoryMapper.selectByProjectAndCode(reqVO.getProjectId(), reqVO.getCategoryCode()) == null) {
            throw exception(EVENT_CATEGORY_NOT_EXISTS);
        }
        LocalDateTime now = LocalDateTime.now();
        PropertyEventSlaService.SlaSnapshot sla = slaService.calculateSnapshot(reqVO.getProjectId(),
                reqVO.getCategoryCode(), reqVO.getUrgencyLevel(), now);
        PropertyEventDO child = PropertyEventDO.builder()
                .eventNo(generateNumber("EVT")).projectId(source.getProjectId())
                .communityId(source.getCommunityId()).spaceId(source.getSpaceId()).houseId(source.getHouseId())
                .assetId(source.getAssetId()).sourceType(source.getSourceType()).sourceSystem("EVENT_SPLIT")
                .sourceRecordId(source.getEventNo() + ":" + IdUtil.fastSimpleUUID().substring(0, 12))
                .categoryCode(reqVO.getCategoryCode()).title(reqVO.getTitle()).description(reqVO.getDescription())
                .urgencyLevel(reqVO.getUrgencyLevel()).impactLevel(reqVO.getImpactLevel())
                .safetyLevel(reqVO.getSafetyLevel()).confidence(source.getConfidence())
                .status(PropertyEventStatusEnum.PENDING_CONFIRM.getStatus())
                .slaRuleId(sla.ruleId()).slaRuleVersion(sla.ruleVersion())
                .slaArrivalMinutes(sla.arrivalMinutes()).slaStartTime(now)
                .responseDeadline(sla.responseDeadline()).arrivalDeadline(sla.arrivalDeadline())
                .recoveryDeadline(sla.recoveryDeadline()).closeDeadline(sla.closeDeadline())
                .slaPaused(false).slaPausedSeconds(0L).slaEscalationMinutes(sla.escalationMinutes())
                .slaEscalationLevel(0).parentEventId(source.getId()).splitReason(reqVO.getReason())
                .reopenCount(0).version(0).build();
        eventMapper.insert(child);
        for (PropertyEventEvidenceDO evidence : evidenceMapper.selectListForValidation(source.getId())) {
            evidenceMapper.insert(PropertyEventEvidenceDO.builder()
                    .eventId(child.getId()).projectId(child.getProjectId())
                    .evidenceType(evidence.getEvidenceType()).fileUrl(evidence.getFileUrl())
                    .description("继承自 " + source.getEventNo() + "：" + StrUtil.nullToEmpty(evidence.getDescription()))
                    .submittedUserId(evidence.getSubmittedUserId()).submittedTime(evidence.getSubmittedTime())
                    .verified(evidence.getVerified()).build());
        }
        updateEvent(source);
        relationMapper.insert(PropertyEventRelationDO.builder()
                .projectId(source.getProjectId()).mainEventId(source.getId()).relatedEventId(child.getId())
                .relationType("SPLIT").reason(reqVO.getReason()).operatorUserId(getLoginUserId()).build());
        appendTimeline(source, "SPLIT_OUT", source.getStatus(), source.getStatus(), reqVO.getReason(),
                "拆分子事件 " + child.getEventNo());
        appendTimeline(child, "SPLIT_FROM", null, child.getStatus(), reqVO.getReason(),
                "来源事件 " + source.getEventNo());
        return child.getId();
    }

    private boolean isTerminalForMerge(PropertyEventDO event) {
        return event.getStatus() == PropertyEventStatusEnum.MERGED.getStatus()
                || event.getStatus() == PropertyEventStatusEnum.CANCELLED.getStatus()
                || event.getStatus() == PropertyEventStatusEnum.CLOSED.getStatus();
    }

    private LocalDateTime earliest(LocalDateTime first, LocalDateTime second) {
        if (first == null) return second;
        if (second == null) return first;
        return first.isBefore(second) ? first : second;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transitionEvent(PropertyEventActionReqVO reqVO, PropertyEventActionEnum action) {
        PropertyEventDO event = validateEvent(reqVO.getId(), reqVO.getProjectId());
        validateVersion(event, reqVO.getVersion());
        validateTransition(event, action);
        validateActionData(event, reqVO, action);
        int fromStatus = event.getStatus();
        int toStatus = PropertyEventStateMachine.nextStatus(fromStatus, action);
        applyEventAction(event, reqVO, action, toStatus);
        updateEvent(event);
        if (action == PropertyEventActionEnum.REOPEN) {
            recoveryService.createAutomaticTask(event, 3, reqVO.getReason());
        }
        applyWorkOrderAction(event, reqVO, action, toStatus);
        appendTimeline(event, action.name(), fromStatus, toStatus, reqVO.getReason(), reqVO.getDetail());
        enqueueActionNotification(event, action);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addEvidence(PropertyEventEvidenceCreateReqVO reqVO) {
        validateEvent(reqVO.getEventId(), reqVO.getProjectId());
        PropertyEventEvidenceDO evidence = BeanUtils.toBean(reqVO, PropertyEventEvidenceDO.class);
        evidence.setSubmittedUserId(getLoginUserId());
        evidence.setSubmittedTime(LocalDateTime.now());
        evidence.setVerified(false);
        evidenceMapper.insert(evidence);
        PropertyEventDO event = eventMapper.selectById(reqVO.getEventId());
        appendTimeline(event, "ADD_EVIDENCE", event.getStatus(), event.getStatus(), null, reqVO.getDescription());
        return evidence.getId();
    }

    private void validateActionData(PropertyEventDO event, PropertyEventActionReqVO reqVO,
            PropertyEventActionEnum action) {
        if ((action == PropertyEventActionEnum.CANCEL || action == PropertyEventActionEnum.COLLABORATE
                || action == PropertyEventActionEnum.REJECT_ORDER
                || action == PropertyEventActionEnum.REJECT || action == PropertyEventActionEnum.REOPEN)
                && StrUtil.isBlank(reqVO.getReason())) {
            throw exception(EVENT_STATUS_TRANSITION_INVALID);
        }
        if (action == PropertyEventActionEnum.SUBMIT_ACCEPTANCE) {
            List<PropertyEventEvidenceDO> evidences = evidenceMapper.selectListForValidation(event.getId());
            if (evidences.isEmpty()) {
                throw exception(EVENT_EVIDENCE_REQUIRED);
            }
            PropertyEventCategoryDO category = categoryMapper.selectByProjectAndCode(
                    event.getProjectId(), event.getCategoryCode());
            if (category != null && StrUtil.isNotBlank(category.getRequiredEvidenceTypes())) {
                Set<Integer> actualTypes = evidences.stream().map(PropertyEventEvidenceDO::getEvidenceType)
                        .collect(Collectors.toSet());
                boolean missingRequiredType = StrUtil.split(category.getRequiredEvidenceTypes(), ',').stream()
                        .map(String::trim).filter(StrUtil::isNotBlank).map(Integer::valueOf)
                        .anyMatch(requiredType -> !actualTypes.contains(requiredType));
                if (missingRequiredType) {
                    throw exception(EVENT_EVIDENCE_REQUIRED);
                }
            }
            if (StrUtil.isBlank(reqVO.getRecoverySummary())) {
                throw exception(EVENT_ACCEPTANCE_REQUIRED);
            }
        }
        if (action == PropertyEventActionEnum.COLLABORATE && Boolean.TRUE.equals(reqVO.getPauseSla())) {
            if (Boolean.TRUE.equals(event.getSlaPaused())
                    || !isSlaPauseReasonAllowed(event, reqVO.getPauseReasonCode())) {
                throw exception(Boolean.TRUE.equals(event.getSlaPaused())
                        ? EVENT_SLA_PAUSE_STATE_INVALID : EVENT_SLA_PAUSE_REASON_INVALID);
            }
        }
        if (action == PropertyEventActionEnum.RESUME && Boolean.TRUE.equals(event.getSlaPaused())
                && slaPauseMapper.selectActiveByEventId(event.getId()) == null) {
            throw exception(EVENT_SLA_PAUSE_STATE_INVALID);
        }
        if (action == PropertyEventActionEnum.APPROVE && (StrUtil.isBlank(reqVO.getAcceptanceResult())
                || StrUtil.isBlank(reqVO.getRootCause()) || StrUtil.isBlank(reqVO.getSolution()))) {
            throw exception(EVENT_ACCEPTANCE_REQUIRED);
        }
        if (action == PropertyEventActionEnum.APPROVE
                && notificationMapper.selectUnsentCount(event.getId()) > 0) {
            throw exception(EVENT_NOTIFICATION_PENDING);
        }
    }

    private void enqueueActionNotification(PropertyEventDO event, PropertyEventActionEnum action) {
        String content = switch (action) {
            case CONFIRM -> "事件 " + event.getEventNo() + " 已确认受理，正在安排处理。";
            case ARRIVE -> "事件 " + event.getEventNo() + " 的工作人员已到场。";
            case COLLABORATE -> "事件 " + event.getEventNo() + " 正在协调资源，处理仍在持续跟进。";
            case SUBMIT_ACCEPTANCE -> "事件 " + event.getEventNo() + " 的主要影响已恢复，正在进行结果验收。";
            case APPROVE -> "事件 " + event.getEventNo() + " 已验收完成，如问题复发可继续反馈。";
            case REOPEN -> "事件 " + event.getEventNo() + " 已重新打开并继续处理。";
            default -> null;
        };
        if (content != null) {
            notificationService.enqueueStatusNotification(event, action.name(), content);
        }
    }

    private void applyEventAction(PropertyEventDO event, PropertyEventActionReqVO reqVO,
            PropertyEventActionEnum action, int toStatus) {
        LocalDateTime now = LocalDateTime.now();
        event.setStatus(toStatus);
        resetEscalationWhenStageChanges(event, action);
        switch (action) {
            case CONFIRM -> event.setRespondedTime(now);
            case ARRIVE -> event.setArrivedTime(now);
            case COLLABORATE -> {
                event.setBlockReason(reqVO.getReason());
                if (Boolean.TRUE.equals(reqVO.getPauseSla())) {
                    pauseCloseSla(event, reqVO, now);
                }
            }
            case RESUME -> {
                event.setBlockReason(null);
                resumeCloseSla(event, now);
            }
            case SUBMIT_ACCEPTANCE -> {
                event.setRecoveredTime(now);
                event.setRecoverySummary(reqVO.getRecoverySummary());
            }
            case APPROVE -> {
                event.setClosedTime(now);
                event.setAcceptanceResult(reqVO.getAcceptanceResult());
                event.setRootCause(reqVO.getRootCause());
                event.setSolution(reqVO.getSolution());
            }
            case CANCEL -> event.setCancelReason(reqVO.getReason());
            case REOPEN -> {
                event.setReopenCount(event.getReopenCount() == null ? 1 : event.getReopenCount() + 1);
                event.setClosedTime(null);
                event.setAcceptanceResult(null);
            }
            default -> {
                // No additional event fields for this transition.
            }
        }
    }

    private void resetEscalationWhenStageChanges(PropertyEventDO event, PropertyEventActionEnum action) {
        if (action == PropertyEventActionEnum.CONFIRM || action == PropertyEventActionEnum.DISPATCH
                || action == PropertyEventActionEnum.ACCEPT || action == PropertyEventActionEnum.ARRIVE
                || action == PropertyEventActionEnum.START || action == PropertyEventActionEnum.SUBMIT_ACCEPTANCE
                || action == PropertyEventActionEnum.REOPEN) {
            resetEscalation(event);
        }
    }

    private void resetEscalation(PropertyEventDO event) {
        event.setSlaEscalationStage(null);
        event.setSlaEscalationLevel(0);
        event.setSlaEscalatedTime(null);
    }

    private boolean isSlaPauseReasonAllowed(PropertyEventDO event, String reasonCode) {
        String reasonLabel = switch (reasonCode == null ? "" : reasonCode) {
            case "CUSTOMER_APPOINTMENT" -> "客户预约";
            case "LEGAL_STOP_WORK" -> "法定停工";
            case "EXTERNAL_APPROVAL" -> "外部审批";
            default -> null;
        };
        if (reasonLabel == null) {
            return false;
        }
        if (event.getSlaRuleId() == null) {
            return true;
        }
        PropertyEventSlaRuleDO rule = slaRuleMapper.selectById(event.getSlaRuleId());
        if (rule == null || StrUtil.isBlank(rule.getAllowedPauseReasons())) {
            return false;
        }
        return StrUtil.split(rule.getAllowedPauseReasons(), ',').stream()
                .map(String::trim).anyMatch(value -> value.equals(reasonCode) || value.equals(reasonLabel));
    }

    private void pauseCloseSla(PropertyEventDO event, PropertyEventActionReqVO reqVO, LocalDateTime now) {
        event.setSlaPaused(true);
        event.setSlaPauseReasonCode(reqVO.getPauseReasonCode());
        event.setSlaPauseStartedTime(now);
        slaPauseMapper.insert(PropertyEventSlaPauseDO.builder()
                .eventId(event.getId())
                .projectId(event.getProjectId())
                .reasonCode(reqVO.getPauseReasonCode())
                .reason(reqVO.getReason())
                .startedTime(now)
                .pauseUserId(getLoginUserId())
                .build());
    }

    private void resumeCloseSla(PropertyEventDO event, LocalDateTime now) {
        if (!Boolean.TRUE.equals(event.getSlaPaused())) {
            return;
        }
        PropertyEventSlaPauseDO pause = slaPauseMapper.selectActiveByEventId(event.getId());
        if (pause == null) {
            throw exception(EVENT_SLA_PAUSE_STATE_INVALID);
        }
        long pausedSeconds = Math.max(0, ChronoUnit.SECONDS.between(pause.getStartedTime(), now));
        pause.setResumedTime(now);
        pause.setDurationSeconds(pausedSeconds);
        pause.setResumeUserId(getLoginUserId());
        slaPauseMapper.updateById(pause);
        event.setCloseDeadline(event.getCloseDeadline().plusSeconds(pausedSeconds));
        event.setSlaPausedSeconds((event.getSlaPausedSeconds() == null ? 0L : event.getSlaPausedSeconds())
                + pausedSeconds);
        event.setSlaPaused(false);
        event.setSlaPauseReasonCode(null);
        event.setSlaPauseStartedTime(null);
    }

    private void applyWorkOrderAction(PropertyEventDO event, PropertyEventActionReqVO reqVO,
            PropertyEventActionEnum action, int toStatus) {
        if (action == PropertyEventActionEnum.CONFIRM || action == PropertyEventActionEnum.CANCEL
                || action == PropertyEventActionEnum.REOPEN) {
            return;
        }
        PropertyWorkOrderDO workOrder = workOrderMapper.selectLatestByEventId(event.getId());
        if (workOrder == null) {
            throw exception(EVENT_WORK_ORDER_NOT_EXISTS);
        }
        LocalDateTime now = LocalDateTime.now();
        workOrder.setStatus(toStatus);
        switch (action) {
            case ACCEPT -> workOrder.setAcceptedTime(now);
            case REJECT_ORDER -> {
                workOrder.setStatus(95);
                workOrder.setRejectReason(reqVO.getReason());
            }
            case ARRIVE -> workOrder.setArrivedTime(now);
            case SUBMIT_ACCEPTANCE -> {
                workOrder.setCompletedTime(now);
                workOrder.setProcessResult(reqVO.getRecoverySummary());
            }
            default -> {
                // Status synchronization is enough for other work-order actions.
            }
        }
        if (workOrderMapper.updateById(workOrder) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    private PropertyEventDO validateEvent(Long id, Long projectId) {
        PropertyEventDO event = eventMapper.selectByIdAndProjectId(id, projectId);
        if (event == null) {
            throw exception(EVENT_NOT_EXISTS);
        }
        return event;
    }

    private void validateVersion(PropertyEventDO event, Integer version) {
        if (!Objects.equals(event.getVersion(), version)) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    private void validateTransition(PropertyEventDO event, PropertyEventActionEnum action) {
        if (!PropertyEventStateMachine.canTransition(event.getStatus(), action)) {
            throw exception(EVENT_STATUS_TRANSITION_INVALID);
        }
    }

    private void updateEvent(PropertyEventDO event) {
        if (eventMapper.updateById(event) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    private void appendTimeline(PropertyEventDO event, String action, Integer fromStatus, Integer toStatus,
            String reason, String detail) {
        timelineMapper.insert(PropertyEventTimelineDO.builder()
                .eventId(event.getId())
                .projectId(event.getProjectId())
                .action(action)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .operatorUserId(getLoginUserId())
                .reason(reason)
                .detail(detail)
                .eventVersion(event.getVersion())
                .build());
    }

    private String generateNumber(String prefix) {
        return prefix + LocalDateTime.now().format(NUMBER_DATE_FORMATTER) + IdUtil.fastSimpleUUID()
                .substring(0, 10).toUpperCase();
    }

}

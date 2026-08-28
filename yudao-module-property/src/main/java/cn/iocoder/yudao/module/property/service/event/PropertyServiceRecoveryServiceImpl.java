package cn.iocoder.yudao.module.property.service.event;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyServiceRecoveryTaskMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CONCURRENT_UPDATE;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.SERVICE_RECOVERY_ACTION_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.SERVICE_RECOVERY_NOT_EXISTS;

@Service
public class PropertyServiceRecoveryServiceImpl implements PropertyServiceRecoveryService {

    private static final DateTimeFormatter NUMBER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Resource
    private PropertyServiceRecoveryTaskMapper recoveryMapper;
    @Resource
    private PropertyEventMapper eventMapper;
    @Resource
    private PropertyEventTimelineMapper timelineMapper;
    @Resource
    private PropertyProjectService projectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createNegativeReviewTask(PropertyServiceRecoveryCreateReqVO reqVO) {
        PropertyEventDO event = validateEvent(reqVO.getEventId(), reqVO.getProjectId());
        Long id = createAutomaticTask(event, 2, reqVO.getTriggerDetail());
        PropertyServiceRecoveryTaskDO task = recoveryMapper.selectById(id);
        if (task.getOriginalRating() == null) {
            task.setOriginalRating(reqVO.getOriginalRating());
            recoveryMapper.updateById(task);
        }
        return id;
    }

    @Override
    public Long createAutomaticTask(PropertyEventDO event, Integer triggerType, String detail) {
        PropertyServiceRecoveryTaskDO existing = recoveryMapper.selectByEventAndTrigger(event.getId(), triggerType);
        if (existing != null) {
            return existing.getId();
        }
        PropertyServiceRecoveryTaskDO task = PropertyServiceRecoveryTaskDO.builder()
                .recoveryNo(generateNumber()).projectId(event.getProjectId()).eventId(event.getId())
                .triggerType(triggerType).triggerDetail(detail).status(0).version(0).build();
        recoveryMapper.insert(task);
        appendTimeline(event, "CREATE_SERVICE_RECOVERY", detail,
                "恢复任务 " + task.getRecoveryNo() + "，触发类型 " + triggerType);
        return task.getId();
    }

    @Override
    public PageResult<PropertyServiceRecoveryTaskDO> getPage(PropertyServiceRecoveryPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return recoveryMapper.selectPage(reqVO);
    }

    @Override
    public List<PropertyServiceRecoveryTaskDO> getListByEventId(Long eventId, Long projectId) {
        validateEvent(eventId, projectId);
        return recoveryMapper.selectListByEventId(eventId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeAction(PropertyServiceRecoveryActionReqVO reqVO) {
        PropertyServiceRecoveryTaskDO task = recoveryMapper.selectById(reqVO.getId());
        if (task == null || !Objects.equals(task.getProjectId(), reqVO.getProjectId())) {
            throw exception(SERVICE_RECOVERY_NOT_EXISTS);
        }
        if (!Objects.equals(task.getVersion(), reqVO.getVersion())) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
        LocalDateTime now = LocalDateTime.now();
        switch (reqVO.getAction()) {
            case "ASSIGN" -> {
                require(reqVO.getResponsibleUserId() != null);
                task.setResponsibleUserId(reqVO.getResponsibleUserId());
            }
            case "CONTACT" -> {
                require(task.getStatus() == 0 && task.getResponsibleUserId() != null
                        && StrUtil.isNotBlank(reqVO.getContactResult()));
                task.setStatus(10);
                task.setContactTime(now);
                task.setContactResult(reqVO.getContactResult());
            }
            case "PLAN" -> {
                require(task.getStatus() == 10 && StrUtil.isNotBlank(reqVO.getRecoveryPlan())
                        && reqVO.getPlanDueTime() != null);
                task.setStatus(20);
                task.setRecoveryPlan(reqVO.getRecoveryPlan());
                task.setPlanDueTime(reqVO.getPlanDueTime());
            }
            case "COMPLETE" -> {
                require(task.getStatus() == 20 && reqVO.getRecoveredSatisfaction() != null
                        && reqVO.getRecoveredSatisfaction() >= 1 && reqVO.getRecoveredSatisfaction() <= 5);
                task.setStatus(30);
                task.setCompletedTime(now);
                task.setRecoveredSatisfaction(reqVO.getRecoveredSatisfaction());
            }
            default -> throw exception(SERVICE_RECOVERY_ACTION_INVALID);
        }
        if (recoveryMapper.updateById(task) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
        PropertyEventDO event = eventMapper.selectById(task.getEventId());
        appendTimeline(event, "SERVICE_RECOVERY_" + reqVO.getAction(), null,
                "恢复任务 " + task.getRecoveryNo() + " 状态 " + task.getStatus());
    }

    private void require(boolean condition) {
        if (!condition) {
            throw exception(SERVICE_RECOVERY_ACTION_INVALID);
        }
    }

    private PropertyEventDO validateEvent(Long eventId, Long projectId) {
        PropertyEventDO event = eventMapper.selectByIdAndProjectId(eventId, projectId);
        if (event == null) {
            throw exception(EVENT_NOT_EXISTS);
        }
        return event;
    }

    private void appendTimeline(PropertyEventDO event, String action, String reason, String detail) {
        timelineMapper.insert(PropertyEventTimelineDO.builder()
                .eventId(event.getId()).projectId(event.getProjectId()).action(action)
                .fromStatus(event.getStatus()).toStatus(event.getStatus()).operatorUserId(getLoginUserId())
                .reason(reason).detail(detail).eventVersion(event.getVersion()).build());
    }

    private String generateNumber() {
        return "SR" + LocalDateTime.now().format(NUMBER_DATE_FORMATTER)
                + IdUtil.fastSimpleUUID().substring(0, 10).toUpperCase();
    }
}

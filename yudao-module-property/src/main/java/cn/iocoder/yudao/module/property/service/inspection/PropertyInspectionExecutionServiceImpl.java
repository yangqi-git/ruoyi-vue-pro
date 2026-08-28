package cn.iocoder.yudao.module.property.service.inspection;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionIssueActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionConflictResolveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionOfflineSubmitRespVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPlanSaveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionRecordSubmitReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionTaskActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionTaskResponsibilityReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionQualitySampleReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionConflictDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionIssueDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPlanDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionRecordDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskAssignmentLogDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionQualitySampleDO;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionConflictMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionIssueMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPlanMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPointMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionRecordMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionStandardMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionTaskMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionTaskAssignmentLogMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionQualitySampleMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.service.event.PropertyEventService;
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
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_ISSUE_ACTION_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_CONFLICT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_CONFLICT_RESOLUTION_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_ISSUE_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_PLAN_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_RESOURCE_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_TASK_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_TASK_STATE_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_QUALITY_ACTION_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_QUALITY_SAMPLE_NOT_EXISTS;

@Service
public class PropertyInspectionExecutionServiceImpl implements PropertyInspectionExecutionService {
    private static final DateTimeFormatter NUMBER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Resource private PropertyInspectionPlanMapper planMapper;
    @Resource private PropertyInspectionTaskMapper taskMapper;
    @Resource private PropertyInspectionRecordMapper recordMapper;
    @Resource private PropertyInspectionIssueMapper issueMapper;
    @Resource private PropertyInspectionStandardMapper standardMapper;
    @Resource private PropertyInspectionPointMapper pointMapper;
    @Resource private PropertyProjectService projectService;
    @Resource private PropertyEventService eventService;
    @Resource private PropertyEventMapper eventMapper;
    @Resource private PropertyInspectionConflictMapper conflictMapper;
    @Resource private PropertyInspectionTaskAssignmentLogMapper assignmentLogMapper;
    @Resource private PropertyInspectionQualitySampleMapper qualitySampleMapper;

    @Override
    public Long createPlan(PropertyInspectionPlanSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateResources(reqVO.getProjectId(), reqVO.getStandardIds(), reqVO.getPointIds());
        PropertyInspectionPlanDO plan = BeanUtils.toBean(reqVO, PropertyInspectionPlanDO.class);
        plan.setNextGenerateTime(initialGenerateTime(reqVO));
        plan.setVersion(0);
        planMapper.insert(plan);
        return plan.getId();
    }

    @Override
    public void updatePlan(PropertyInspectionPlanSaveReqVO reqVO) {
        validatePlan(reqVO.getId(), reqVO.getProjectId());
        validateResources(reqVO.getProjectId(), reqVO.getStandardIds(), reqVO.getPointIds());
        PropertyInspectionPlanDO plan = BeanUtils.toBean(reqVO, PropertyInspectionPlanDO.class);
        plan.setNextGenerateTime(initialGenerateTime(reqVO));
        if (planMapper.updateById(plan) == 0) throw exception(INSPECTION_TASK_STATE_INVALID);
    }

    @Override
    public PageResult<PropertyInspectionPlanDO> getPlanPage(PropertyInspectionPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return planMapper.selectPage(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateTask(Long planId, Long projectId) {
        return generateTask(validatePlan(planId, projectId), LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateDueTasks() {
        int count = 0;
        LocalDateTime now = LocalDateTime.now();
        for (PropertyInspectionPlanDO plan : planMapper.selectDueList(now)) {
            if (plan.getEffectiveEnd() == null || !now.toLocalDate().isAfter(plan.getEffectiveEnd())) {
                generateTask(plan, now);
                count++;
            } else {
                plan.setStatus(1);
                planMapper.updateById(plan);
            }
        }
        return count;
    }

    @Override
    public int syncLinkedEventResults() {
        int count = 0;
        for (PropertyInspectionIssueDO issue : issueMapper.selectLinkedEventList()) {
            PropertyEventDO event = eventMapper.selectById(issue.getEventId());
            if (event == null) continue;
            issue.setEventStatus(event.getStatus());
            if (event.getStatus() == 80) {
                issue.setEventClosedTime(event.getClosedTime());
                issue.setEventResultSnapshot(StrUtil.join("；",
                        StrUtil.nullToEmpty(event.getRecoverySummary()),
                        StrUtil.nullToEmpty(event.getRootCause()),
                        StrUtil.nullToEmpty(event.getSolution())));
            }
            issueMapper.updateById(issue);
            count++;
        }
        return count;
    }

    @Override
    public PageResult<PropertyInspectionTaskDO> getTaskPage(PropertyInspectionPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return taskMapper.selectPage(reqVO);
    }

    @Override
    public List<PropertyInspectionRecordDO> getTaskRecords(Long taskId, Long projectId) {
        validateTask(taskId, projectId);
        return recordMapper.selectListByTaskId(taskId);
    }

    @Override
    public void startTask(PropertyInspectionTaskActionReqVO reqVO) {
        PropertyInspectionTaskDO task = validateTask(reqVO.getId(), reqVO.getProjectId());
        validateTaskVersion(task, reqVO.getVersion());
        if (task.getStatus() != 0) throw exception(INSPECTION_TASK_STATE_INVALID);
        task.setStatus(10);
        task.setStartedTime(LocalDateTime.now());
        updateTask(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitRecord(PropertyInspectionRecordSubmitReqVO reqVO) {
        PropertyInspectionRecordDO existing = recordMapper.selectByClientOperationId(reqVO.getClientOperationId());
        if (existing != null) return existing.getId();
        PropertyInspectionTaskDO task = validateTask(reqVO.getTaskId(), reqVO.getProjectId());
        validateTaskVersion(task, reqVO.getTaskVersion());
        if (task.getStatus() != 10 || !parseIds(task.getPointIds()).contains(reqVO.getPointId())
                || !snapshotContains(task.getStandardSnapshot(), reqVO.getStandardId())) {
            throw exception(INSPECTION_TASK_STATE_INVALID);
        }
        PropertyInspectionPointDO point = pointMapper.selectById(reqVO.getPointId());
        PropertyInspectionStandardDO standard = standardMapper.selectById(reqVO.getStandardId());
        if (point == null || standard == null || !Objects.equals(point.getProjectId(), reqVO.getProjectId())
                || !Objects.equals(standard.getProjectId(), reqVO.getProjectId())) {
            throw exception(INSPECTION_RESOURCE_INVALID);
        }
        List<PropertyInspectionRecordDO> records = recordMapper.selectListByTaskId(task.getId());
        if (Boolean.TRUE.equals(point.getSequenceRequired()) && reqVO.getSequenceNo() != records.size() + 1) {
            throw exception(INSPECTION_TASK_STATE_INVALID);
        }
        PropertyInspectionRecordDO record = BeanUtils.toBean(reqVO, PropertyInspectionRecordDO.class);
        record.setStandardVersion(standard.getStandardVersion());
        record.setSubmittedUserId(getLoginUserId());
        record.setSubmittedTime(LocalDateTime.now());
        record.setSuspicious(false);
        recordMapper.insert(record);
        task.setCheckCount(task.getCheckCount() + 1);
        if (reqVO.getResult() == 10) {
            task.setAbnormalCount(task.getAbnormalCount() + 1);
            createIssue(task, record, point, standard, reqVO);
        }
        updateTask(task);
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PropertyInspectionOfflineSubmitRespVO submitOfflineRecord(PropertyInspectionRecordSubmitReqVO reqVO) {
        PropertyInspectionRecordDO existingRecord = recordMapper.selectByClientOperationId(reqVO.getClientOperationId());
        if (existingRecord != null) {
            return PropertyInspectionOfflineSubmitRespVO.builder().accepted(true).recordId(existingRecord.getId()).build();
        }
        PropertyInspectionConflictDO existingConflict = conflictMapper.selectByClientOperationId(reqVO.getClientOperationId());
        if (existingConflict != null) {
            return PropertyInspectionOfflineSubmitRespVO.builder().accepted(false).conflictId(existingConflict.getId())
                    .conflictReason(existingConflict.getConflictReason()).build();
        }
        PropertyInspectionTaskDO task = taskMapper.selectById(reqVO.getTaskId());
        if (task == null || !Objects.equals(task.getProjectId(), reqVO.getProjectId())) {
            throw exception(INSPECTION_TASK_NOT_EXISTS);
        }
        if (task.getStatus() == 10 && Objects.equals(task.getVersion(), reqVO.getTaskVersion())) {
            return PropertyInspectionOfflineSubmitRespVO.builder().accepted(true).recordId(submitRecord(reqVO)).build();
        }
        String reason = task.getStatus() != 10 ? "SERVER_TASK_CLOSED_OR_CHANGED" : "TASK_VERSION_CONFLICT";
        PropertyInspectionConflictDO conflict = BeanUtils.toBean(reqVO, PropertyInspectionConflictDO.class);
        conflict.setConflictNo(generateNumber("IC"));
        conflict.setClientTaskVersion(reqVO.getTaskVersion());
        conflict.setServerTaskVersion(task.getVersion());
        conflict.setServerTaskStatus(task.getStatus());
        conflict.setConflictReason(reason);
        conflict.setStatus(0);
        conflict.setVersion(0);
        conflictMapper.insert(conflict);
        return PropertyInspectionOfflineSubmitRespVO.builder().accepted(false).conflictId(conflict.getId())
                .conflictReason(reason).build();
    }

    @Override
    public void completeTask(PropertyInspectionTaskActionReqVO reqVO) {
        PropertyInspectionTaskDO task = validateTask(reqVO.getId(), reqVO.getProjectId());
        validateTaskVersion(task, reqVO.getVersion());
        if (task.getStatus() != 10 || task.getCheckCount() < parseIds(task.getPointIds()).size()) {
            throw exception(INSPECTION_TASK_STATE_INVALID);
        }
        LocalDateTime now = LocalDateTime.now();
        task.setStatus(20);
        task.setSubmittedTime(now);
        task.setOnTime(!now.isAfter(task.getPlannedEndTime()));
        updateTask(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeTaskResponsibility(PropertyInspectionTaskResponsibilityReqVO reqVO) {
        PropertyInspectionTaskDO task = validateTask(reqVO.getId(), reqVO.getProjectId());
        validateTaskVersion(task, reqVO.getVersion());
        if (!List.of(0, 10).contains(task.getStatus())) throw exception(INSPECTION_TASK_STATE_INVALID);
        Long fromUserId = task.getInspectorUserId();
        int fromStatus = task.getStatus();
        if ("TRANSFER".equals(reqVO.getAction()) && reqVO.getTargetUserId() != null
                && !Objects.equals(fromUserId, reqVO.getTargetUserId())) {
            task.setInspectorUserId(reqVO.getTargetUserId());
        } else if ("TERMINATE".equals(reqVO.getAction())) {
            task.setStatus(90);
            task.setTerminateReason(reqVO.getReason());
        } else {
            throw exception(INSPECTION_TASK_STATE_INVALID);
        }
        updateTask(task);
        assignmentLogMapper.insert(PropertyInspectionTaskAssignmentLogDO.builder()
                .projectId(task.getProjectId()).taskId(task.getId()).action(reqVO.getAction())
                .fromUserId(fromUserId).toUserId(task.getInspectorUserId()).fromStatus(fromStatus)
                .toStatus(task.getStatus()).reason(reqVO.getReason()).operatorUserId(getLoginUserId())
                .taskVersion(task.getVersion()).build());
    }

    @Override
    public PageResult<PropertyInspectionIssueDO> getIssuePage(PropertyInspectionPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return issueMapper.selectPage(reqVO);
    }

    @Override
    public void executeIssueAction(PropertyInspectionIssueActionReqVO reqVO) {
        PropertyInspectionIssueDO issue = issueMapper.selectById(reqVO.getId());
        if (issue == null || !Objects.equals(issue.getProjectId(), reqVO.getProjectId())
                || !Objects.equals(issue.getVersion(), reqVO.getVersion())) {
            throw exception(INSPECTION_ISSUE_NOT_EXISTS);
        }
        if ("RECTIFY".equals(reqVO.getAction()) && issue.getStatus() == 0
                && StrUtil.isNotBlank(reqVO.getRectificationResult()) && StrUtil.isNotBlank(reqVO.getAfterEvidenceUrls())
                && (getLoginUserId() == null || Objects.equals(getLoginUserId(), issue.getRectifierUserId()))) {
            issue.setRectificationResult(reqVO.getRectificationResult());
            issue.setAfterEvidenceUrls(reqVO.getAfterEvidenceUrls());
            issue.setRectifiedTime(LocalDateTime.now());
            issue.setStatus(10);
        } else if ("REVIEW".equals(reqVO.getAction()) && issue.getStatus() == 10
                && !Objects.equals(issue.getRectifierUserId(), issue.getReviewerUserId())
                && StrUtil.isNotBlank(reqVO.getReviewResult())
                && (getLoginUserId() == null || Objects.equals(getLoginUserId(), issue.getReviewerUserId()))) {
            issue.setReviewResult(reqVO.getReviewResult());
            issue.setReviewedTime(LocalDateTime.now());
            issue.setReviewCount(issue.getReviewCount() + 1);
            if (Boolean.TRUE.equals(reqVO.getPassed())) {
                issue.setStatus(20);
            } else {
                issue.setStatus(0);
                issue.setSuspectedUnresolved(issue.getReviewCount() >= 2);
            }
        } else {
            throw exception(INSPECTION_ISSUE_ACTION_INVALID);
        }
        if (issueMapper.updateById(issue) == 0) throw exception(INSPECTION_TASK_STATE_INVALID);
    }

    @Override
    public PageResult<PropertyInspectionConflictDO> getConflictPage(PropertyInspectionPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return conflictMapper.selectPage(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resolveConflict(PropertyInspectionConflictResolveReqVO reqVO) {
        PropertyInspectionConflictDO conflict = conflictMapper.selectById(reqVO.getId());
        if (conflict == null || conflict.getStatus() != 0
                || !Objects.equals(conflict.getProjectId(), reqVO.getProjectId())
                || !Objects.equals(conflict.getVersion(), reqVO.getVersion())) {
            throw exception(INSPECTION_CONFLICT_NOT_EXISTS);
        }
        if ("KEEP_FIELD_RECORD".equals(reqVO.getResolution())) {
            retainConflictRecord(conflict);
        } else if ("REGENERATE_TASK".equals(reqVO.getResolution())) {
            PropertyInspectionTaskDO task = validateTask(conflict.getTaskId(), conflict.getProjectId());
            conflict.setReplacementTaskId(generateTask(task.getPlanId(), task.getProjectId()));
        } else if (!"VOID_FIELD_RECORD".equals(reqVO.getResolution())) {
            throw exception(INSPECTION_CONFLICT_RESOLUTION_INVALID);
        }
        conflict.setStatus(10);
        conflict.setResolution(reqVO.getResolution());
        conflict.setResolutionRemark(reqVO.getResolutionRemark());
        conflict.setResolverUserId(getLoginUserId());
        conflict.setResolvedTime(LocalDateTime.now());
        if (conflictMapper.updateById(conflict) == 0) throw exception(INSPECTION_CONFLICT_NOT_EXISTS);
    }

    @Override
    public Long createQualitySample(PropertyInspectionQualitySampleReqVO reqVO) {
        PropertyInspectionTaskDO task = validateTask(reqVO.getTaskId(), reqVO.getProjectId());
        if (task.getStatus() != 20 || Objects.equals(task.getInspectorUserId(), reqVO.getReviewerUserId())) {
            throw exception(INSPECTION_QUALITY_ACTION_INVALID);
        }
        List<PropertyInspectionRecordDO> records = recordMapper.selectListByTaskId(task.getId());
        PropertyInspectionQualitySampleDO sample = PropertyInspectionQualitySampleDO.builder()
                .sampleNo(generateNumber("QS")).projectId(task.getProjectId()).taskId(task.getId())
                .sampleType(reqVO.getSampleType()).reviewerUserId(reqVO.getReviewerUserId())
                .recordCount(records.size()).suspiciousCount((int) records.stream()
                        .filter(item -> Boolean.TRUE.equals(item.getSuspicious())).count())
                .status(0).version(0).build();
        qualitySampleMapper.insert(sample);
        return sample.getId();
    }

    @Override
    public void completeQualitySample(PropertyInspectionQualitySampleReqVO reqVO) {
        PropertyInspectionQualitySampleDO sample = qualitySampleMapper.selectById(reqVO.getId());
        if (sample == null || sample.getStatus() != 0 || !Objects.equals(sample.getProjectId(), reqVO.getProjectId())
                || !Objects.equals(sample.getVersion(), reqVO.getVersion()) || reqVO.getScore() == null
                || reqVO.getPassed() == null || StrUtil.isBlank(reqVO.getFindings())) {
            throw exception(INSPECTION_QUALITY_SAMPLE_NOT_EXISTS);
        }
        sample.setScore(reqVO.getScore());
        sample.setPassed(reqVO.getPassed());
        sample.setFindings(reqVO.getFindings());
        sample.setImprovementActions(reqVO.getImprovementActions());
        sample.setStatus(10);
        sample.setCompletedTime(LocalDateTime.now());
        if (qualitySampleMapper.updateById(sample) == 0) throw exception(INSPECTION_QUALITY_SAMPLE_NOT_EXISTS);
    }

    @Override
    public PageResult<PropertyInspectionQualitySampleDO> getQualitySamplePage(PropertyInspectionPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return qualitySampleMapper.selectPage(reqVO);
    }

    private Long generateTask(PropertyInspectionPlanDO plan, LocalDateTime now) {
        List<PropertyInspectionStandardDO> standards = validateResources(
                plan.getProjectId(), plan.getStandardIds(), plan.getPointIds());
        LocalDateTime start = now.isAfter(plan.getNextGenerateTime()) ? now : plan.getNextGenerateTime();
        PropertyInspectionTaskDO task = PropertyInspectionTaskDO.builder()
                .taskNo(generateNumber("IT")).projectId(plan.getProjectId()).planId(plan.getId())
                .planName(plan.getName()).specialty(plan.getSpecialty())
                .standardSnapshot(standards.stream().map(item -> item.getId() + ":" + item.getStandardVersion())
                        .reduce((a, b) -> a + "," + b).orElse(""))
                .pointIds(plan.getPointIds()).plannedStartTime(start)
                .plannedEndTime(start.plusMinutes(plan.getWindowMinutes()))
                .originalInspectorUserId(plan.getInspectorUserId()).inspectorUserId(plan.getInspectorUserId())
                .status(0).checkCount(0).abnormalCount(0).version(0).build();
        taskMapper.insert(task);
        plan.setNextGenerateTime(nextGenerateTime(start, plan));
        planMapper.updateById(plan);
        return task.getId();
    }

    private void createIssue(PropertyInspectionTaskDO task, PropertyInspectionRecordDO record,
            PropertyInspectionPointDO point, PropertyInspectionStandardDO standard,
            PropertyInspectionRecordSubmitReqVO reqVO) {
        if (reqVO.getRectifierUserId() == null || reqVO.getReviewerUserId() == null
                || Objects.equals(reqVO.getRectifierUserId(), reqVO.getReviewerUserId())
                || StrUtil.isBlank(reqVO.getTemporaryControl())) {
            throw exception(INSPECTION_ISSUE_ACTION_INVALID);
        }
        PropertyInspectionIssueDO issue = PropertyInspectionIssueDO.builder()
                .issueNo(generateNumber("II")).projectId(task.getProjectId()).taskId(task.getId())
                .recordId(record.getId()).pointId(point.getId()).standardId(standard.getId())
                .riskLevel(standard.getRiskLevel()).description(reqVO.getRemark())
                .temporaryControl(reqVO.getTemporaryControl()).rectifierUserId(reqVO.getRectifierUserId())
                .reviewerUserId(reqVO.getReviewerUserId())
                .rectificationDeadline(LocalDateTime.now().plusHours(standard.getRectificationHours()))
                .status(0).beforeEvidenceUrls(reqVO.getEvidenceUrls()).reviewCount(0)
                .suspectedUnresolved(false).version(0).build();
        issueMapper.insert(issue);
        if (standard.getRiskLevel() >= 3) {
            PropertyEventCreateReqVO eventReq = new PropertyEventCreateReqVO();
            eventReq.setProjectId(task.getProjectId());
            eventReq.setCommunityId(point.getCommunityId());
            eventReq.setSpaceId(point.getSpaceId());
            eventReq.setAssetId(point.getAssetId());
            eventReq.setSourceType(5);
            eventReq.setSourceSystem("PROPERTY_INSPECTION");
            eventReq.setSourceRecordId(issue.getIssueNo());
            eventReq.setCategoryCode(standard.getEventCategoryCode());
            eventReq.setTitle("巡检重大异常：" + point.getName());
            eventReq.setDescription(reqVO.getRemark() + "；临时控制：" + reqVO.getTemporaryControl());
            eventReq.setUrgencyLevel(Math.min(4, standard.getRiskLevel()));
            eventReq.setImpactLevel(Math.min(4, point.getRiskLevel()));
            eventReq.setSafetyLevel(Math.min(3, standard.getRiskLevel()));
            issue.setEventId(eventService.createEvent(eventReq));
            issueMapper.updateById(issue);
        }
    }

    private void retainConflictRecord(PropertyInspectionConflictDO conflict) {
        if (recordMapper.selectByClientOperationId(conflict.getClientOperationId()) != null) return;
        PropertyInspectionTaskDO task = validateTask(conflict.getTaskId(), conflict.getProjectId());
        PropertyInspectionPointDO point = pointMapper.selectById(conflict.getPointId());
        PropertyInspectionStandardDO standard = standardMapper.selectById(conflict.getStandardId());
        if (point == null || standard == null || !Objects.equals(point.getProjectId(), conflict.getProjectId())
                || !Objects.equals(standard.getProjectId(), conflict.getProjectId())) {
            throw exception(INSPECTION_RESOURCE_INVALID);
        }
        PropertyInspectionRecordDO record = PropertyInspectionRecordDO.builder()
                .projectId(conflict.getProjectId()).taskId(conflict.getTaskId()).pointId(conflict.getPointId())
                .standardId(conflict.getStandardId()).standardVersion(standard.getStandardVersion())
                .sequenceNo(conflict.getSequenceNo()).verificationMethod(conflict.getVerificationMethod())
                .verificationCode(conflict.getVerificationCode()).result(conflict.getResult())
                .evidenceUrls(conflict.getEvidenceUrls()).remark(conflict.getRemark())
                .exceptionReasonCode(conflict.getExceptionReasonCode()).clientOperationId(conflict.getClientOperationId())
                .submittedUserId(getLoginUserId()).submittedTime(LocalDateTime.now()).suspicious(false)
                .conflictId(conflict.getId()).retainedAfterConflict(true).build();
        recordMapper.insert(record);
        if (conflict.getResult() == 10) {
            PropertyInspectionRecordSubmitReqVO reqVO = new PropertyInspectionRecordSubmitReqVO();
            reqVO.setEvidenceUrls(conflict.getEvidenceUrls());
            reqVO.setRemark(conflict.getRemark());
            reqVO.setTemporaryControl(conflict.getTemporaryControl());
            reqVO.setRectifierUserId(conflict.getRectifierUserId());
            reqVO.setReviewerUserId(conflict.getReviewerUserId());
            createIssue(task, record, point, standard, reqVO);
        }
    }

    private List<PropertyInspectionStandardDO> validateResources(Long projectId, String standardIds, String pointIds) {
        List<Long> standardIdList = parseIds(standardIds);
        List<Long> pointIdList = parseIds(pointIds);
        if (standardIdList.isEmpty() || pointIdList.isEmpty()) throw exception(INSPECTION_RESOURCE_INVALID);
        List<PropertyInspectionStandardDO> standards = standardMapper.selectBatchIds(standardIdList);
        List<PropertyInspectionPointDO> points = pointMapper.selectBatchIds(pointIdList);
        if (standards.size() != standardIdList.size() || points.size() != pointIdList.size()
                || standards.stream().anyMatch(item -> !Objects.equals(item.getProjectId(), projectId)
                        || !Boolean.TRUE.equals(item.getPublished()) || item.getStatus() != 0)
                || points.stream().anyMatch(item -> !Objects.equals(item.getProjectId(), projectId) || item.getStatus() != 0)) {
            throw exception(INSPECTION_RESOURCE_INVALID);
        }
        return standards;
    }

    private List<Long> parseIds(String ids) {
        try {
            return StrUtil.split(ids, ',').stream().map(String::trim).filter(StrUtil::isNotBlank)
                    .map(Long::valueOf).distinct().toList();
        } catch (NumberFormatException ex) {
            throw exception(INSPECTION_RESOURCE_INVALID);
        }
    }

    private boolean snapshotContains(String snapshot, Long id) {
        return StrUtil.split(snapshot, ',').stream().anyMatch(item -> item.startsWith(id + ":"));
    }

    private PropertyInspectionPlanDO validatePlan(Long id, Long projectId) {
        PropertyInspectionPlanDO plan = planMapper.selectById(id);
        if (plan == null || !Objects.equals(plan.getProjectId(), projectId)) throw exception(INSPECTION_PLAN_NOT_EXISTS);
        return plan;
    }

    private PropertyInspectionTaskDO validateTask(Long id, Long projectId) {
        PropertyInspectionTaskDO task = taskMapper.selectById(id);
        if (task == null || !Objects.equals(task.getProjectId(), projectId)) throw exception(INSPECTION_TASK_NOT_EXISTS);
        return task;
    }

    private void validateTaskVersion(PropertyInspectionTaskDO task, Integer version) {
        if (!Objects.equals(task.getVersion(), version)) throw exception(INSPECTION_TASK_STATE_INVALID);
    }

    private void updateTask(PropertyInspectionTaskDO task) {
        if (taskMapper.updateById(task) == 0) throw exception(INSPECTION_TASK_STATE_INVALID);
    }

    private LocalDateTime initialGenerateTime(PropertyInspectionPlanSaveReqVO reqVO) {
        LocalDateTime effective = reqVO.getEffectiveStart().atStartOfDay();
        return effective.isAfter(LocalDateTime.now()) ? effective : LocalDateTime.now();
    }

    private LocalDateTime nextGenerateTime(LocalDateTime current, PropertyInspectionPlanDO plan) {
        return switch (plan.getFrequencyType()) {
            case "WEEKLY" -> current.plusWeeks(plan.getIntervalValue());
            case "MONTHLY" -> current.plusMonths(plan.getIntervalValue());
            default -> current.plusDays(plan.getIntervalValue());
        };
    }

    private String generateNumber(String prefix) {
        return prefix + LocalDateTime.now().format(NUMBER_DATE_FORMATTER)
                + IdUtil.fastSimpleUUID().substring(0, 10).toUpperCase();
    }
}

package cn.iocoder.yudao.module.property.service.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
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
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPlanDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionRecordDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionQualitySampleDO;

import java.util.List;

public interface PropertyInspectionExecutionService {
    Long createPlan(PropertyInspectionPlanSaveReqVO reqVO);
    void updatePlan(PropertyInspectionPlanSaveReqVO reqVO);
    PageResult<PropertyInspectionPlanDO> getPlanPage(PropertyInspectionPageReqVO reqVO);
    Long generateTask(Long planId, Long projectId);
    int generateDueTasks();
    int syncLinkedEventResults();
    PageResult<PropertyInspectionTaskDO> getTaskPage(PropertyInspectionPageReqVO reqVO);
    List<PropertyInspectionRecordDO> getTaskRecords(Long taskId, Long projectId);
    void startTask(PropertyInspectionTaskActionReqVO reqVO);
    Long submitRecord(PropertyInspectionRecordSubmitReqVO reqVO);
    PropertyInspectionOfflineSubmitRespVO submitOfflineRecord(PropertyInspectionRecordSubmitReqVO reqVO);
    void completeTask(PropertyInspectionTaskActionReqVO reqVO);
    void executeTaskResponsibility(PropertyInspectionTaskResponsibilityReqVO reqVO);
    PageResult<PropertyInspectionIssueDO> getIssuePage(PropertyInspectionPageReqVO reqVO);
    void executeIssueAction(PropertyInspectionIssueActionReqVO reqVO);
    PageResult<PropertyInspectionConflictDO> getConflictPage(PropertyInspectionPageReqVO reqVO);
    void resolveConflict(PropertyInspectionConflictResolveReqVO reqVO);
    Long createQualitySample(PropertyInspectionQualitySampleReqVO reqVO);
    void completeQualitySample(PropertyInspectionQualitySampleReqVO reqVO);
    PageResult<PropertyInspectionQualitySampleDO> getQualitySamplePage(PropertyInspectionPageReqVO reqVO);
}

package cn.iocoder.yudao.module.property.controller.admin.inspection;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
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
import cn.iocoder.yudao.module.property.service.inspection.PropertyInspectionExecutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业巡检计划任务与整改")
@RestController
@RequestMapping("/property/inspection")
@Validated
public class PropertyInspectionExecutionController {
    @Resource private PropertyInspectionExecutionService executionService;

    @PostMapping("/plan/create")
    @PreAuthorize("@ss.hasPermission('property:inspection:plan:create')")
    public CommonResult<Long> createPlan(@Valid @RequestBody PropertyInspectionPlanSaveReqVO reqVO) {
        return success(executionService.createPlan(reqVO));
    }

    @PutMapping("/plan/update")
    @PreAuthorize("@ss.hasPermission('property:inspection:plan:update')")
    public CommonResult<Boolean> updatePlan(@Valid @RequestBody PropertyInspectionPlanSaveReqVO reqVO) {
        executionService.updatePlan(reqVO);
        return success(true);
    }

    @GetMapping("/plan/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:plan:query')")
    public CommonResult<PageResult<PropertyInspectionPlanDO>> planPage(@Valid PropertyInspectionPageReqVO reqVO) {
        return success(executionService.getPlanPage(reqVO));
    }

    @PostMapping("/plan/generate-task")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:create')")
    public CommonResult<Long> generateTask(@RequestParam Long planId, @RequestParam Long projectId) {
        return success(executionService.generateTask(planId, projectId));
    }

    @GetMapping("/task/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:query')")
    public CommonResult<PageResult<PropertyInspectionTaskDO>> taskPage(@Valid PropertyInspectionPageReqVO reqVO) {
        return success(executionService.getTaskPage(reqVO));
    }

    @GetMapping("/task/records")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:query')")
    public CommonResult<List<PropertyInspectionRecordDO>> taskRecords(
            @RequestParam Long taskId, @RequestParam Long projectId) {
        return success(executionService.getTaskRecords(taskId, projectId));
    }

    @PutMapping("/task/start")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:execute')")
    public CommonResult<Boolean> startTask(@Valid @RequestBody PropertyInspectionTaskActionReqVO reqVO) {
        executionService.startTask(reqVO);
        return success(true);
    }

    @PostMapping("/task/submit-record")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:execute')")
    public CommonResult<Long> submitRecord(@Valid @RequestBody PropertyInspectionRecordSubmitReqVO reqVO) {
        return success(executionService.submitRecord(reqVO));
    }

    @PostMapping("/task/offline-submit")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:execute')")
    public CommonResult<PropertyInspectionOfflineSubmitRespVO> submitOfflineRecord(
            @Valid @RequestBody PropertyInspectionRecordSubmitReqVO reqVO) {
        return success(executionService.submitOfflineRecord(reqVO));
    }

    @PutMapping("/task/complete")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:execute')")
    public CommonResult<Boolean> completeTask(@Valid @RequestBody PropertyInspectionTaskActionReqVO reqVO) {
        executionService.completeTask(reqVO);
        return success(true);
    }

    @PutMapping("/task/responsibility")
    @PreAuthorize("@ss.hasPermission('property:inspection:task:dispatch')")
    public CommonResult<Boolean> taskResponsibility(
            @Valid @RequestBody PropertyInspectionTaskResponsibilityReqVO reqVO) {
        executionService.executeTaskResponsibility(reqVO);
        return success(true);
    }

    @GetMapping("/issue/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:issue:query')")
    public CommonResult<PageResult<PropertyInspectionIssueDO>> issuePage(@Valid PropertyInspectionPageReqVO reqVO) {
        return success(executionService.getIssuePage(reqVO));
    }

    @PutMapping("/issue/action")
    @PreAuthorize("@ss.hasPermission('property:inspection:issue:update')")
    public CommonResult<Boolean> issueAction(@Valid @RequestBody PropertyInspectionIssueActionReqVO reqVO) {
        executionService.executeIssueAction(reqVO);
        return success(true);
    }

    @GetMapping("/conflict/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:conflict:query')")
    public CommonResult<PageResult<PropertyInspectionConflictDO>> conflictPage(
            @Valid PropertyInspectionPageReqVO reqVO) {
        return success(executionService.getConflictPage(reqVO));
    }

    @PutMapping("/conflict/resolve")
    @PreAuthorize("@ss.hasPermission('property:inspection:conflict:resolve')")
    public CommonResult<Boolean> resolveConflict(@Valid @RequestBody PropertyInspectionConflictResolveReqVO reqVO) {
        executionService.resolveConflict(reqVO);
        return success(true);
    }

    @PostMapping("/quality/create")
    @PreAuthorize("@ss.hasPermission('property:inspection:quality:create')")
    public CommonResult<Long> createQuality(@Valid @RequestBody PropertyInspectionQualitySampleReqVO reqVO) {
        return success(executionService.createQualitySample(reqVO));
    }

    @PutMapping("/quality/complete")
    @PreAuthorize("@ss.hasPermission('property:inspection:quality:update')")
    public CommonResult<Boolean> completeQuality(@Valid @RequestBody PropertyInspectionQualitySampleReqVO reqVO) {
        executionService.completeQualitySample(reqVO);
        return success(true);
    }

    @GetMapping("/quality/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:quality:query')")
    public CommonResult<PageResult<PropertyInspectionQualitySampleDO>> qualityPage(
            @Valid PropertyInspectionPageReqVO reqVO) {
        return success(executionService.getQualitySamplePage(reqVO));
    }
}

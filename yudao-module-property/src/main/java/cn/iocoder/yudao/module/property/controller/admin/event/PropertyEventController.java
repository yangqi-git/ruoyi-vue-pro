package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
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
import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;
import cn.iocoder.yudao.module.property.service.event.PropertyEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业事件中心")
@RestController
@RequestMapping("/property/event")
@Validated
public class PropertyEventController {

    @Resource
    private PropertyEventService eventService;

    @PostMapping("/create")
    @Operation(summary = "受理物业事件")
    @PreAuthorize("@ss.hasPermission('property:event:create')")
    public CommonResult<Long> createEvent(@Valid @RequestBody PropertyEventCreateReqVO reqVO) {
        return success(eventService.createEvent(reqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获得物业事件分页")
    @PreAuthorize("@ss.hasPermission('property:event:query')")
    public CommonResult<PageResult<PropertyEventDO>> getEventPage(@Valid PropertyEventPageReqVO reqVO) {
        return success(eventService.getEventPage(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得物业事件详情、工单、证据和时间线")
    @PreAuthorize("@ss.hasPermission('property:event:query')")
    public CommonResult<PropertyEventDetailRespVO> getEventDetail(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("projectId") @NotNull Long projectId) {
        return success(eventService.getEventDetail(id, projectId));
    }

    @GetMapping("/stats")
    @Operation(summary = "获得事件指挥台统计")
    @PreAuthorize("@ss.hasPermission('property:event:query')")
    public CommonResult<PropertyEventStatsRespVO> getEventStats(
            @RequestParam("projectId") @NotNull Long projectId) {
        return success(eventService.getEventStats(projectId));
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认有效事件")
    @PreAuthorize("@ss.hasPermission('property:event:confirm')")
    public CommonResult<Boolean> confirmEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        eventService.transitionEvent(reqVO, PropertyEventActionEnum.CONFIRM);
        return success(true);
    }

    @PutMapping("/cancel")
    @Operation(summary = "取消无效或重复事件")
    @PreAuthorize("@ss.hasPermission('property:event:cancel')")
    public CommonResult<Boolean> cancelEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        eventService.transitionEvent(reqVO, PropertyEventActionEnum.CANCEL);
        return success(true);
    }

    @PostMapping("/dispatch")
    @Operation(summary = "派发事件工单")
    @PreAuthorize("@ss.hasPermission('property:event:dispatch')")
    public CommonResult<Boolean> dispatchEvent(@Valid @RequestBody PropertyEventDispatchReqVO reqVO) {
        eventService.dispatchEvent(reqVO);
        return success(true);
    }

    @PostMapping("/transfer")
    @Operation(summary = "受控转派事件工单（不重置原 SLA）")
    @PreAuthorize("@ss.hasPermission('property:event:dispatch')")
    public CommonResult<Boolean> transferEvent(@Valid @RequestBody PropertyEventTransferReqVO reqVO) {
        eventService.transferEvent(reqVO);
        return success(true);
    }

    @PostMapping("/merge")
    @Operation(summary = "将重复事件合并到主事件")
    @PreAuthorize("@ss.hasPermission('property:event:merge')")
    public CommonResult<Boolean> mergeEvent(@Valid @RequestBody PropertyEventMergeReqVO reqVO) {
        eventService.mergeEvent(reqVO);
        return success(true);
    }

    @PostMapping("/split")
    @Operation(summary = "从事件拆分独立子事件")
    @PreAuthorize("@ss.hasPermission('property:event:split')")
    public CommonResult<Long> splitEvent(@Valid @RequestBody PropertyEventSplitReqVO reqVO) {
        return success(eventService.splitEvent(reqVO));
    }

    @PutMapping("/accept")
    @Operation(summary = "执行人接单")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> acceptEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.ACCEPT);
    }

    @PutMapping("/reject-order")
    @Operation(summary = "执行人拒单并退回调度池")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> rejectWorkOrder(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.REJECT_ORDER);
    }

    @PutMapping("/arrive")
    @Operation(summary = "执行人到场")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> arriveEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.ARRIVE);
    }

    @PutMapping("/start")
    @Operation(summary = "开始处置事件")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> startEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.START);
    }

    @PutMapping("/collaborate")
    @Operation(summary = "事件进入协作等待")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> collaborateEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.COLLABORATE);
    }

    @PutMapping("/resume")
    @Operation(summary = "解除协作阻塞")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> resumeEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.RESUME);
    }

    @PutMapping("/submit-acceptance")
    @Operation(summary = "提交事件验收")
    @PreAuthorize("@ss.hasPermission('property:event:execute')")
    public CommonResult<Boolean> submitAcceptance(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.SUBMIT_ACCEPTANCE);
    }

    @PutMapping("/approve")
    @Operation(summary = "验收通过并关闭事件")
    @PreAuthorize("@ss.hasPermission('property:event:acceptance')")
    public CommonResult<Boolean> approveEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.APPROVE);
    }

    @PutMapping("/reject")
    @Operation(summary = "验收退回")
    @PreAuthorize("@ss.hasPermission('property:event:acceptance')")
    public CommonResult<Boolean> rejectEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.REJECT);
    }

    @PutMapping("/reopen")
    @Operation(summary = "复开已关闭事件")
    @PreAuthorize("@ss.hasPermission('property:event:reopen')")
    public CommonResult<Boolean> reopenEvent(@Valid @RequestBody PropertyEventActionReqVO reqVO) {
        return execute(reqVO, PropertyEventActionEnum.REOPEN);
    }

    @PostMapping("/evidence/create")
    @Operation(summary = "上传事件处置证据")
    @PreAuthorize("@ss.hasPermission('property:event:evidence:create')")
    public CommonResult<Long> addEvidence(@Valid @RequestBody PropertyEventEvidenceCreateReqVO reqVO) {
        return success(eventService.addEvidence(reqVO));
    }

    private CommonResult<Boolean> execute(PropertyEventActionReqVO reqVO, PropertyEventActionEnum action) {
        eventService.transitionEvent(reqVO, action);
        return success(true);
    }
}

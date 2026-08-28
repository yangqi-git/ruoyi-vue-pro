package cn.iocoder.yudao.module.property.controller.admin.settlement;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyApproveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.settlement.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.settlement.PropertySettlementBatchDO;
import cn.iocoder.yudao.module.property.service.settlement.PropertySettlementBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 结算批次")
@RestController @RequestMapping("/property/settlement/batch") @Validated
public class PropertySettlementBatchController {

    @Resource private PropertySettlementBatchService settlementBatchService;

    @PostMapping("/create") @Operation(summary = "创建结算批次")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertySettlementBatchSaveReqVO reqVO) {
        return success(settlementBatchService.createSettlementBatch(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新结算批次")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertySettlementBatchSaveReqVO reqVO) {
        settlementBatchService.updateSettlementBatch(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除结算批次")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        settlementBatchService.deleteSettlementBatch(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得结算批次")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:query')")
    public CommonResult<PropertySettlementBatchDO> get(@RequestParam("id") Long id) {
        return success(settlementBatchService.getSettlementBatch(id));
    }

    @GetMapping("/page") @Operation(summary = "结算批次分页")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:query')")
    public CommonResult<PageResult<PropertySettlementBatchDO>> page(@Valid PropertySettlementBatchPageReqVO pageReqVO) {
        return success(settlementBatchService.getSettlementBatchPage(pageReqVO.getProjectId(), pageReqVO.getCommunityId(),
                pageReqVO.getBatchType(), pageReqVO.getBatchStatus(), pageReqVO.getApprovalStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取结算批次列表")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:query')")
    public CommonResult<List<PropertySettlementBatchDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(settlementBatchService.getSettlementBatchListByCommunityId(communityId));
    }

    @PostMapping("/approve") @Operation(summary = "审批结算批次")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:approve')")
    public CommonResult<Boolean> approve(@Valid @RequestBody PropertyApproveReqVO reqVO) {
        settlementBatchService.approveSettlementBatch(reqVO.getId(), null, reqVO.getApprovalStatus(), reqVO.getApprovalRemark());
        return success(true);
    }

    @PostMapping("/settle") @Operation(summary = "执行结算")
    @PreAuthorize("@ss.hasPermission('property:settlement:batch:settle')")
    public CommonResult<Boolean> settle(@RequestParam("id") Long id) {
        settlementBatchService.settleBatch(id, null);
        return success(true);
    }
}

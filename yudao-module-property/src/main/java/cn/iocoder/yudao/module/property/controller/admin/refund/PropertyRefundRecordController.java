package cn.iocoder.yudao.module.property.controller.admin.refund;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundRecordDO;
import cn.iocoder.yudao.module.property.service.refund.PropertyRefundRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 退款单")
@RestController @RequestMapping("/property/refund/record") @Validated
public class PropertyRefundRecordController {

    @Resource private PropertyRefundRecordService refundService;

    @PostMapping("/create") @Operation(summary = "创建退款单")
    @PreAuthorize("@ss.hasPermission('property:refund:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyRefundRecordSaveReqVO reqVO) {
        return success(refundService.createRefund(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新退款单")
    @PreAuthorize("@ss.hasPermission('property:refund:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyRefundRecordSaveReqVO reqVO) {
        refundService.updateRefund(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除退款单")
    @PreAuthorize("@ss.hasPermission('property:refund:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        refundService.deleteRefund(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得退款单")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<PropertyRefundRecordDO> get(@RequestParam("id") Long id) {
        return success(refundService.getRefund(id));
    }

    @GetMapping("/page") @Operation(summary = "退款单分页")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<PageResult<PropertyRefundRecordDO>> page(@Valid PropertyRefundRecordPageReqVO pageReqVO) {
        return success(refundService.getRefundPage(pageReqVO.getProjectId(), pageReqVO.getRefundNo(), pageReqVO.getCashierRecordId(),
                pageReqVO.getBillId(), pageReqVO.getHouseId(), pageReqVO.getRefundType(),
                pageReqVO.getRefundStatus(), pageReqVO.getApprovalStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "退款单列表")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<List<PropertyRefundRecordDO>> list() {
        return success(refundService.getRefundList());
    }
}

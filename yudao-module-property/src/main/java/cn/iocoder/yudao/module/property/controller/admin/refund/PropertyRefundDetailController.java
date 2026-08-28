package cn.iocoder.yudao.module.property.controller.admin.refund;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundDetailDO;
import cn.iocoder.yudao.module.property.service.refund.PropertyRefundDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 退款明细")
@RestController @RequestMapping("/property/refund/detail") @Validated
public class PropertyRefundDetailController {

    @Resource private PropertyRefundDetailService detailService;

    @PostMapping("/create") @Operation(summary = "创建退款明细")
    @PreAuthorize("@ss.hasPermission('property:refund:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyRefundDetailSaveReqVO reqVO) {
        return success(detailService.createDetail(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新退款明细")
    @PreAuthorize("@ss.hasPermission('property:refund:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyRefundDetailSaveReqVO reqVO) {
        detailService.updateDetail(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除退款明细")
    @PreAuthorize("@ss.hasPermission('property:refund:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        detailService.deleteDetail(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得退款明细")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<PropertyRefundDetailDO> get(@RequestParam("id") Long id) {
        return success(detailService.getDetail(id));
    }

    @GetMapping("/page") @Operation(summary = "退款明细分页")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<PageResult<PropertyRefundDetailDO>> page(@Valid PropertyRefundDetailPageReqVO pageReqVO) {
        return success(detailService.getDetailPage(pageReqVO.getRefundId(), pageReqVO.getBillDetailId(),
                pageReqVO.getRefundStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "退款明细列表")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<List<PropertyRefundDetailDO>> list() {
        return success(detailService.getDetailList());
    }

    @GetMapping("/list-by-refund") @Operation(summary = "按退款单获取明细列表")
    @PreAuthorize("@ss.hasPermission('property:refund:query')")
    public CommonResult<List<PropertyRefundDetailDO>> listByRefund(@RequestParam("refundId") Long refundId) {
        return success(detailService.getDetailListByRefundId(refundId));
    }
}
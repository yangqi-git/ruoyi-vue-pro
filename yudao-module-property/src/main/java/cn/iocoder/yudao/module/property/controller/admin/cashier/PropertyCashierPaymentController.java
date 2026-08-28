package cn.iocoder.yudao.module.property.controller.admin.cashier;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierPaymentDO;
import cn.iocoder.yudao.module.property.service.cashier.PropertyCashierPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 支付渠道明细")
@RestController @RequestMapping("/property/cashier/payment") @Validated
public class PropertyCashierPaymentController {

    @Resource private PropertyCashierPaymentService paymentService;

    @PostMapping("/create") @Operation(summary = "创建支付渠道明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyCashierPaymentSaveReqVO reqVO) {
        return success(paymentService.createPayment(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新支付渠道明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyCashierPaymentSaveReqVO reqVO) {
        paymentService.updatePayment(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除支付渠道明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        paymentService.deletePayment(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得支付渠道明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PropertyCashierPaymentDO> get(@RequestParam("id") Long id) {
        return success(paymentService.getPayment(id));
    }

    @GetMapping("/page") @Operation(summary = "支付渠道明细分页")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PageResult<PropertyCashierPaymentDO>> page(@Valid PropertyCashierPaymentPageReqVO pageReqVO) {
        return success(paymentService.getPaymentPage(pageReqVO.getRecordId(), pageReqVO.getPayChannel(),
                pageReqVO.getPayStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "支付渠道明细列表")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyCashierPaymentDO>> list() {
        return success(paymentService.getPaymentList());
    }

    @GetMapping("/list-by-record") @Operation(summary = "按收银记录获取支付明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyCashierPaymentDO>> listByRecord(@RequestParam("recordId") Long recordId) {
        return success(paymentService.getPaymentListByRecordId(recordId));
    }
}
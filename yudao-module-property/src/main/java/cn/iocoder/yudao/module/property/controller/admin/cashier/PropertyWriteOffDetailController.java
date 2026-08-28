package cn.iocoder.yudao.module.property.controller.admin.cashier;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyWriteOffDetailDO;
import cn.iocoder.yudao.module.property.service.cashier.PropertyWriteOffDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 核销明细")
@RestController @RequestMapping("/property/cashier/writeoff") @Validated
public class PropertyWriteOffDetailController {

    @Resource private PropertyWriteOffDetailService detailService;

    @PostMapping("/create") @Operation(summary = "创建核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyWriteOffDetailSaveReqVO reqVO) {
        return success(detailService.createDetail(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyWriteOffDetailSaveReqVO reqVO) {
        detailService.updateDetail(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        detailService.deleteDetail(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PropertyWriteOffDetailDO> get(@RequestParam("id") Long id) {
        return success(detailService.getDetail(id));
    }

    @GetMapping("/page") @Operation(summary = "核销明细分页")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PageResult<PropertyWriteOffDetailDO>> page(@Valid PropertyWriteOffDetailPageReqVO pageReqVO) {
        return success(detailService.getDetailPage(pageReqVO.getRecordId(), pageReqVO.getBillId(),
                pageReqVO.getWriteOffType(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "核销明细列表")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyWriteOffDetailDO>> list() {
        return success(detailService.getDetailList());
    }

    @GetMapping("/list-by-record") @Operation(summary = "按收银记录获取核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyWriteOffDetailDO>> listByRecord(@RequestParam("recordId") Long recordId) {
        return success(detailService.getDetailListByRecordId(recordId));
    }

    @GetMapping("/list-by-bill") @Operation(summary = "按账单获取核销明细")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyWriteOffDetailDO>> listByBill(@RequestParam("billId") Long billId) {
        return success(detailService.getDetailListByBillId(billId));
    }
}
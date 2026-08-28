package cn.iocoder.yudao.module.property.controller.admin.bill;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyBillDetailDO;
import cn.iocoder.yudao.module.property.service.bill.PropertyBillDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 账单明细")
@RestController @RequestMapping("/property/bill/detail") @Validated
public class PropertyBillDetailController {

    @Resource private PropertyBillDetailService detailService;

    @PostMapping("/create") @Operation(summary = "创建账单明细")
    @PreAuthorize("@ss.hasPermission('property:bill:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyBillDetailSaveReqVO reqVO) {
        return success(detailService.createDetail(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新账单明细")
    @PreAuthorize("@ss.hasPermission('property:bill:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyBillDetailSaveReqVO reqVO) {
        detailService.updateDetail(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除账单明细")
    @PreAuthorize("@ss.hasPermission('property:bill:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        detailService.deleteDetail(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得账单明细")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<PropertyBillDetailDO> get(@RequestParam("id") Long id) {
        return success(detailService.getDetail(id));
    }

    @GetMapping("/page") @Operation(summary = "账单明细分页")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<PageResult<PropertyBillDetailDO>> page(@Valid PropertyBillDetailPageReqVO pageReqVO) {
        return success(detailService.getDetailPage(pageReqVO.getBillId(), pageReqVO.getItemId(),
                pageReqVO.getPayStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "账单明细列表")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<List<PropertyBillDetailDO>> list() {
        return success(detailService.getDetailList());
    }

    @GetMapping("/list-by-bill") @Operation(summary = "按账单获取明细列表")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<List<PropertyBillDetailDO>> listByBill(@RequestParam("billId") Long billId) {
        return success(detailService.getDetailListByBillId(billId));
    }
}
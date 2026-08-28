package cn.iocoder.yudao.module.property.controller.admin.cashier;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierRecordDO;
import cn.iocoder.yudao.module.property.service.cashier.PropertyCashierRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 收银记录")
@RestController @RequestMapping("/property/cashier/record") @Validated
public class PropertyCashierRecordController {

    @Resource
    private PropertyCashierRecordService cashierRecordService;

    @PostMapping("/create") @Operation(summary = "创建收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyCashierRecordSaveReqVO reqVO) {
        return success(cashierRecordService.createRecord(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyCashierRecordSaveReqVO reqVO) {
        cashierRecordService.updateRecord(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        cashierRecordService.deleteRecord(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PropertyCashierRecordDO> get(@RequestParam("id") Long id) {
        return success(cashierRecordService.getRecord(id));
    }

    @GetMapping("/page") @Operation(summary = "收银记录分页")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<PageResult<PropertyCashierRecordDO>> page(@Valid PropertyCashierRecordPageReqVO pageReqVO) {
        return success(cashierRecordService.getRecordPage(pageReqVO.getProjectId(), pageReqVO.getRecordNo(), pageReqVO.getBillId(),
                pageReqVO.getHouseId(), pageReqVO.getCommunityId(), pageReqVO.getPayType(),
                pageReqVO.getPayStatus(), pageReqVO.getRecordStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "收银记录列表")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyCashierRecordDO>> list() {
        return success(cashierRecordService.getRecordList());
    }

    @GetMapping("/list-by-bill") @Operation(summary = "按账单获取收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyCashierRecordDO>> listByBill(@RequestParam("billId") Long billId) {
        return success(cashierRecordService.getRecordListByBillId(billId));
    }

    @GetMapping("/list-by-house") @Operation(summary = "按房屋获取收银记录")
    @PreAuthorize("@ss.hasPermission('property:cashier:query')")
    public CommonResult<List<PropertyCashierRecordDO>> listByHouse(@RequestParam("houseId") Long houseId) {
        return success(cashierRecordService.getRecordListByHouseId(houseId));
    }
}

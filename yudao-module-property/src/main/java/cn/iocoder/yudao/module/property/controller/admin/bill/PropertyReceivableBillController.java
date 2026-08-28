package cn.iocoder.yudao.module.property.controller.admin.bill;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyReceivableBillDO;
import cn.iocoder.yudao.module.property.service.bill.PropertyReceivableBillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 应收账单")
@RestController @RequestMapping("/property/bill/receivable") @Validated
public class PropertyReceivableBillController {

    @Resource private PropertyReceivableBillService billService;

    @PostMapping("/create") @Operation(summary = "创建应收账单")
    @PreAuthorize("@ss.hasPermission('property:bill:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyReceivableBillSaveReqVO reqVO) {
        return success(billService.createBill(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新应收账单")
    @PreAuthorize("@ss.hasPermission('property:bill:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyReceivableBillSaveReqVO reqVO) {
        billService.updateBill(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除应收账单")
    @PreAuthorize("@ss.hasPermission('property:bill:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        billService.deleteBill(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得应收账单")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<PropertyReceivableBillDO> get(@RequestParam("id") Long id) {
        return success(billService.getBill(id));
    }

    @GetMapping("/page") @Operation(summary = "应收账单分页")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<PageResult<PropertyReceivableBillDO>> page(@Valid PropertyReceivableBillPageReqVO pageReqVO) {
        return success(billService.getBillPage(pageReqVO.getBillNo(), pageReqVO.getHouseId(),
                pageReqVO.getCommunityId(), pageReqVO.getProjectId(), pageReqVO.getBillType(),
                pageReqVO.getPayStatus(), pageReqVO.getBillStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "应收账单列表")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<List<PropertyReceivableBillDO>> list() {
        return success(billService.getBillList());
    }

    @GetMapping("/list-by-house") @Operation(summary = "按房屋获取账单列表")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<List<PropertyReceivableBillDO>> listByHouse(@RequestParam("houseId") Long houseId) {
        return success(billService.getBillListByHouseId(houseId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取账单列表")
    @PreAuthorize("@ss.hasPermission('property:bill:query')")
    public CommonResult<List<PropertyReceivableBillDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(billService.getBillListByCommunityId(communityId));
    }
}
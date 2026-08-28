package cn.iocoder.yudao.module.property.controller.admin.discount;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyDiscountRecordDO;
import cn.iocoder.yudao.module.property.service.discount.PropertyDiscountRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 优惠记录")
@RestController @RequestMapping("/property/discount/record") @Validated
public class PropertyDiscountRecordController {

    @Resource private PropertyDiscountRecordService discountRecordService;

    @PostMapping("/create") @Operation(summary = "创建优惠记录")
    @PreAuthorize("@ss.hasPermission('property:discount:record:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyDiscountRecordSaveReqVO reqVO) {
        return success(discountRecordService.createDiscountRecord(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新优惠记录")
    @PreAuthorize("@ss.hasPermission('property:discount:record:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyDiscountRecordSaveReqVO reqVO) {
        discountRecordService.updateDiscountRecord(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除优惠记录")
    @PreAuthorize("@ss.hasPermission('property:discount:record:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        discountRecordService.deleteDiscountRecord(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得优惠记录")
    @PreAuthorize("@ss.hasPermission('property:discount:record:query')")
    public CommonResult<PropertyDiscountRecordDO> get(@RequestParam("id") Long id) {
        return success(discountRecordService.getDiscountRecord(id));
    }

    @GetMapping("/page") @Operation(summary = "优惠记录分页")
    @PreAuthorize("@ss.hasPermission('property:discount:record:query')")
    public CommonResult<PageResult<PropertyDiscountRecordDO>> page(@Valid PropertyDiscountRecordPageReqVO pageReqVO) {
        return success(discountRecordService.getDiscountRecordPage(pageReqVO.getProjectId(), pageReqVO.getCommunityId(), pageReqVO.getHouseId(),
                pageReqVO.getBillId(), pageReqVO.getDiscountType(), pageReqVO.getApprovalStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-bill") @Operation(summary = "按账单获取优惠记录列表")
    @PreAuthorize("@ss.hasPermission('property:discount:record:query')")
    public CommonResult<List<PropertyDiscountRecordDO>> listByBill(@RequestParam("billId") Long billId) {
        return success(discountRecordService.getDiscountRecordListByBillId(billId));
    }

    @PostMapping("/approve") @Operation(summary = "审批优惠记录")
    @PreAuthorize("@ss.hasPermission('property:discount:record:approve')")
    public CommonResult<Boolean> approve(@Valid @RequestBody PropertyApproveReqVO reqVO) {
        discountRecordService.approveDiscountRecord(reqVO.getId(), null, reqVO.getApprovalStatus(), reqVO.getApprovalRemark());
        return success(true);
    }
}

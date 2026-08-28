package cn.iocoder.yudao.module.property.controller.admin.discount;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyAdjustRecordDO;
import cn.iocoder.yudao.module.property.service.discount.PropertyAdjustRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 调价记录")
@RestController @RequestMapping("/property/discount/adjust") @Validated
public class PropertyAdjustRecordController {

    @Resource private PropertyAdjustRecordService adjustRecordService;

    @PostMapping("/create") @Operation(summary = "创建调价记录")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyAdjustRecordSaveReqVO reqVO) {
        return success(adjustRecordService.createAdjustRecord(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新调价记录")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyAdjustRecordSaveReqVO reqVO) {
        adjustRecordService.updateAdjustRecord(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除调价记录")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        adjustRecordService.deleteAdjustRecord(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得调价记录")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:query')")
    public CommonResult<PropertyAdjustRecordDO> get(@RequestParam("id") Long id) {
        return success(adjustRecordService.getAdjustRecord(id));
    }

    @GetMapping("/page") @Operation(summary = "调价记录分页")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:query')")
    public CommonResult<PageResult<PropertyAdjustRecordDO>> page(@Valid PropertyAdjustRecordPageReqVO pageReqVO) {
        return success(adjustRecordService.getAdjustRecordPage(pageReqVO.getCommunityId(), pageReqVO.getHouseId(),
                pageReqVO.getBillId(), pageReqVO.getAdjustType(), pageReqVO.getApprovalStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-bill") @Operation(summary = "按账单获取调价记录列表")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:query')")
    public CommonResult<List<PropertyAdjustRecordDO>> listByBill(@RequestParam("billId") Long billId) {
        return success(adjustRecordService.getAdjustRecordListByBillId(billId));
    }

    @PostMapping("/approve") @Operation(summary = "审批调价记录")
    @PreAuthorize("@ss.hasPermission('property:discount:adjust:approve')")
    public CommonResult<Boolean> approve(@Valid @RequestBody PropertyApproveReqVO reqVO) {
        adjustRecordService.approveAdjustRecord(reqVO.getId(), null, reqVO.getApprovalStatus(), reqVO.getApprovalRemark());
        return success(true);
    }
}
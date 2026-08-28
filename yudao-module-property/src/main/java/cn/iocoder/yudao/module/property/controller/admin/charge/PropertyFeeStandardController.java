package cn.iocoder.yudao.module.property.controller.admin.charge;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.PropertyFeeStandardDO;
import cn.iocoder.yudao.module.property.service.charge.PropertyFeeStandardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业费标准")
@RestController @RequestMapping("/property/charge/fee-standard") @Validated
public class PropertyFeeStandardController {

    @Resource private PropertyFeeStandardService feeStandardService;

    @PostMapping("/create") @Operation(summary = "创建费用标准")
    @PreAuthorize("@ss.hasPermission('property:charge:fee-standard:create')")
    public CommonResult<Long> create(@Valid @RequestBody FeeStandardSaveReqVO reqVO) {
        return success(feeStandardService.createFeeStandard(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新费用标准")
    @PreAuthorize("@ss.hasPermission('property:charge:fee-standard:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody FeeStandardSaveReqVO reqVO) {
        feeStandardService.updateFeeStandard(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除费用标准")
    @PreAuthorize("@ss.hasPermission('property:charge:fee-standard:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        feeStandardService.deleteFeeStandard(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得费用标准")
    @PreAuthorize("@ss.hasPermission('property:charge:fee-standard:query')")
    public CommonResult<PropertyFeeStandardDO> get(@RequestParam("id") Long id) {
        return success(feeStandardService.getFeeStandard(id));
    }

    @GetMapping("/page") @Operation(summary = "费用标准分页")
    @PreAuthorize("@ss.hasPermission('property:charge:fee-standard:query')")
    public CommonResult<PageResult<PropertyFeeStandardDO>> page(@Valid FeeStandardPageReqVO pageReqVO) {
        return success(feeStandardService.getFeeStandardPage(pageReqVO));
    }
}

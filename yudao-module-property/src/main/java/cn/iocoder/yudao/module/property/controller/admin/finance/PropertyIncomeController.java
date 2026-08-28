package cn.iocoder.yudao.module.property.controller.admin.finance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.finance.vo.income.*;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyIncomeDO;
import cn.iocoder.yudao.module.property.service.finance.PropertyIncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 收入管理")
@RestController @RequestMapping("/property/finance/income") @Validated
public class PropertyIncomeController {

    @Resource private PropertyIncomeService incomeService;

    @PostMapping("/confirm") @Operation(summary = "确认收入")
    @PreAuthorize("@ss.hasPermission('property:finance:income:confirm')")
    public CommonResult<Boolean> confirm(@Valid @RequestBody PropertyIncomeDO income) {
        incomeService.confirmIncome(income.getId(), income.getConfirmedBy());
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得收入记录")
    @PreAuthorize("@ss.hasPermission('property:finance:income:query')")
    public CommonResult<PropertyIncomeDO> get(@RequestParam("id") Long id) {
        return success(incomeService.getIncome(id));
    }

    @GetMapping("/page") @Operation(summary = "收入记录分页")
    @PreAuthorize("@ss.hasPermission('property:finance:income:query')")
    public CommonResult<PageResult<PropertyIncomeDO>> page(@Valid PropertyIncomePageReqVO pageReqVO) {
        return success(incomeService.getIncomePage(pageReqVO.getPayerId(), pageReqVO.getIncomeType(),
                pageReqVO.getPayChannel(), pageReqVO.getConfirmed(),
                pageReqVO.getBeginDate(), pageReqVO.getEndDate(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }
}

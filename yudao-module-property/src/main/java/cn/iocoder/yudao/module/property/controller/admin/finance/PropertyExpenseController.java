package cn.iocoder.yudao.module.property.controller.admin.finance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.finance.vo.expense.*;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyExpenseDO;
import cn.iocoder.yudao.module.property.service.finance.PropertyExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.object.BeanUtils.toBean;

@Tag(name = "管理后台 - 支出管理")
@RestController @RequestMapping("/property/finance/expense") @Validated
public class PropertyExpenseController {

    @Resource private PropertyExpenseService expenseService;

    @PostMapping("/create") @Operation(summary = "创建支出记录")
    @PreAuthorize("@ss.hasPermission('property:finance:expense:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyExpenseSaveReqVO reqVO) {
        return success(expenseService.createExpense(toBean(reqVO, PropertyExpenseDO.class)));
    }

    @PutMapping("/approve") @Operation(summary = "审批支出")
    @PreAuthorize("@ss.hasPermission('property:finance:expense:approve')")
    public CommonResult<Boolean> approve(@Valid @RequestBody PropertyExpenseApproveReqVO reqVO) {
        expenseService.approveExpense(reqVO.getId(), reqVO.getApprovalStatus(), null);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得支出记录")
    @PreAuthorize("@ss.hasPermission('property:finance:expense:query')")
    public CommonResult<PropertyExpenseDO> get(@RequestParam("id") Long id) {
        return success(expenseService.getExpense(id));
    }

    @GetMapping("/page") @Operation(summary = "支出记录分页")
    @PreAuthorize("@ss.hasPermission('property:finance:expense:query')")
    public CommonResult<PageResult<PropertyExpenseDO>> page(@Valid PropertyExpensePageReqVO pageReqVO) {
        return success(expenseService.getExpensePage(pageReqVO.getExpenseType(), pageReqVO.getExpenseCategory(),
                pageReqVO.getApprovalStatus(), pageReqVO.getBeginDate(), pageReqVO.getEndDate(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }
}

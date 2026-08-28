package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyExpenseDO;
import jakarta.validation.Valid;

public interface PropertyExpenseService {
    Long createExpense(@Valid PropertyExpenseDO expense);
    void approveExpense(Long id, Integer approvalStatus, String approvedBy);
    PropertyExpenseDO getExpense(Long id);
    PageResult<PropertyExpenseDO> getExpensePage(String expenseType, String expenseCategory, Integer approvalStatus,
                                                  String beginDate, String endDate, Integer pageNo, Integer pageSize);
}

package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyExpenseDO;
import cn.iocoder.yudao.module.property.dal.mysql.finance.PropertyExpenseMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.object.BeanUtils.toBean;

@Service @Slf4j @Validated
public class PropertyExpenseServiceImpl implements PropertyExpenseService {

    @Resource private PropertyExpenseMapper mapper;

    @Override @Transactional(rollbackFor = Exception.class)
    public Long createExpense(PropertyExpenseDO expense) {
        PropertyExpenseDO entity = toBean(expense, PropertyExpenseDO.class);
        entity.setApprovalStatus(0);
        entity.setExpenseDate(LocalDate.now());
        mapper.insert(entity);
        return entity.getId();
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void approveExpense(Long id, Integer approvalStatus, String approvedBy) {
        PropertyExpenseDO exist = validateExists(id);
        if (exist.getApprovalStatus() != 0) throw exception(ErrorCodeConstants.EXPENSE_APPROVAL_NOT_PENDING);
        exist.setApprovalStatus(approvalStatus);
        exist.setApprovedBy(approvedBy);
        exist.setApprovedTime(LocalDateTime.now());
        mapper.updateById(exist);
    }

    @Override public PropertyExpenseDO getExpense(Long id) {
        return validateExists(id);
    }

    @Override public PageResult<PropertyExpenseDO> getExpensePage(String expenseType, String expenseCategory,
                                                                  Integer approvalStatus, String beginDate, String endDate,
                                                                  Integer pageNo, Integer pageSize) {
        return mapper.selectPage(expenseType, expenseCategory, approvalStatus,
                beginDate != null ? LocalDate.parse(beginDate) : null,
                endDate != null ? LocalDate.parse(endDate) : null);
    }

    private PropertyExpenseDO validateExists(Long id) {
        PropertyExpenseDO entity = mapper.selectById(id);
        if (entity == null) throw exception(ErrorCodeConstants.EXPENSE_NOT_EXISTS);
        return entity;
    }
}

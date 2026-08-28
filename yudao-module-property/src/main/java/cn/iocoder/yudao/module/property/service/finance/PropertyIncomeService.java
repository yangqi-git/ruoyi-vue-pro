package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyIncomeDO;
import jakarta.validation.Valid;

public interface PropertyIncomeService {
    Long createIncome(@Valid PropertyIncomeDO income);
    void confirmIncome(Long id, String confirmedBy);
    PropertyIncomeDO getIncome(Long id);
    PageResult<PropertyIncomeDO> getIncomePage(Long payerId, String incomeType, String payChannel,
                                               Integer confirmed, String beginDate, String endDate, Integer pageNo, Integer pageSize);
}

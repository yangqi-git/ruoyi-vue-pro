package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyIncomeDO;
import cn.iocoder.yudao.module.property.dal.mysql.finance.PropertyIncomeMapper;
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
public class PropertyIncomeServiceImpl implements PropertyIncomeService {

    @Resource private PropertyIncomeMapper mapper;

    @Override @Transactional(rollbackFor = Exception.class)
    public Long createIncome(PropertyIncomeDO income) {
        PropertyIncomeDO entity = toBean(income, PropertyIncomeDO.class);
        entity.setConfirmed(0);
        entity.setIncomeDate(LocalDate.now());
        mapper.insert(entity);
        return entity.getId();
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void confirmIncome(Long id, String confirmedBy) {
        PropertyIncomeDO exist = validateExists(id);
        if (exist.getConfirmed() == 1) throw exception(ErrorCodeConstants.INCOME_ALREADY_CONFIRMED);
        exist.setConfirmed(1);
        exist.setConfirmedBy(confirmedBy);
        exist.setConfirmedTime(LocalDateTime.now());
        mapper.updateById(exist);
    }

    @Override public PropertyIncomeDO getIncome(Long id) {
        return validateExists(id);
    }

    @Override public PageResult<PropertyIncomeDO> getIncomePage(Long payerId, String incomeType, String payChannel,
                                                                Integer confirmed, String beginDate, String endDate,
                                                                Integer pageNo, Integer pageSize) {
        return mapper.selectPage(incomeType, payChannel, payerId,
                beginDate != null ? LocalDate.parse(beginDate) : null,
                endDate != null ? LocalDate.parse(endDate) : null, confirmed);
    }

    private PropertyIncomeDO validateExists(Long id) {
        PropertyIncomeDO entity = mapper.selectById(id);
        if (entity == null) throw exception(ErrorCodeConstants.INCOME_NOT_EXISTS);
        return entity;
    }
}

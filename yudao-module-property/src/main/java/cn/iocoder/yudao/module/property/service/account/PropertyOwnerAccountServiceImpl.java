package cn.iocoder.yudao.module.property.service.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.account.PropertyOwnerAccountDO;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyPrepayRecordDO;
import cn.iocoder.yudao.module.property.dal.mysql.account.PropertyOwnerAccountMapper;
import cn.iocoder.yudao.module.property.dal.mysql.finance.PropertyPrepayRecordMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.property.enums.PrepayRecordTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyOwnerAccountServiceImpl implements PropertyOwnerAccountService {

    @Resource private PropertyOwnerAccountMapper accountMapper;
    @Resource private PropertyPrepayRecordMapper prepayRecordMapper;

    @Override public PropertyOwnerAccountDO getAccount(Long id) {
        PropertyOwnerAccountDO entity = accountMapper.selectById(id);
        if (entity == null) throw exception(ErrorCodeConstants.OWNER_ACCOUNT_NOT_EXISTS);
        return entity;
    }

    @Override public PropertyOwnerAccountDO getAccountByOwnerId(Long ownerId) {
        return accountMapper.selectByOwnerId(ownerId);
    }

    @Override public PageResult<PropertyOwnerAccountDO> getAccountPage(Long ownerId, Integer status,
                                                                        Integer pageNo, Integer pageSize) {
        return accountMapper.selectPage(ownerId, status);
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void topUp(Long accountId, BigDecimal amount, String payChannel) {
        PropertyOwnerAccountDO account = getAccount(accountId);
        BigDecimal before = account.getBalance();
        account.setBalance(before.add(amount));
        account.setTotalIncome(account.getTotalIncome().add(amount));
        account.setLastPayDate(LocalDateTime.now());
        accountMapper.updateById(account);

        PropertyPrepayRecordDO record = PropertyPrepayRecordDO.builder()
                .accountId(accountId).recordType(PrepayRecordTypeEnum.TOPUP.getType())
                .amount(amount).balanceBefore(before).balanceAfter(account.getBalance())
                .payChannel(payChannel).transactionTime(LocalDateTime.now()).build();
        prepayRecordMapper.insert(record);
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void consumeFromPrepay(Long accountId, BigDecimal amount, String relatedBizType, Long relatedBizId) {
        PropertyOwnerAccountDO account = getAccount(accountId);
        if (account.getBalance().compareTo(amount) < 0) throw exception(ErrorCodeConstants.INSUFFICIENT_BALANCE);
        BigDecimal before = account.getBalance();
        account.setBalance(before.subtract(amount));
        accountMapper.updateById(account);

        PropertyPrepayRecordDO record = PropertyPrepayRecordDO.builder()
                .accountId(accountId).recordType(PrepayRecordTypeEnum.CONSUME.getType())
                .amount(amount.negate()).balanceBefore(before).balanceAfter(account.getBalance())
                .relatedBizType(relatedBizType).relatedBizId(relatedBizId)
                .transactionTime(LocalDateTime.now()).build();
        prepayRecordMapper.insert(record);
    }

    @Override public PageResult<PropertyPrepayRecordDO> getPrepayRecordPage(Long accountId, Integer recordType,
                                                                             String beginTime, String endTime,
                                                                             Integer pageNo, Integer pageSize) {
        return prepayRecordMapper.selectPage(accountId, recordType,
                beginTime != null ? LocalDateTime.parse(beginTime) : null,
                endTime != null ? LocalDateTime.parse(endTime) : null);
    }
}

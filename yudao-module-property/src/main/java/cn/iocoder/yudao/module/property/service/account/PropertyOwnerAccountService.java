package cn.iocoder.yudao.module.property.service.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.account.PropertyOwnerAccountDO;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyPrepayRecordDO;
import jakarta.validation.Valid;
import java.math.BigDecimal;

public interface PropertyOwnerAccountService {
    PropertyOwnerAccountDO getAccount(Long id);
    PropertyOwnerAccountDO getAccountByOwnerId(Long ownerId);
    PageResult<PropertyOwnerAccountDO> getAccountPage(Long ownerId, Integer status, Integer pageNo, Integer pageSize);
    void topUp(Long accountId, BigDecimal amount, String payChannel);
    void consumeFromPrepay(Long accountId, BigDecimal amount, String relatedBizType, Long relatedBizId);
    PageResult<PropertyPrepayRecordDO> getPrepayRecordPage(Long accountId, Integer recordType,
                                                            String beginTime, String endTime, Integer pageNo, Integer pageSize);
}

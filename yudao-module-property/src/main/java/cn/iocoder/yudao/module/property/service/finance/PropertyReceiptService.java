package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyReceiptDO;
import jakarta.validation.Valid;

public interface PropertyReceiptService {
    Long createReceipt(@Valid PropertyReceiptDO receipt);
    PropertyReceiptDO getReceipt(Long id);
    PageResult<PropertyReceiptDO> getReceiptPage(String receiptType, Long payerId, String beginDate, String endDate,
                                                  Integer pageNo, Integer pageSize);
}

package cn.iocoder.yudao.module.property.service.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyReceiptDO;
import cn.iocoder.yudao.module.property.dal.mysql.finance.PropertyReceiptMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;
import static cn.iocoder.yudao.framework.common.util.object.BeanUtils.toBean;

@Service @Slf4j @Validated
public class PropertyReceiptServiceImpl implements PropertyReceiptService {

    @Resource private PropertyReceiptMapper mapper;

    @Override @Transactional(rollbackFor = Exception.class)
    public Long createReceipt(PropertyReceiptDO receipt) {
        PropertyReceiptDO entity = toBean(receipt, PropertyReceiptDO.class);
        entity.setIssueDate(LocalDate.now());
        entity.setPrintCount(0);
        mapper.insert(entity);
        return entity.getId();
    }

    @Override public PropertyReceiptDO getReceipt(Long id) {
        return mapper.selectById(id);
    }

    @Override public PageResult<PropertyReceiptDO> getReceiptPage(String receiptType, Long payerId,
                                                                  String beginDate, String endDate,
                                                                  Integer pageNo, Integer pageSize) {
        return mapper.selectPage(receiptType, payerId,
                beginDate != null ? LocalDate.parse(beginDate) : null,
                endDate != null ? LocalDate.parse(endDate) : null);
    }
}

package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyCashierPaymentSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierPaymentDO;
import cn.iocoder.yudao.module.property.dal.mysql.cashier.PropertyCashierPaymentMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyCashierPaymentServiceImpl implements PropertyCashierPaymentService {

    @Resource private PropertyCashierPaymentMapper paymentMapper;

    @Override public Long createPayment(PropertyCashierPaymentSaveReqVO reqVO) {
        PropertyCashierPaymentDO payment = BeanUtils.toBean(reqVO, PropertyCashierPaymentDO.class);
        paymentMapper.insert(payment);
        return payment.getId();
    }

    @Override public void updatePayment(PropertyCashierPaymentSaveReqVO reqVO) {
        validatePaymentExists(reqVO.getId());
        PropertyCashierPaymentDO payment = BeanUtils.toBean(reqVO, PropertyCashierPaymentDO.class);
        paymentMapper.updateById(payment);
    }

    @Override public void deletePayment(Long id) {
        validatePaymentExists(id);
        paymentMapper.deleteById(id);
    }

    @Override public PropertyCashierPaymentDO getPayment(Long id) {
        return paymentMapper.selectById(id);
    }

    @Override public PageResult<PropertyCashierPaymentDO> getPaymentPage(Long recordId, String payChannel, Integer payStatus, Integer pageNo, Integer pageSize) {
        return paymentMapper.selectPage(recordId, payChannel, payStatus, pageNo, pageSize);
    }

    @Override public List<PropertyCashierPaymentDO> getPaymentList() {
        return paymentMapper.selectList();
    }

    @Override public List<PropertyCashierPaymentDO> getPaymentListByRecordId(Long recordId) {
        return paymentMapper.selectListByRecordId(recordId);
    }

    @Override public List<PropertyCashierPaymentDO> getPaymentListByPayStatus(Integer payStatus) {
        return paymentMapper.selectListByPayStatus(payStatus);
    }

    private void validatePaymentExists(Long id) {
        if (paymentMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CASHIER_PAYMENT_NOT_EXISTS);
        }
    }
}
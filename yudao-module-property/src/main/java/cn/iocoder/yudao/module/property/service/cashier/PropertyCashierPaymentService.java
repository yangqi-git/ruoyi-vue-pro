package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierPaymentDO;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyCashierPaymentSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyCashierPaymentService {

    Long createPayment(@Valid PropertyCashierPaymentSaveReqVO reqVO);

    void updatePayment(@Valid PropertyCashierPaymentSaveReqVO reqVO);

    void deletePayment(Long id);

    PropertyCashierPaymentDO getPayment(Long id);

    PageResult<PropertyCashierPaymentDO> getPaymentPage(Long recordId, String payChannel, Integer payStatus, Integer pageNo, Integer pageSize);

    List<PropertyCashierPaymentDO> getPaymentList();

    List<PropertyCashierPaymentDO> getPaymentListByRecordId(Long recordId);

    List<PropertyCashierPaymentDO> getPaymentListByPayStatus(Integer payStatus);
}
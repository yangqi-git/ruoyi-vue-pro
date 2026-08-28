package cn.iocoder.yudao.module.property.service.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundRecordDO;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.PropertyRefundRecordSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyRefundRecordService {

    Long createRefund(@Valid PropertyRefundRecordSaveReqVO reqVO);

    void updateRefund(@Valid PropertyRefundRecordSaveReqVO reqVO);

    void deleteRefund(Long id);

    PropertyRefundRecordDO getRefund(Long id);

    PageResult<PropertyRefundRecordDO> getRefundPage(Long projectId, String refundNo, Long cashierRecordId, Long billId, Long houseId, Integer refundType, Integer refundStatus, Integer approvalStatus, Integer pageNo, Integer pageSize);

    List<PropertyRefundRecordDO> getRefundList();

    List<PropertyRefundRecordDO> getRefundListByCashierRecordId(Long cashierRecordId);

    List<PropertyRefundRecordDO> getRefundListByBillId(Long billId);

    List<PropertyRefundRecordDO> getRefundListByHouseId(Long houseId);

    List<PropertyRefundRecordDO> getRefundListByRefundStatus(Integer refundStatus);
}

package cn.iocoder.yudao.module.property.service.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundDetailDO;
import cn.iocoder.yudao.module.property.controller.admin.refund.vo.PropertyRefundDetailSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyRefundDetailService {

    Long createDetail(@Valid PropertyRefundDetailSaveReqVO reqVO);

    void updateDetail(@Valid PropertyRefundDetailSaveReqVO reqVO);

    void deleteDetail(Long id);

    PropertyRefundDetailDO getDetail(Long id);

    PageResult<PropertyRefundDetailDO> getDetailPage(Long refundId, Long billDetailId, Integer refundStatus, Integer pageNo, Integer pageSize);

    List<PropertyRefundDetailDO> getDetailList();

    List<PropertyRefundDetailDO> getDetailListByRefundId(Long refundId);

    List<PropertyRefundDetailDO> getDetailListByBillDetailId(Long billDetailId);

    List<PropertyRefundDetailDO> getDetailListByRefundStatus(Integer refundStatus);
}
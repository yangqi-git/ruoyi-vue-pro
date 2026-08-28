package cn.iocoder.yudao.module.property.service.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyBillDetailDO;
import cn.iocoder.yudao.module.property.controller.admin.bill.vo.PropertyBillDetailSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyBillDetailService {

    Long createDetail(@Valid PropertyBillDetailSaveReqVO reqVO);

    void updateDetail(@Valid PropertyBillDetailSaveReqVO reqVO);

    void deleteDetail(Long id);

    PropertyBillDetailDO getDetail(Long id);

    PageResult<PropertyBillDetailDO> getDetailPage(Long billId, Long itemId, Integer payStatus, Integer pageNo, Integer pageSize);

    List<PropertyBillDetailDO> getDetailList();

    List<PropertyBillDetailDO> getDetailListByBillId(Long billId);

    List<PropertyBillDetailDO> getDetailListByItemId(Long itemId);

    List<PropertyBillDetailDO> getDetailListByPayStatus(Integer payStatus);
}
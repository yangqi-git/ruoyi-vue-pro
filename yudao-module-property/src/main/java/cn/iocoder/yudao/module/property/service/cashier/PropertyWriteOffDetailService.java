package cn.iocoder.yudao.module.property.service.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyWriteOffDetailDO;
import cn.iocoder.yudao.module.property.controller.admin.cashier.vo.PropertyWriteOffDetailSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyWriteOffDetailService {

    Long createDetail(@Valid PropertyWriteOffDetailSaveReqVO reqVO);

    void updateDetail(@Valid PropertyWriteOffDetailSaveReqVO reqVO);

    void deleteDetail(Long id);

    PropertyWriteOffDetailDO getDetail(Long id);

    PageResult<PropertyWriteOffDetailDO> getDetailPage(Long recordId, Long billId, Integer writeOffType, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyWriteOffDetailDO> getDetailList();

    List<PropertyWriteOffDetailDO> getDetailListByRecordId(Long recordId);

    List<PropertyWriteOffDetailDO> getDetailListByBillId(Long billId);

    List<PropertyWriteOffDetailDO> getDetailListByStatus(Integer status);
}
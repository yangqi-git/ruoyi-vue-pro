package cn.iocoder.yudao.module.property.service.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyAdjustRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyAdjustRecordDO;
import java.util.List;

public interface PropertyAdjustRecordService {

    Long createAdjustRecord(PropertyAdjustRecordSaveReqVO reqVO);

    void updateAdjustRecord(PropertyAdjustRecordSaveReqVO reqVO);

    void deleteAdjustRecord(Long id);

    PropertyAdjustRecordDO getAdjustRecord(Long id);

    PageResult<PropertyAdjustRecordDO> getAdjustRecordPage(Long communityId, Long houseId, Long billId,
            Integer adjustType, Integer approvalStatus, Integer pageNo, Integer pageSize);

    List<PropertyAdjustRecordDO> getAdjustRecordListByBillId(Long billId);

    void approveAdjustRecord(Long id, Long approverId, Integer approvalStatus, String approvalRemark);
}
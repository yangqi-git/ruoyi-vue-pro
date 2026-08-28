package cn.iocoder.yudao.module.property.service.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyDiscountRecordSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyDiscountRecordDO;
import java.util.List;

public interface PropertyDiscountRecordService {

    Long createDiscountRecord(PropertyDiscountRecordSaveReqVO reqVO);

    void updateDiscountRecord(PropertyDiscountRecordSaveReqVO reqVO);

    void deleteDiscountRecord(Long id);

    PropertyDiscountRecordDO getDiscountRecord(Long id);

    PageResult<PropertyDiscountRecordDO> getDiscountRecordPage(Long projectId, Long communityId, Long houseId, Long billId,
            Integer discountType, Integer approvalStatus, Integer pageNo, Integer pageSize);

    List<PropertyDiscountRecordDO> getDiscountRecordListByBillId(Long billId);

    void approveDiscountRecord(Long id, Long approverId, Integer approvalStatus, String approvalRemark);
}

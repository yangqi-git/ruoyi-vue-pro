package cn.iocoder.yudao.module.property.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.settlement.vo.PropertySettlementBatchSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.settlement.PropertySettlementBatchDO;
import java.util.List;

public interface PropertySettlementBatchService {

    Long createSettlementBatch(PropertySettlementBatchSaveReqVO reqVO);

    void updateSettlementBatch(PropertySettlementBatchSaveReqVO reqVO);

    void deleteSettlementBatch(Long id);

    PropertySettlementBatchDO getSettlementBatch(Long id);

    PageResult<PropertySettlementBatchDO> getSettlementBatchPage(Long projectId, Long communityId, Integer batchType,
            Integer batchStatus, Integer approvalStatus, Integer pageNo, Integer pageSize);

    List<PropertySettlementBatchDO> getSettlementBatchListByCommunityId(Long communityId);

    void approveSettlementBatch(Long id, Long approverId, Integer approvalStatus, String approvalRemark);

    void settleBatch(Long id, Long settlerId);
}

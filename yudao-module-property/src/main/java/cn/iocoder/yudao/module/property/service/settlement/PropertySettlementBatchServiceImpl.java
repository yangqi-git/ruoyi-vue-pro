package cn.iocoder.yudao.module.property.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.settlement.vo.PropertySettlementBatchSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.settlement.PropertySettlementBatchDO;
import cn.iocoder.yudao.module.property.dal.mysql.settlement.PropertySettlementBatchMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertySettlementBatchServiceImpl implements PropertySettlementBatchService {

    @Resource private PropertySettlementBatchMapper settlementBatchMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createSettlementBatch(PropertySettlementBatchSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertySettlementBatchDO batch = BeanUtils.toBean(reqVO, PropertySettlementBatchDO.class);
        settlementBatchMapper.insert(batch);
        return batch.getId();
    }

    @Override
    public void updateSettlementBatch(PropertySettlementBatchSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateSettlementBatchExists(reqVO.getId());
        PropertySettlementBatchDO batch = BeanUtils.toBean(reqVO, PropertySettlementBatchDO.class);
        settlementBatchMapper.updateById(batch);
    }

    @Override
    public void deleteSettlementBatch(Long id) {
        validateSettlementBatchExists(id);
        settlementBatchMapper.deleteById(id);
    }

    @Override
    public PropertySettlementBatchDO getSettlementBatch(Long id) {
        return settlementBatchMapper.selectById(id);
    }

    @Override
    public PageResult<PropertySettlementBatchDO> getSettlementBatchPage(Long projectId, Long communityId, Integer batchType,
            Integer batchStatus, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        return settlementBatchMapper.selectPage(projectId, communityId, batchType, batchStatus, approvalStatus, pageNo, pageSize);
    }

    @Override
    public List<PropertySettlementBatchDO> getSettlementBatchListByCommunityId(Long communityId) {
        return settlementBatchMapper.selectList(PropertySettlementBatchDO::getCommunityId, communityId);
    }

    @Override
    public void approveSettlementBatch(Long id, Long approverId, Integer approvalStatus, String approvalRemark) {
        validateSettlementBatchExists(id);
        PropertySettlementBatchDO batch = new PropertySettlementBatchDO();
        batch.setId(id);
        batch.setApproverId(approverId);
        batch.setApprovalStatus(approvalStatus);
        batch.setApprovalTime(LocalDateTime.now());
        settlementBatchMapper.updateById(batch);
    }

    @Override
    public void settleBatch(Long id, Long settlerId) {
        validateSettlementBatchExists(id);
        PropertySettlementBatchDO batch = new PropertySettlementBatchDO();
        batch.setId(id);
        batch.setSettlerId(settlerId);
        batch.setSettleTime(LocalDateTime.now());
        batch.setBatchStatus(2);
        settlementBatchMapper.updateById(batch);
    }

    private void validateSettlementBatchExists(Long id) {
        if (settlementBatchMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SETTLEMENT_BATCH_NOT_EXISTS);
        }
    }
}

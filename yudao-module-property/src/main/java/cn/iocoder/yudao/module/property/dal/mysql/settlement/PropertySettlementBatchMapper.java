package cn.iocoder.yudao.module.property.dal.mysql.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.settlement.PropertySettlementBatchDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertySettlementBatchMapper extends BaseMapperX<PropertySettlementBatchDO> {

    default PropertySettlementBatchDO selectByBatchNo(String batchNo) {
        return selectOne(PropertySettlementBatchDO::getBatchNo, batchNo);
    }

    default PageResult<PropertySettlementBatchDO> selectPage(Long projectId, Long communityId, Integer batchType,
            Integer batchStatus, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        Page<PropertySettlementBatchDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertySettlementBatchDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertySettlementBatchDO>()
                .eqIfPresent(PropertySettlementBatchDO::getProjectId, projectId)
                .eqIfPresent(PropertySettlementBatchDO::getCommunityId, communityId)
                .eqIfPresent(PropertySettlementBatchDO::getBatchType, batchType)
                .eqIfPresent(PropertySettlementBatchDO::getBatchStatus, batchStatus)
                .eqIfPresent(PropertySettlementBatchDO::getApprovalStatus, approvalStatus)
                .
        orderByDesc(PropertySettlementBatchDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

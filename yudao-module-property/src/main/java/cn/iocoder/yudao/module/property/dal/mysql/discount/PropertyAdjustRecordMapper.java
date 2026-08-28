package cn.iocoder.yudao.module.property.dal.mysql.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyAdjustRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyAdjustRecordMapper extends BaseMapperX<PropertyAdjustRecordDO> {

    default PropertyAdjustRecordDO selectByAdjustNo(String adjustNo) {
        return selectOne(PropertyAdjustRecordDO::getAdjustNo, adjustNo);
    }

    default PageResult<PropertyAdjustRecordDO> selectPage(Long communityId, Long houseId, Long billId,
            Integer adjustType, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyAdjustRecordDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyAdjustRecordDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyAdjustRecordDO>()
                .eqIfPresent(PropertyAdjustRecordDO::getCommunityId, communityId)
                .eqIfPresent(PropertyAdjustRecordDO::getHouseId, houseId)
                .eqIfPresent(PropertyAdjustRecordDO::getBillId, billId)
                .eqIfPresent(PropertyAdjustRecordDO::getAdjustType, adjustType)
                .eqIfPresent(PropertyAdjustRecordDO::getApprovalStatus, approvalStatus)
                .
        orderByDesc(PropertyAdjustRecordDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}
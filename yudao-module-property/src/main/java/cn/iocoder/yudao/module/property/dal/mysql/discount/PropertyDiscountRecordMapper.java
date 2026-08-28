package cn.iocoder.yudao.module.property.dal.mysql.discount;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.discount.PropertyDiscountRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyDiscountRecordMapper extends BaseMapperX<PropertyDiscountRecordDO> {

    default PropertyDiscountRecordDO selectByDiscountNo(String discountNo) {
        return selectOne(PropertyDiscountRecordDO::getDiscountNo, discountNo);
    }

    default PageResult<PropertyDiscountRecordDO> selectPage(Long projectId, Long communityId, Long houseId, Long billId,
            Integer discountType, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyDiscountRecordDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyDiscountRecordDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyDiscountRecordDO>()
                .eqIfPresent(PropertyDiscountRecordDO::getProjectId, projectId)
                .eqIfPresent(PropertyDiscountRecordDO::getCommunityId, communityId)
                .eqIfPresent(PropertyDiscountRecordDO::getHouseId, houseId)
                .eqIfPresent(PropertyDiscountRecordDO::getBillId, billId)
                .eqIfPresent(PropertyDiscountRecordDO::getDiscountType, discountType)
                .eqIfPresent(PropertyDiscountRecordDO::getApprovalStatus, approvalStatus)
                .
        orderByDesc(PropertyDiscountRecordDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

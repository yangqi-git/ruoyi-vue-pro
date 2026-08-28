package cn.iocoder.yudao.module.property.dal.mysql.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleApplyDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyChargeRuleApplyMapper extends BaseMapperX<PropertyChargeRuleApplyDO> {

    default PageResult<PropertyChargeRuleApplyDO> selectPage(Long ruleId, Long itemId, Long houseId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyChargeRuleApplyDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyChargeRuleApplyDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyChargeRuleApplyDO>()
                .eqIfPresent(PropertyChargeRuleApplyDO::getRuleId, ruleId)
                .eqIfPresent(PropertyChargeRuleApplyDO::getItemId, itemId)
                .eqIfPresent(PropertyChargeRuleApplyDO::getHouseId, houseId)
                .eqIfPresent(PropertyChargeRuleApplyDO::getCommunityId, communityId)
                .eqIfPresent(PropertyChargeRuleApplyDO::getStatus, status)
                .
        orderByDesc(PropertyChargeRuleApplyDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyChargeRuleApplyDO> selectListByRuleId(Long ruleId) {
        return selectList(PropertyChargeRuleApplyDO::getRuleId, ruleId);
    }

    default List<PropertyChargeRuleApplyDO> selectListByItemId(Long itemId) {
        return selectList(PropertyChargeRuleApplyDO::getItemId, itemId);
    }

    default List<PropertyChargeRuleApplyDO> selectListByHouseId(Long houseId) {
        return selectList(PropertyChargeRuleApplyDO::getHouseId, houseId);
    }

    default List<PropertyChargeRuleApplyDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyChargeRuleApplyDO::getCommunityId, communityId);
    }

    default List<PropertyChargeRuleApplyDO> selectListByStatus(Integer status) {
        return selectList(PropertyChargeRuleApplyDO::getStatus, status);
    }

    default List<PropertyChargeRuleApplyDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyChargeRuleApplyDO>()
                .orderByDesc(PropertyChargeRuleApplyDO::getId));
    }
}
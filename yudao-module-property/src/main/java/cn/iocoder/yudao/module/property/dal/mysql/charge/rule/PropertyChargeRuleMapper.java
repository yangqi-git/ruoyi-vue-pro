package cn.iocoder.yudao.module.property.dal.mysql.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyChargeRuleMapper extends BaseMapperX<PropertyChargeRuleDO> {

    default PageResult<PropertyChargeRuleDO> selectPage(String name, String code, Long itemId, Long projectId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyChargeRuleDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyChargeRuleDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyChargeRuleDO>()
                .likeIfPresent(PropertyChargeRuleDO::getName, name)
                .eqIfPresent(PropertyChargeRuleDO::getCode, code)
                .eqIfPresent(PropertyChargeRuleDO::getItemId, itemId)
                .eqIfPresent(PropertyChargeRuleDO::getProjectId, projectId)
                .eqIfPresent(PropertyChargeRuleDO::getCommunityId, communityId)
                .eqIfPresent(PropertyChargeRuleDO::getStatus, status)
                .
        orderByDesc(PropertyChargeRuleDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyChargeRuleDO selectByCode(String code) {
        return selectOne(PropertyChargeRuleDO::getCode, code);
    }

    default List<PropertyChargeRuleDO> selectListByItemId(Long itemId) {
        return selectList(PropertyChargeRuleDO::getItemId, itemId);
    }

    default List<PropertyChargeRuleDO> selectListByProjectId(Long projectId) {
        return selectList(PropertyChargeRuleDO::getProjectId, projectId);
    }

    default List<PropertyChargeRuleDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyChargeRuleDO::getCommunityId, communityId);
    }

    default List<PropertyChargeRuleDO> selectListByStatus(Integer status) {
        return selectList(PropertyChargeRuleDO::getStatus, status);
    }

    default List<PropertyChargeRuleDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyChargeRuleDO>()
                .orderByDesc(PropertyChargeRuleDO::getId));
    }
}
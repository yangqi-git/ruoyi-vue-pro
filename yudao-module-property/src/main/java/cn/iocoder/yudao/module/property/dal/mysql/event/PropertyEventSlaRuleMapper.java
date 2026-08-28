package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRulePageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyEventSlaRuleMapper extends BaseMapperX<PropertyEventSlaRuleDO> {
    default PropertyEventSlaRuleDO selectUnique(Long projectId, String categoryCode, Integer urgencyLevel) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventSlaRuleDO>()
                .eq(PropertyEventSlaRuleDO::getProjectId, projectId)
                .eq(PropertyEventSlaRuleDO::getCategoryCode, categoryCode)
                .eq(PropertyEventSlaRuleDO::getUrgencyLevel, urgencyLevel));
    }

    default PropertyEventSlaRuleDO selectEnabled(Long projectId, String categoryCode, Integer urgencyLevel) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventSlaRuleDO>()
                .eq(PropertyEventSlaRuleDO::getProjectId, projectId)
                .eq(PropertyEventSlaRuleDO::getCategoryCode, categoryCode)
                .eq(PropertyEventSlaRuleDO::getUrgencyLevel, urgencyLevel)
                .eq(PropertyEventSlaRuleDO::getStatus, 0));
    }

    default PageResult<PropertyEventSlaRuleDO> selectPage(PropertyEventSlaRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyEventSlaRuleDO>()
                .eq(PropertyEventSlaRuleDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyEventSlaRuleDO::getCategoryCode, reqVO.getCategoryCode())
                .eqIfPresent(PropertyEventSlaRuleDO::getUrgencyLevel, reqVO.getUrgencyLevel())
                .eqIfPresent(PropertyEventSlaRuleDO::getStatus, reqVO.getStatus())
                .orderByAsc(PropertyEventSlaRuleDO::getCategoryCode)
                .orderByDesc(PropertyEventSlaRuleDO::getUrgencyLevel));
    }
}

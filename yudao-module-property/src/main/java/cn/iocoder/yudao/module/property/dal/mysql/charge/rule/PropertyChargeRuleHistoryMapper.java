package cn.iocoder.yudao.module.property.dal.mysql.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyChargeRuleHistoryMapper extends BaseMapperX<PropertyChargeRuleHistoryDO> {

    default PageResult<PropertyChargeRuleHistoryDO> selectPage(Long ruleId, Integer version, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyChargeRuleHistoryDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyChargeRuleHistoryDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyChargeRuleHistoryDO>()
                .eqIfPresent(PropertyChargeRuleHistoryDO::getRuleId, ruleId)
                .eqIfPresent(PropertyChargeRuleHistoryDO::getVersion, version)
                .eqIfPresent(PropertyChargeRuleHistoryDO::getStatus, status)
                .
        orderByDesc(PropertyChargeRuleHistoryDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyChargeRuleHistoryDO> selectListByRuleId(Long ruleId) {
        return selectList(new LambdaQueryWrapperX<PropertyChargeRuleHistoryDO>()
                .eq(PropertyChargeRuleHistoryDO::getRuleId, ruleId)
                .orderByDesc(PropertyChargeRuleHistoryDO::getVersion));
    }

    default List<PropertyChargeRuleHistoryDO> selectListByStatus(Integer status) {
        return selectList(PropertyChargeRuleHistoryDO::getStatus, status);
    }

    default List<PropertyChargeRuleHistoryDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyChargeRuleHistoryDO>()
                .orderByDesc(PropertyChargeRuleHistoryDO::getId));
    }
}
package cn.iocoder.yudao.module.property.dal.mysql.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeItemDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyChargeItemMapper extends BaseMapperX<PropertyChargeItemDO> {

    default PageResult<PropertyChargeItemDO> selectPage(String name, String code, Long projectId, Integer itemType, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyChargeItemDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyChargeItemDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyChargeItemDO>()
                .likeIfPresent(PropertyChargeItemDO::getName, name)
                .eqIfPresent(PropertyChargeItemDO::getCode, code)
                .eqIfPresent(PropertyChargeItemDO::getProjectId, projectId)
                .eqIfPresent(PropertyChargeItemDO::getItemType, itemType)
                .eqIfPresent(PropertyChargeItemDO::getStatus, status)
                .
        orderByDesc(PropertyChargeItemDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyChargeItemDO selectByCode(String code) {
        return selectOne(PropertyChargeItemDO::getCode, code);
    }

    default List<PropertyChargeItemDO> selectListByProjectId(Long projectId) {
        return selectList(PropertyChargeItemDO::getProjectId, projectId);
    }

    default List<PropertyChargeItemDO> selectListByStatus(Integer status) {
        return selectList(PropertyChargeItemDO::getStatus, status);
    }

    default List<PropertyChargeItemDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyChargeItemDO>()
                .orderByDesc(PropertyChargeItemDO::getId));
    }
}
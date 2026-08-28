package cn.iocoder.yudao.module.property.dal.mysql.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyUnitDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyUnitMapper extends BaseMapperX<PropertyUnitDO> {

    default PageResult<PropertyUnitDO> selectPage(Long projectId, String name, String code, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyUnitDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyUnitDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyUnitDO>()
                .eqIfPresent(PropertyUnitDO::getProjectId, projectId)
                .likeIfPresent(PropertyUnitDO::getName, name)
                .eqIfPresent(PropertyUnitDO::getCode, code)
                .eqIfPresent(PropertyUnitDO::getBuildingId, buildingId)
                .eqIfPresent(PropertyUnitDO::getCommunityId, communityId)
                .eqIfPresent(PropertyUnitDO::getStatus, status)
                .
        orderByDesc(PropertyUnitDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyUnitDO selectByCode(String code) {
        return selectOne(PropertyUnitDO::getCode, code);
    }

    default List<PropertyUnitDO> selectListByBuildingId(Long buildingId) {
        return selectList(PropertyUnitDO::getBuildingId, buildingId);
    }

    default List<PropertyUnitDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyUnitDO::getCommunityId, communityId);
    }

    default List<PropertyUnitDO> selectListByStatus(Integer status) {
        return selectList(PropertyUnitDO::getStatus, status);
    }

    default List<PropertyUnitDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyUnitDO>()
                .orderByDesc(PropertyUnitDO::getId));
    }
}

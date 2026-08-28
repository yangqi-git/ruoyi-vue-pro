package cn.iocoder.yudao.module.property.dal.mysql.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyFloorDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyFloorMapper extends BaseMapperX<PropertyFloorDO> {

    default PageResult<PropertyFloorDO> selectPage(Long projectId, String name, String code, Long unitId, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyFloorDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyFloorDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyFloorDO>()
                .eqIfPresent(PropertyFloorDO::getProjectId, projectId)
                .likeIfPresent(PropertyFloorDO::getName, name)
                .eqIfPresent(PropertyFloorDO::getCode, code)
                .eqIfPresent(PropertyFloorDO::getUnitId, unitId)
                .eqIfPresent(PropertyFloorDO::getBuildingId, buildingId)
                .eqIfPresent(PropertyFloorDO::getCommunityId, communityId)
                .eqIfPresent(PropertyFloorDO::getStatus, status)
                .
        orderByDesc(PropertyFloorDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyFloorDO selectByCode(String code) {
        return selectOne(PropertyFloorDO::getCode, code);
    }

    default List<PropertyFloorDO> selectListByUnitId(Long unitId) {
        return selectList(PropertyFloorDO::getUnitId, unitId);
    }

    default List<PropertyFloorDO> selectListByBuildingId(Long buildingId) {
        return selectList(PropertyFloorDO::getBuildingId, buildingId);
    }

    default List<PropertyFloorDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyFloorDO::getCommunityId, communityId);
    }

    default List<PropertyFloorDO> selectListByStatus(Integer status) {
        return selectList(PropertyFloorDO::getStatus, status);
    }

    default List<PropertyFloorDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyFloorDO>()
                .orderByDesc(PropertyFloorDO::getId));
    }
}

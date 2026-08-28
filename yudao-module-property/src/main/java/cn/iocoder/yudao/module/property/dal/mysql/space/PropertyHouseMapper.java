package cn.iocoder.yudao.module.property.dal.mysql.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyHouseDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyHouseMapper extends BaseMapperX<PropertyHouseDO> {

    default PageResult<PropertyHouseDO> selectPage(Long projectId, String name, String code, Long floorId, Long unitId, Long buildingId, Long communityId, Integer houseStatus, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyHouseDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyHouseDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyHouseDO>()
                .eqIfPresent(PropertyHouseDO::getProjectId, projectId)
                .likeIfPresent(PropertyHouseDO::getName, name)
                .eqIfPresent(PropertyHouseDO::getCode, code)
                .eqIfPresent(PropertyHouseDO::getFloorId, floorId)
                .eqIfPresent(PropertyHouseDO::getUnitId, unitId)
                .eqIfPresent(PropertyHouseDO::getBuildingId, buildingId)
                .eqIfPresent(PropertyHouseDO::getCommunityId, communityId)
                .eqIfPresent(PropertyHouseDO::getHouseStatus, houseStatus)
                .eqIfPresent(PropertyHouseDO::getStatus, status)
                .
        orderByDesc(PropertyHouseDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyHouseDO selectByCode(String code) {
        return selectOne(PropertyHouseDO::getCode, code);
    }

    default List<PropertyHouseDO> selectListByFloorId(Long floorId) {
        return selectList(PropertyHouseDO::getFloorId, floorId);
    }

    default List<PropertyHouseDO> selectListByUnitId(Long unitId) {
        return selectList(PropertyHouseDO::getUnitId, unitId);
    }

    default List<PropertyHouseDO> selectListByBuildingId(Long buildingId) {
        return selectList(PropertyHouseDO::getBuildingId, buildingId);
    }

    default List<PropertyHouseDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyHouseDO::getCommunityId, communityId);
    }

    default List<PropertyHouseDO> selectListByHouseStatus(Integer houseStatus) {
        return selectList(PropertyHouseDO::getHouseStatus, houseStatus);
    }

    default List<PropertyHouseDO> selectListByStatus(Integer status) {
        return selectList(PropertyHouseDO::getStatus, status);
    }

    default List<PropertyHouseDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyHouseDO>()
                .orderByDesc(PropertyHouseDO::getId));
    }
}

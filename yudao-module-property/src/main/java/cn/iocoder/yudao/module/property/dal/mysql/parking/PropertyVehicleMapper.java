package cn.iocoder.yudao.module.property.dal.mysql.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyVehicleDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyVehicleMapper extends BaseMapperX<PropertyVehicleDO> {

    default PropertyVehicleDO selectByPlateNo(String plateNo) {
        return selectOne(PropertyVehicleDO::getPlateNo, plateNo);
    }

    default PageResult<PropertyVehicleDO> selectPage(Long projectId, Long communityId, Long residentId,
            Integer vehicleStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyVehicleDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyVehicleDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyVehicleDO>()
                .eqIfPresent(PropertyVehicleDO::getProjectId, projectId)
                .eqIfPresent(PropertyVehicleDO::getCommunityId, communityId)
                .eqIfPresent(PropertyVehicleDO::getResidentId, residentId)
                .eqIfPresent(PropertyVehicleDO::getVehicleStatus, vehicleStatus)
                .
        orderByDesc(PropertyVehicleDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default java.util.List<PropertyVehicleDO> selectListByResidentId(Long residentId) {
        return selectList(PropertyVehicleDO::getResidentId, residentId);
    }

    default java.util.List<PropertyVehicleDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyVehicleDO::getCommunityId, communityId);
    }
}

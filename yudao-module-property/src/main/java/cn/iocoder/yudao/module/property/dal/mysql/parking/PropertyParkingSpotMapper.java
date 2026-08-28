package cn.iocoder.yudao.module.property.dal.mysql.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingSpotDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyParkingSpotMapper extends BaseMapperX<PropertyParkingSpotDO> {

    default PropertyParkingSpotDO selectBySpotNo(String spotNo) {
        return selectOne(PropertyParkingSpotDO::getSpotNo, spotNo);
    }

    default PageResult<PropertyParkingSpotDO> selectPage(Long projectId, Long parkingLotId, Long communityId,
            Integer spotType, Integer spotStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyParkingSpotDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyParkingSpotDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyParkingSpotDO>()
                .eqIfPresent(PropertyParkingSpotDO::getProjectId, projectId)
                .eqIfPresent(PropertyParkingSpotDO::getParkingLotId, parkingLotId)
                .eqIfPresent(PropertyParkingSpotDO::getCommunityId, communityId)
                .eqIfPresent(PropertyParkingSpotDO::getSpotType, spotType)
                .eqIfPresent(PropertyParkingSpotDO::getSpotStatus, spotStatus)
                .
        orderByDesc(PropertyParkingSpotDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default java.util.List<PropertyParkingSpotDO> selectListByParkingLotId(Long parkingLotId) {
        return selectList(PropertyParkingSpotDO::getParkingLotId, parkingLotId);
    }

    default java.util.List<PropertyParkingSpotDO> selectAvailableSpots(Long communityId) {
        return selectList(new LambdaQueryWrapperX<PropertyParkingSpotDO>()
                .eq(PropertyParkingSpotDO::getCommunityId, communityId)
                .eq(PropertyParkingSpotDO::getSpotStatus, 1));
    }
}

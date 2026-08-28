package cn.iocoder.yudao.module.property.dal.mysql.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLeaseDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyParkingLeaseMapper extends BaseMapperX<PropertyParkingLeaseDO> {

    default PropertyParkingLeaseDO selectByLeaseNo(String leaseNo) {
        return selectOne(PropertyParkingLeaseDO::getLeaseNo, leaseNo);
    }

    default PageResult<PropertyParkingLeaseDO> selectPage(Long projectId, Long parkingSpotId, Long communityId,
            Long residentId, Integer leaseType, Integer leaseStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyParkingLeaseDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyParkingLeaseDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyParkingLeaseDO>()
                .eqIfPresent(PropertyParkingLeaseDO::getProjectId, projectId)
                .eqIfPresent(PropertyParkingLeaseDO::getParkingSpotId, parkingSpotId)
                .eqIfPresent(PropertyParkingLeaseDO::getCommunityId, communityId)
                .eqIfPresent(PropertyParkingLeaseDO::getResidentId, residentId)
                .eqIfPresent(PropertyParkingLeaseDO::getLeaseType, leaseType)
                .eqIfPresent(PropertyParkingLeaseDO::getLeaseStatus, leaseStatus)
                .
        orderByDesc(PropertyParkingLeaseDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default java.util.List<PropertyParkingLeaseDO> selectListByParkingSpotId(Long parkingSpotId) {
        return selectList(PropertyParkingLeaseDO::getParkingSpotId, parkingSpotId);
    }

    default java.util.List<PropertyParkingLeaseDO> selectListByResidentId(Long residentId) {
        return selectList(PropertyParkingLeaseDO::getResidentId, residentId);
    }
}

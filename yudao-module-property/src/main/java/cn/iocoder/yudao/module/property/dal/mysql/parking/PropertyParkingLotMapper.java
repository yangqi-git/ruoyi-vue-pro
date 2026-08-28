package cn.iocoder.yudao.module.property.dal.mysql.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLotDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyParkingLotMapper extends BaseMapperX<PropertyParkingLotDO> {

    default PropertyParkingLotDO selectByCode(String code) {
        return selectOne(PropertyParkingLotDO::getCode, code);
    }

    default PageResult<PropertyParkingLotDO> selectPage(Long projectId, Long communityId, Integer parkingType,
            Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyParkingLotDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyParkingLotDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyParkingLotDO>()
                .eqIfPresent(PropertyParkingLotDO::getProjectId, projectId)
                .eqIfPresent(PropertyParkingLotDO::getCommunityId, communityId)
                .eqIfPresent(PropertyParkingLotDO::getParkingType, parkingType)
                .eqIfPresent(PropertyParkingLotDO::getStatus, status)
                .
        orderByDesc(PropertyParkingLotDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default java.util.List<PropertyParkingLotDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyParkingLotDO::getCommunityId, communityId);
    }
}

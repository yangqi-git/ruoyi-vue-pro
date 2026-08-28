package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingSpotDO;
import java.util.List;

public interface PropertyParkingSpotService {

    Long createParkingSpot(PropertyParkingSpotSaveReqVO reqVO);

    void updateParkingSpot(PropertyParkingSpotSaveReqVO reqVO);

    void deleteParkingSpot(Long id);

    PropertyParkingSpotDO getParkingSpot(Long id);

    PageResult<PropertyParkingSpotDO> getParkingSpotPage(Long projectId, Long parkingLotId, Long communityId,
            Integer spotType, Integer spotStatus, Integer pageNo, Integer pageSize);

    List<PropertyParkingSpotDO> getParkingSpotListByParkingLotId(Long parkingLotId);

    List<PropertyParkingSpotDO> getAvailableSpots(Long communityId);
}

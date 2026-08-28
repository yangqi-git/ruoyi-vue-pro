package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLotDO;
import java.util.List;

public interface PropertyParkingLotService {

    Long createParkingLot(PropertyParkingLotSaveReqVO reqVO);

    void updateParkingLot(PropertyParkingLotSaveReqVO reqVO);

    void deleteParkingLot(Long id);

    PropertyParkingLotDO getParkingLot(Long id);

    PageResult<PropertyParkingLotDO> getParkingLotPage(Long projectId, Long communityId, Integer parkingType,
            Integer status, Integer pageNo, Integer pageSize);

    List<PropertyParkingLotDO> getParkingLotListByCommunityId(Long communityId);
}

package cn.iocoder.yudao.module.property.service.parking;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyVehicleDO;
import java.util.List;

public interface PropertyVehicleService {

    Long createVehicle(PropertyVehicleSaveReqVO reqVO);

    void updateVehicle(PropertyVehicleSaveReqVO reqVO);

    void deleteVehicle(Long id);

    PropertyVehicleDO getVehicle(Long id);

    PageResult<PropertyVehicleDO> getVehiclePage(Long projectId, Long communityId, Long residentId,
            Integer vehicleStatus, Integer pageNo, Integer pageSize);

    List<PropertyVehicleDO> getVehicleListByResidentId(Long residentId);

    List<PropertyVehicleDO> getVehicleListByCommunityId(Long communityId);

    PropertyVehicleDO getVehicleByPlateNo(String plateNo);
}

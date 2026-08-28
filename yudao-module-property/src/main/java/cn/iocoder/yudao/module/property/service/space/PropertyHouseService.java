package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyHouseDO;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyHouseSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyHouseService {

    Long createHouse(@Valid PropertyHouseSaveReqVO reqVO);

    void updateHouse(@Valid PropertyHouseSaveReqVO reqVO);

    void deleteHouse(Long id);

    PropertyHouseDO getHouse(Long id);

    PageResult<PropertyHouseDO> getHousePage(Long projectId, String name, String code, Long floorId, Long unitId, Long buildingId, Long communityId, Integer houseStatus, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyHouseDO> getHouseList();

    List<PropertyHouseDO> getHouseListByFloorId(Long floorId);

    List<PropertyHouseDO> getHouseListByUnitId(Long unitId);

    List<PropertyHouseDO> getHouseListByBuildingId(Long buildingId);

    List<PropertyHouseDO> getHouseListByCommunityId(Long communityId);

    List<PropertyHouseDO> getHouseListByHouseStatus(Integer houseStatus);

    List<PropertyHouseDO> getHouseListByStatus(Integer status);
}

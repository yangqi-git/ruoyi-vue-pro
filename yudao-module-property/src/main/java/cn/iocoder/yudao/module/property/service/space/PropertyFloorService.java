package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyFloorDO;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyFloorSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyFloorService {

    Long createFloor(@Valid PropertyFloorSaveReqVO reqVO);

    void updateFloor(@Valid PropertyFloorSaveReqVO reqVO);

    void deleteFloor(Long id);

    PropertyFloorDO getFloor(Long id);

    PageResult<PropertyFloorDO> getFloorPage(Long projectId, String name, String code, Long unitId, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyFloorDO> getFloorList();

    List<PropertyFloorDO> getFloorListByUnitId(Long unitId);

    List<PropertyFloorDO> getFloorListByBuildingId(Long buildingId);

    List<PropertyFloorDO> getFloorListByCommunityId(Long communityId);

    List<PropertyFloorDO> getFloorListByStatus(Integer status);
}

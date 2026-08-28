package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyUnitDO;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyUnitSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyUnitService {

    Long createUnit(@Valid PropertyUnitSaveReqVO reqVO);

    void updateUnit(@Valid PropertyUnitSaveReqVO reqVO);

    void deleteUnit(Long id);

    PropertyUnitDO getUnit(Long id);

    PageResult<PropertyUnitDO> getUnitPage(Long projectId, String name, String code, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyUnitDO> getUnitList();

    List<PropertyUnitDO> getUnitListByBuildingId(Long buildingId);

    List<PropertyUnitDO> getUnitListByCommunityId(Long communityId);

    List<PropertyUnitDO> getUnitListByStatus(Integer status);
}

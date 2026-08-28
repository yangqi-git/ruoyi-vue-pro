package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyBuildingDO;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyBuildingSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyBuildingService {

    Long createBuilding(@Valid PropertyBuildingSaveReqVO reqVO);

    void updateBuilding(@Valid PropertyBuildingSaveReqVO reqVO);

    void deleteBuilding(Long id);

    PropertyBuildingDO getBuilding(Long id);

    PageResult<PropertyBuildingDO> getBuildingPage(Long projectId, String name, String code, Long communityId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyBuildingDO> getBuildingList();

    List<PropertyBuildingDO> getBuildingListByCommunityId(Long communityId);

    List<PropertyBuildingDO> getBuildingListByStatus(Integer status);
}

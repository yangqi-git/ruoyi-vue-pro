package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyResidentSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyResidentService {

    Long createResident(@Valid PropertyResidentSaveReqVO reqVO);

    void updateResident(@Valid PropertyResidentSaveReqVO reqVO);

    void deleteResident(Long id);

    PropertyResidentDO getResident(Long id);

    PageResult<PropertyResidentDO> getResidentPage(Long projectId, String name, String idCard, Long houseId, Long communityId, Integer residentType, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyResidentDO> getResidentList();

    List<PropertyResidentDO> getResidentListByHouseId(Long houseId);

    List<PropertyResidentDO> getResidentListByCommunityId(Long communityId);

    List<PropertyResidentDO> getResidentListByResidentType(Integer residentType);

    List<PropertyResidentDO> getResidentListByStatus(Integer status);
}

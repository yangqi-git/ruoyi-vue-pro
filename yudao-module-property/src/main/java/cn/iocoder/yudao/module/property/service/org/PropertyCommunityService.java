package cn.iocoder.yudao.module.property.service.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyCommunityDO;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.PropertyCommunitySaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyCommunityService {

    Long createCommunity(@Valid PropertyCommunitySaveReqVO reqVO);

    void updateCommunity(@Valid PropertyCommunitySaveReqVO reqVO);

    void deleteCommunity(Long id);

    PropertyCommunityDO getCommunity(Long id);

    PageResult<PropertyCommunityDO> getCommunityPage(String name, String code, Long projectId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyCommunityDO> getCommunityList();

    List<PropertyCommunityDO> getCommunityListByProjectId(Long projectId);

    List<PropertyCommunityDO> getCommunityListByStatus(Integer status);
}
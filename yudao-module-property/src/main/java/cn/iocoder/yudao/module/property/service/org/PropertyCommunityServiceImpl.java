package cn.iocoder.yudao.module.property.service.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.PropertyCommunitySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyCommunityDO;
import cn.iocoder.yudao.module.property.dal.mysql.org.PropertyCommunityMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyCommunityServiceImpl implements PropertyCommunityService {

    @Resource private PropertyCommunityMapper communityMapper;
    @Resource private PropertyProjectService projectService;

    @Override public Long createCommunity(PropertyCommunitySaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && communityMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.COMMUNITY_CODE_EXISTS);
        }
        PropertyCommunityDO community = BeanUtils.toBean(reqVO, PropertyCommunityDO.class);
        communityMapper.insert(community);
        return community.getId();
    }

    @Override public void updateCommunity(PropertyCommunitySaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateCommunityExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyCommunityDO existing = communityMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.COMMUNITY_CODE_EXISTS);
            }
        }
        PropertyCommunityDO community = BeanUtils.toBean(reqVO, PropertyCommunityDO.class);
        communityMapper.updateById(community);
    }

    @Override public void deleteCommunity(Long id) {
        validateCommunityExists(id);
        communityMapper.deleteById(id);
    }

    @Override public PropertyCommunityDO getCommunity(Long id) {
        return communityMapper.selectById(id);
    }

    @Override public PageResult<PropertyCommunityDO> getCommunityPage(String name, String code, Long projectId, Integer status, Integer pageNo, Integer pageSize) {
        return communityMapper.selectPage(name, code, projectId, status, pageNo, pageSize);
    }

    @Override public List<PropertyCommunityDO> getCommunityList() {
        return communityMapper.selectList();
    }

    @Override public List<PropertyCommunityDO> getCommunityListByProjectId(Long projectId) {
        return communityMapper.selectListByProjectId(projectId);
    }

    @Override public List<PropertyCommunityDO> getCommunityListByStatus(Integer status) {
        return communityMapper.selectListByStatus(status);
    }

    private void validateCommunityExists(Long id) {
        if (communityMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.COMMUNITY_NOT_EXISTS);
        }
    }
}

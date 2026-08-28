package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyBuildingSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyBuildingDO;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyBuildingMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyBuildingServiceImpl implements PropertyBuildingService {

    @Resource private PropertyBuildingMapper buildingMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createBuilding(PropertyBuildingSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && buildingMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.BUILDING_CODE_EXISTS);
        }
        PropertyBuildingDO building = BeanUtils.toBean(reqVO, PropertyBuildingDO.class);
        buildingMapper.insert(building);
        return building.getId();
    }

    @Override public void updateBuilding(PropertyBuildingSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateBuildingExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyBuildingDO existing = buildingMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.BUILDING_CODE_EXISTS);
            }
        }
        PropertyBuildingDO building = BeanUtils.toBean(reqVO, PropertyBuildingDO.class);
        buildingMapper.updateById(building);
    }

    @Override public void deleteBuilding(Long id) {
        validateBuildingExists(id);
        buildingMapper.deleteById(id);
    }

    @Override public PropertyBuildingDO getBuilding(Long id) {
        return buildingMapper.selectById(id);
    }

    @Override public PageResult<PropertyBuildingDO> getBuildingPage(Long projectId, String name, String code, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        return buildingMapper.selectPage(projectId, name, code, communityId, status, pageNo, pageSize);
    }

    @Override public List<PropertyBuildingDO> getBuildingList() {
        return buildingMapper.selectList();
    }

    @Override public List<PropertyBuildingDO> getBuildingListByCommunityId(Long communityId) {
        return buildingMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyBuildingDO> getBuildingListByStatus(Integer status) {
        return buildingMapper.selectListByStatus(status);
    }

    private void validateBuildingExists(Long id) {
        if (buildingMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.BUILDING_NOT_EXISTS);
        }
    }
}

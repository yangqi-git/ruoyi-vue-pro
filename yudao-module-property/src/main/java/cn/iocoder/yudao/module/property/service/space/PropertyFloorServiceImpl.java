package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyFloorSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyFloorDO;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyFloorMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyFloorServiceImpl implements PropertyFloorService {

    @Resource private PropertyFloorMapper floorMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createFloor(PropertyFloorSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && floorMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.FLOOR_NOT_EXISTS);
        }
        PropertyFloorDO floor = BeanUtils.toBean(reqVO, PropertyFloorDO.class);
        floorMapper.insert(floor);
        return floor.getId();
    }

    @Override public void updateFloor(PropertyFloorSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateFloorExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyFloorDO existing = floorMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.FLOOR_NOT_EXISTS);
            }
        }
        PropertyFloorDO floor = BeanUtils.toBean(reqVO, PropertyFloorDO.class);
        floorMapper.updateById(floor);
    }

    @Override public void deleteFloor(Long id) {
        validateFloorExists(id);
        floorMapper.deleteById(id);
    }

    @Override public PropertyFloorDO getFloor(Long id) {
        return floorMapper.selectById(id);
    }

    @Override public PageResult<PropertyFloorDO> getFloorPage(Long projectId, String name, String code, Long unitId, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        return floorMapper.selectPage(projectId, name, code, unitId, buildingId, communityId, status, pageNo, pageSize);
    }

    @Override public List<PropertyFloorDO> getFloorList() {
        return floorMapper.selectList();
    }

    @Override public List<PropertyFloorDO> getFloorListByUnitId(Long unitId) {
        return floorMapper.selectListByUnitId(unitId);
    }

    @Override public List<PropertyFloorDO> getFloorListByBuildingId(Long buildingId) {
        return floorMapper.selectListByBuildingId(buildingId);
    }

    @Override public List<PropertyFloorDO> getFloorListByCommunityId(Long communityId) {
        return floorMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyFloorDO> getFloorListByStatus(Integer status) {
        return floorMapper.selectListByStatus(status);
    }

    private void validateFloorExists(Long id) {
        if (floorMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.FLOOR_NOT_EXISTS);
        }
    }
}

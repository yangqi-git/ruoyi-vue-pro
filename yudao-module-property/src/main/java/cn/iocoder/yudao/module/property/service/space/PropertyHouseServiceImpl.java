package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyHouseSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyHouseDO;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyHouseMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyHouseServiceImpl implements PropertyHouseService {

    @Resource private PropertyHouseMapper houseMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createHouse(PropertyHouseSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && houseMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.HOUSE_CODE_EXISTS);
        }
        PropertyHouseDO house = BeanUtils.toBean(reqVO, PropertyHouseDO.class);
        houseMapper.insert(house);
        return house.getId();
    }

    @Override public void updateHouse(PropertyHouseSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateHouseExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyHouseDO existing = houseMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.HOUSE_CODE_EXISTS);
            }
        }
        PropertyHouseDO house = BeanUtils.toBean(reqVO, PropertyHouseDO.class);
        houseMapper.updateById(house);
    }

    @Override public void deleteHouse(Long id) {
        validateHouseExists(id);
        houseMapper.deleteById(id);
    }

    @Override public PropertyHouseDO getHouse(Long id) {
        return houseMapper.selectById(id);
    }

    @Override public PageResult<PropertyHouseDO> getHousePage(Long projectId, String name, String code, Long floorId, Long unitId, Long buildingId, Long communityId, Integer houseStatus, Integer status, Integer pageNo, Integer pageSize) {
        return houseMapper.selectPage(projectId, name, code, floorId, unitId, buildingId, communityId, houseStatus, status, pageNo, pageSize);
    }

    @Override public List<PropertyHouseDO> getHouseList() {
        return houseMapper.selectList();
    }

    @Override public List<PropertyHouseDO> getHouseListByFloorId(Long floorId) {
        return houseMapper.selectListByFloorId(floorId);
    }

    @Override public List<PropertyHouseDO> getHouseListByUnitId(Long unitId) {
        return houseMapper.selectListByUnitId(unitId);
    }

    @Override public List<PropertyHouseDO> getHouseListByBuildingId(Long buildingId) {
        return houseMapper.selectListByBuildingId(buildingId);
    }

    @Override public List<PropertyHouseDO> getHouseListByCommunityId(Long communityId) {
        return houseMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyHouseDO> getHouseListByHouseStatus(Integer houseStatus) {
        return houseMapper.selectListByHouseStatus(houseStatus);
    }

    @Override public List<PropertyHouseDO> getHouseListByStatus(Integer status) {
        return houseMapper.selectListByStatus(status);
    }

    private void validateHouseExists(Long id) {
        if (houseMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.HOUSE_NOT_EXISTS);
        }
    }
}

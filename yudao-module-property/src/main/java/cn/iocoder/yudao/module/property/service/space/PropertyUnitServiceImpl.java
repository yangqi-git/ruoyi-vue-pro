package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyUnitSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyUnitDO;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyUnitMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyUnitServiceImpl implements PropertyUnitService {

    @Resource private PropertyUnitMapper unitMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createUnit(PropertyUnitSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && unitMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.UNIT_CODE_EXISTS);
        }
        PropertyUnitDO unit = BeanUtils.toBean(reqVO, PropertyUnitDO.class);
        unitMapper.insert(unit);
        return unit.getId();
    }

    @Override public void updateUnit(PropertyUnitSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateUnitExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyUnitDO existing = unitMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.UNIT_CODE_EXISTS);
            }
        }
        PropertyUnitDO unit = BeanUtils.toBean(reqVO, PropertyUnitDO.class);
        unitMapper.updateById(unit);
    }

    @Override public void deleteUnit(Long id) {
        validateUnitExists(id);
        unitMapper.deleteById(id);
    }

    @Override public PropertyUnitDO getUnit(Long id) {
        return unitMapper.selectById(id);
    }

    @Override public PageResult<PropertyUnitDO> getUnitPage(Long projectId, String name, String code, Long buildingId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        return unitMapper.selectPage(projectId, name, code, buildingId, communityId, status, pageNo, pageSize);
    }

    @Override public List<PropertyUnitDO> getUnitList() {
        return unitMapper.selectList();
    }

    @Override public List<PropertyUnitDO> getUnitListByBuildingId(Long buildingId) {
        return unitMapper.selectListByBuildingId(buildingId);
    }

    @Override public List<PropertyUnitDO> getUnitListByCommunityId(Long communityId) {
        return unitMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyUnitDO> getUnitListByStatus(Integer status) {
        return unitMapper.selectListByStatus(status);
    }

    private void validateUnitExists(Long id) {
        if (unitMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.UNIT_NOT_EXISTS);
        }
    }
}

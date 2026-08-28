package cn.iocoder.yudao.module.property.service.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.PropertyResidentSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyResidentMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyResidentServiceImpl implements PropertyResidentService {

    @Resource private PropertyResidentMapper residentMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createResident(PropertyResidentSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyResidentDO resident = BeanUtils.toBean(reqVO, PropertyResidentDO.class);
        residentMapper.insert(resident);
        return resident.getId();
    }

    @Override public void updateResident(PropertyResidentSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateResidentExists(reqVO.getId());
        PropertyResidentDO resident = BeanUtils.toBean(reqVO, PropertyResidentDO.class);
        residentMapper.updateById(resident);
    }

    @Override public void deleteResident(Long id) {
        validateResidentExists(id);
        residentMapper.deleteById(id);
    }

    @Override public PropertyResidentDO getResident(Long id) {
        return residentMapper.selectById(id);
    }

    @Override public PageResult<PropertyResidentDO> getResidentPage(Long projectId, String name, String idCard, Long houseId, Long communityId, Integer residentType, Integer status, Integer pageNo, Integer pageSize) {
        return residentMapper.selectPage(projectId, name, idCard, houseId, communityId, residentType, status, pageNo, pageSize);
    }

    @Override public List<PropertyResidentDO> getResidentList() {
        return residentMapper.selectList();
    }

    @Override public List<PropertyResidentDO> getResidentListByHouseId(Long houseId) {
        return residentMapper.selectListByHouseId(houseId);
    }

    @Override public List<PropertyResidentDO> getResidentListByCommunityId(Long communityId) {
        return residentMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyResidentDO> getResidentListByResidentType(Integer residentType) {
        return residentMapper.selectListByResidentType(residentType);
    }

    @Override public List<PropertyResidentDO> getResidentListByStatus(Integer status) {
        return residentMapper.selectListByStatus(status);
    }

    private void validateResidentExists(Long id) {
        if (residentMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.RESIDENT_NOT_EXISTS);
        }
    }
}

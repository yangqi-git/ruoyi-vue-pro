package cn.iocoder.yudao.module.property.service.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.PropertyProjectSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyProjectDO;
import cn.iocoder.yudao.module.property.dal.mysql.org.PropertyProjectMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyProjectServiceImpl implements PropertyProjectService {

    @Resource private PropertyProjectMapper projectMapper;

    @Override public Long createProject(PropertyProjectSaveReqVO reqVO) {
        if (reqVO.getCode() != null && projectMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.PROJECT_CODE_EXISTS);
        }
        PropertyProjectDO project = BeanUtils.toBean(reqVO, PropertyProjectDO.class);
        projectMapper.insert(project);
        return project.getId();
    }

    @Override public void updateProject(PropertyProjectSaveReqVO reqVO) {
        validateProjectExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyProjectDO existing = projectMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.PROJECT_CODE_EXISTS);
            }
        }
        PropertyProjectDO project = BeanUtils.toBean(reqVO, PropertyProjectDO.class);
        projectMapper.updateById(project);
    }

    @Override public void deleteProject(Long id) {
        validateProjectExists(id);
        projectMapper.deleteById(id);
    }

    @Override public PropertyProjectDO getProject(Long id) {
        return projectMapper.selectById(id);
    }

    @Override public PageResult<PropertyProjectDO> getProjectPage(String name, String code, Integer status, Integer pageNo, Integer pageSize) {
        return projectMapper.selectPage(name, code, status, pageNo, pageSize);
    }

    @Override public List<PropertyProjectDO> getProjectList() {
        return projectMapper.selectList();
    }

    @Override public List<PropertyProjectDO> getProjectListByStatus(Integer status) {
        return projectMapper.selectListByStatus(status);
    }

    @Override
    public void validateProject(Long id) {
        if (projectMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.PROPERTY_PROJECT_NOT_EXISTS);
        }
    }

    private void validateProjectExists(Long id) {
        validateProject(id);
    }
}

package cn.iocoder.yudao.module.property.service.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyProjectDO;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.PropertyProjectSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyProjectService {

    Long createProject(@Valid PropertyProjectSaveReqVO reqVO);

    void updateProject(@Valid PropertyProjectSaveReqVO reqVO);

    void deleteProject(Long id);

    PropertyProjectDO getProject(Long id);

    /**
     * 校验物业项目存在。
     *
     * @param id 项目编号
     */
    void validateProject(Long id);

    PageResult<PropertyProjectDO> getProjectPage(String name, String code, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyProjectDO> getProjectList();

    List<PropertyProjectDO> getProjectListByStatus(Integer status);
}

package cn.iocoder.yudao.module.property.service.approval;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.approval.PropertyApprovalConfigDO;
import cn.iocoder.yudao.module.property.dal.mysql.approval.PropertyApprovalConfigMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyApprovalConfigServiceImpl implements PropertyApprovalConfigService {

    @Resource private PropertyApprovalConfigMapper approvalConfigMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override
    public Long createApprovalConfig(PropertyApprovalConfigSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyApprovalConfigDO config = BeanUtils.toBean(reqVO, PropertyApprovalConfigDO.class);
        approvalConfigMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateApprovalConfig(PropertyApprovalConfigSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateApprovalConfigExists(reqVO.getId());
        PropertyApprovalConfigDO config = BeanUtils.toBean(reqVO, PropertyApprovalConfigDO.class);
        approvalConfigMapper.updateById(config);
    }

    @Override
    public void deleteApprovalConfig(Long id) {
        validateApprovalConfigExists(id);
        approvalConfigMapper.deleteById(id);
    }

    @Override
    public PropertyApprovalConfigDO getApprovalConfig(Long id) {
        return approvalConfigMapper.selectById(id);
    }

    @Override
    public PageResult<PropertyApprovalConfigDO> getApprovalConfigPage(Long projectId, Long communityId, Integer approvalType,
            Integer status, Integer pageNo, Integer pageSize) {
        return approvalConfigMapper.selectPage(projectId, communityId, approvalType, status, pageNo, pageSize);
    }

    @Override
    public List<PropertyApprovalConfigDO> getApprovalConfigListByCommunityId(Long communityId) {
        return approvalConfigMapper.selectListByCommunityId(communityId);
    }

    @Override
    public List<PropertyApprovalConfigDO> getActiveApprovalConfigList(Long communityId) {
        return approvalConfigMapper.selectActiveListByCommunityId(communityId);
    }

    private void validateApprovalConfigExists(Long id) {
        if (approvalConfigMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.APPROVAL_CONFIG_NOT_EXISTS);
        }
    }
}

package cn.iocoder.yudao.module.property.service.approval;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.approval.PropertyApprovalConfigDO;
import java.util.List;

public interface PropertyApprovalConfigService {

    Long createApprovalConfig(PropertyApprovalConfigSaveReqVO reqVO);

    void updateApprovalConfig(PropertyApprovalConfigSaveReqVO reqVO);

    void deleteApprovalConfig(Long id);

    PropertyApprovalConfigDO getApprovalConfig(Long id);

    PageResult<PropertyApprovalConfigDO> getApprovalConfigPage(Long projectId, Long communityId, Integer approvalType,
            Integer status, Integer pageNo, Integer pageSize);

    List<PropertyApprovalConfigDO> getApprovalConfigListByCommunityId(Long communityId);

    List<PropertyApprovalConfigDO> getActiveApprovalConfigList(Long communityId);
}

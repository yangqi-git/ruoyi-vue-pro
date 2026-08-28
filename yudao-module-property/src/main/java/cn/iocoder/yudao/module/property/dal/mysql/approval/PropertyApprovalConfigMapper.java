package cn.iocoder.yudao.module.property.dal.mysql.approval;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.approval.PropertyApprovalConfigDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyApprovalConfigMapper extends BaseMapperX<PropertyApprovalConfigDO> {

    default PageResult<PropertyApprovalConfigDO> selectPage(Long projectId, Long communityId, Integer approvalType,
            Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyApprovalConfigDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyApprovalConfigDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyApprovalConfigDO>()
                .eqIfPresent(PropertyApprovalConfigDO::getProjectId, projectId)
                .eqIfPresent(PropertyApprovalConfigDO::getCommunityId, communityId)
                .eqIfPresent(PropertyApprovalConfigDO::getApprovalType, approvalType)
                .eqIfPresent(PropertyApprovalConfigDO::getStatus, status)
                .
        orderByDesc(PropertyApprovalConfigDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyApprovalConfigDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyApprovalConfigDO::getCommunityId, communityId);
    }

    default List<PropertyApprovalConfigDO> selectActiveListByCommunityId(Long communityId) {
        return selectList(new LambdaQueryWrapperX<PropertyApprovalConfigDO>()
                .eq(PropertyApprovalConfigDO::getCommunityId, communityId)
                .eq(PropertyApprovalConfigDO::getStatus, 1));
    }
}

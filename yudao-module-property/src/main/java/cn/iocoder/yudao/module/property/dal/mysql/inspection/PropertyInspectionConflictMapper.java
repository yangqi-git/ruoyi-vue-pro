package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionConflictDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyInspectionConflictMapper extends BaseMapperX<PropertyInspectionConflictDO> {
    default PropertyInspectionConflictDO selectByClientOperationId(String clientOperationId) {
        return selectOne(PropertyInspectionConflictDO::getClientOperationId, clientOperationId);
    }

    default PageResult<PropertyInspectionConflictDO> selectPage(PropertyInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInspectionConflictDO>()
                .eq(PropertyInspectionConflictDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionConflictDO::getStatus, reqVO.getStatus())
                .orderByAsc(PropertyInspectionConflictDO::getStatus)
                .orderByDesc(PropertyInspectionConflictDO::getId));
    }
}

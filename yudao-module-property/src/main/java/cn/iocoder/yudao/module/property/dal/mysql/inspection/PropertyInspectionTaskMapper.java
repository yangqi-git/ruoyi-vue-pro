package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyInspectionTaskMapper extends BaseMapperX<PropertyInspectionTaskDO> {
    default PageResult<PropertyInspectionTaskDO> selectPage(PropertyInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInspectionTaskDO>()
                .eq(PropertyInspectionTaskDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionTaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInspectionTaskDO::getSpecialty, reqVO.getSpecialty())
                .eqIfPresent(PropertyInspectionTaskDO::getInspectorUserId, reqVO.getInspectorUserId())
                .orderByAsc(PropertyInspectionTaskDO::getPlannedEndTime)
                .orderByDesc(PropertyInspectionTaskDO::getId));
    }
}

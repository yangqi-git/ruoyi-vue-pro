package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPlanDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PropertyInspectionPlanMapper extends BaseMapperX<PropertyInspectionPlanDO> {
    default PageResult<PropertyInspectionPlanDO> selectPage(PropertyInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInspectionPlanDO>()
                .eq(PropertyInspectionPlanDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionPlanDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInspectionPlanDO::getSpecialty, reqVO.getSpecialty())
                .eqIfPresent(PropertyInspectionPlanDO::getInspectorUserId, reqVO.getInspectorUserId())
                .orderByDesc(PropertyInspectionPlanDO::getId));
    }

    default List<PropertyInspectionPlanDO> selectDueList(LocalDateTime now) {
        return selectList(new LambdaQueryWrapperX<PropertyInspectionPlanDO>()
                .eq(PropertyInspectionPlanDO::getStatus, 0)
                .le(PropertyInspectionPlanDO::getNextGenerateTime, now)
                .orderByAsc(PropertyInspectionPlanDO::getNextGenerateTime).last("LIMIT 200"));
    }
}

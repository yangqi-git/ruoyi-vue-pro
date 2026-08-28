package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionQualitySampleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyInspectionQualitySampleMapper extends BaseMapperX<PropertyInspectionQualitySampleDO> {
    default PageResult<PropertyInspectionQualitySampleDO> selectPage(PropertyInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInspectionQualitySampleDO>()
                .eq(PropertyInspectionQualitySampleDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionQualitySampleDO::getStatus, reqVO.getStatus())
                .orderByAsc(PropertyInspectionQualitySampleDO::getStatus)
                .orderByDesc(PropertyInspectionQualitySampleDO::getId));
    }
}

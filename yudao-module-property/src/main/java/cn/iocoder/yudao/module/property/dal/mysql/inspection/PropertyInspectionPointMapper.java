package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionResourcePageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyInspectionPointMapper extends BaseMapperX<PropertyInspectionPointDO> {
    default PropertyInspectionPointDO selectByProjectAndCode(Long projectId, String code) {
        return selectOne(new LambdaQueryWrapperX<PropertyInspectionPointDO>()
                .eq(PropertyInspectionPointDO::getProjectId, projectId)
                .eq(PropertyInspectionPointDO::getPointCode, code));
    }

    default PageResult<PropertyInspectionPointDO> selectPage(PropertyInspectionResourcePageReqVO reqVO) {
        LambdaQueryWrapperX<PropertyInspectionPointDO> wrapper = new LambdaQueryWrapperX<PropertyInspectionPointDO>()
                .eq(PropertyInspectionPointDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionPointDO::getPointType, reqVO.getSpecialty())
                .eqIfPresent(PropertyInspectionPointDO::getStatus, reqVO.getStatus())
                .orderByDesc(PropertyInspectionPointDO::getId);
        if (StrUtil.isNotBlank(reqVO.getKeyword())) {
            wrapper.and(item -> item.like(PropertyInspectionPointDO::getName, reqVO.getKeyword()).or()
                    .like(PropertyInspectionPointDO::getPointCode, reqVO.getKeyword()));
        }
        return selectPage(reqVO, wrapper);
    }
}

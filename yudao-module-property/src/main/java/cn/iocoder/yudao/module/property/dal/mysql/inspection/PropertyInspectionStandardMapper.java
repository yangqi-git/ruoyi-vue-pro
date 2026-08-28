package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionResourcePageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PropertyInspectionStandardMapper extends BaseMapperX<PropertyInspectionStandardDO> {
    default PropertyInspectionStandardDO selectLatestByCode(Long projectId, String code) {
        return selectOne(new LambdaQueryWrapperX<PropertyInspectionStandardDO>()
                .eq(PropertyInspectionStandardDO::getProjectId, projectId)
                .eq(PropertyInspectionStandardDO::getStandardCode, code)
                .orderByDesc(PropertyInspectionStandardDO::getStandardVersion).last("LIMIT 1"));
    }

    default PageResult<PropertyInspectionStandardDO> selectPage(PropertyInspectionResourcePageReqVO reqVO) {
        LambdaQueryWrapperX<PropertyInspectionStandardDO> wrapper = new LambdaQueryWrapperX<PropertyInspectionStandardDO>()
                .eq(PropertyInspectionStandardDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionStandardDO::getSpecialty, reqVO.getSpecialty())
                .eqIfPresent(PropertyInspectionStandardDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInspectionStandardDO::getPublished, reqVO.getPublished())
                .orderByDesc(PropertyInspectionStandardDO::getId);
        if (StrUtil.isNotBlank(reqVO.getKeyword())) {
            wrapper.and(item -> item.like(PropertyInspectionStandardDO::getName, reqVO.getKeyword()).or()
                    .like(PropertyInspectionStandardDO::getStandardCode, reqVO.getKeyword()));
        }
        return selectPage(reqVO, wrapper);
    }
}

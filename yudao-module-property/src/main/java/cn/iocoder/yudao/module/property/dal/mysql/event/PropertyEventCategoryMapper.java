package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategoryPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyEventCategoryMapper extends BaseMapperX<PropertyEventCategoryDO> {
    default PropertyEventCategoryDO selectByProjectAndCode(Long projectId, String code) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventCategoryDO>()
                .eq(PropertyEventCategoryDO::getProjectId, projectId)
                .eq(PropertyEventCategoryDO::getCode, code));
    }

    default PageResult<PropertyEventCategoryDO> selectPage(PropertyEventCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyEventCategoryDO>()
                .eq(PropertyEventCategoryDO::getProjectId, reqVO.getProjectId())
                .likeIfPresent(PropertyEventCategoryDO::getName, reqVO.getName())
                .likeIfPresent(PropertyEventCategoryDO::getCode, reqVO.getCode())
                .eqIfPresent(PropertyEventCategoryDO::getStatus, reqVO.getStatus())
                .orderByAsc(PropertyEventCategoryDO::getSort)
                .orderByDesc(PropertyEventCategoryDO::getId));
    }

    default List<PropertyEventCategoryDO> selectEnabledList(Long projectId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventCategoryDO>()
                .eq(PropertyEventCategoryDO::getProjectId, projectId)
                .eq(PropertyEventCategoryDO::getStatus, 0)
                .orderByAsc(PropertyEventCategoryDO::getSort));
    }
}

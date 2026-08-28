package cn.iocoder.yudao.module.property.dal.mysql.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyBuildingDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyBuildingMapper extends BaseMapperX<PropertyBuildingDO> {

    default PageResult<PropertyBuildingDO> selectPage(Long projectId, String name, String code, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyBuildingDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyBuildingDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyBuildingDO>()
                .eqIfPresent(PropertyBuildingDO::getProjectId, projectId)
                .likeIfPresent(PropertyBuildingDO::getName, name)
                .eqIfPresent(PropertyBuildingDO::getCode, code)
                .eqIfPresent(PropertyBuildingDO::getCommunityId, communityId)
                .eqIfPresent(PropertyBuildingDO::getStatus, status)
                .
        orderByDesc(PropertyBuildingDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyBuildingDO selectByCode(String code) {
        return selectOne(PropertyBuildingDO::getCode, code);
    }

    default List<PropertyBuildingDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyBuildingDO::getCommunityId, communityId);
    }

    default List<PropertyBuildingDO> selectListByStatus(Integer status) {
        return selectList(PropertyBuildingDO::getStatus, status);
    }

    default List<PropertyBuildingDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyBuildingDO>()
                .orderByDesc(PropertyBuildingDO::getId));
    }
}

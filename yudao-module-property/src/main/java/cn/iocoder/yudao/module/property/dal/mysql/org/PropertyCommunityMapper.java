package cn.iocoder.yudao.module.property.dal.mysql.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyCommunityDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyCommunityMapper extends BaseMapperX<PropertyCommunityDO> {

    default PageResult<PropertyCommunityDO> selectPage(String name, String code, Long projectId, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyCommunityDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyCommunityDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyCommunityDO>()
                .likeIfPresent(PropertyCommunityDO::getName, name)
                .eqIfPresent(PropertyCommunityDO::getCode, code)
                .eqIfPresent(PropertyCommunityDO::getProjectId, projectId)
                .eqIfPresent(PropertyCommunityDO::getStatus, status)
                .
        orderByDesc(PropertyCommunityDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyCommunityDO selectByCode(String code) {
        return selectOne(PropertyCommunityDO::getCode, code);
    }

    default List<PropertyCommunityDO> selectListByProjectId(Long projectId) {
        return selectList(PropertyCommunityDO::getProjectId, projectId);
    }

    default List<PropertyCommunityDO> selectListByStatus(Integer status) {
        return selectList(PropertyCommunityDO::getStatus, status);
    }

    default List<PropertyCommunityDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyCommunityDO>()
                .orderByDesc(PropertyCommunityDO::getId));
    }
}
package cn.iocoder.yudao.module.property.dal.mysql.space;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyResidentMapper extends BaseMapperX<PropertyResidentDO> {

    default PageResult<PropertyResidentDO> selectPage(Long projectId, String name, String idCard, Long houseId, Long communityId, Integer residentType, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyResidentDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyResidentDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyResidentDO>()
                .eqIfPresent(PropertyResidentDO::getProjectId, projectId)
                .likeIfPresent(PropertyResidentDO::getName, name)
                .eqIfPresent(PropertyResidentDO::getIdCard, idCard)
                .eqIfPresent(PropertyResidentDO::getHouseId, houseId)
                .eqIfPresent(PropertyResidentDO::getCommunityId, communityId)
                .eqIfPresent(PropertyResidentDO::getResidentType, residentType)
                .eqIfPresent(PropertyResidentDO::getStatus, status)
                .
        orderByDesc(PropertyResidentDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyResidentDO> selectListByHouseId(Long houseId) {
        return selectList(PropertyResidentDO::getHouseId, houseId);
    }

    default List<PropertyResidentDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyResidentDO::getCommunityId, communityId);
    }

    default List<PropertyResidentDO> selectListByResidentType(Integer residentType) {
        return selectList(PropertyResidentDO::getResidentType, residentType);
    }

    default List<PropertyResidentDO> selectListByStatus(Integer status) {
        return selectList(PropertyResidentDO::getStatus, status);
    }

    default List<PropertyResidentDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyResidentDO>()
                .orderByDesc(PropertyResidentDO::getId));
    }
}

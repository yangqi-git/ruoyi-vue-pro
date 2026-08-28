package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventRelationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyEventRelationMapper extends BaseMapperX<PropertyEventRelationDO> {

    default List<PropertyEventRelationDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventRelationDO>()
                .and(wrapper -> wrapper.eq(PropertyEventRelationDO::getMainEventId, eventId)
                        .or().eq(PropertyEventRelationDO::getRelatedEventId, eventId))
                .orderByAsc(PropertyEventRelationDO::getId));
    }
}

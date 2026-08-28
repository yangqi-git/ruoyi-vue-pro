package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaPauseDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyEventSlaPauseMapper extends BaseMapperX<PropertyEventSlaPauseDO> {

    default PropertyEventSlaPauseDO selectActiveByEventId(Long eventId) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventSlaPauseDO>()
                .eq(PropertyEventSlaPauseDO::getEventId, eventId)
                .isNull(PropertyEventSlaPauseDO::getResumedTime)
                .orderByDesc(PropertyEventSlaPauseDO::getId)
                .last("LIMIT 1"));
    }

    default List<PropertyEventSlaPauseDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventSlaPauseDO>()
                .eq(PropertyEventSlaPauseDO::getEventId, eventId)
                .orderByAsc(PropertyEventSlaPauseDO::getId));
    }
}

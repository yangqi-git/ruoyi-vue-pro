package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyEventTimelineMapper extends BaseMapperX<PropertyEventTimelineDO> {
    default List<PropertyEventTimelineDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventTimelineDO>()
                .eq(PropertyEventTimelineDO::getEventId, eventId)
                .orderByAsc(PropertyEventTimelineDO::getId));
    }
}

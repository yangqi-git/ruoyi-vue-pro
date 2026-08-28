package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventEvidenceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyEventEvidenceMapper extends BaseMapperX<PropertyEventEvidenceDO> {
    default List<PropertyEventEvidenceDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventEvidenceDO>()
                .eq(PropertyEventEvidenceDO::getEventId, eventId)
                .orderByDesc(PropertyEventEvidenceDO::getId));
    }

    default Long selectCountByEventId(Long eventId) {
        return selectCount(PropertyEventEvidenceDO::getEventId, eventId);
    }

    default List<PropertyEventEvidenceDO> selectListForValidation(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventEvidenceDO>()
                .eq(PropertyEventEvidenceDO::getEventId, eventId));
    }
}

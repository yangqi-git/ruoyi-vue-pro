package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PropertyEventNotificationMapper extends BaseMapperX<PropertyEventNotificationDO> {

    default PropertyEventNotificationDO selectByIdempotencyKey(String idempotencyKey) {
        return selectOne(PropertyEventNotificationDO::getIdempotencyKey, idempotencyKey);
    }

    default PropertyEventNotificationDO selectByMessageNo(String messageNo) {
        return selectOne(PropertyEventNotificationDO::getMessageNo, messageNo);
    }

    default List<PropertyEventNotificationDO> selectPendingList(LocalDateTime now) {
        return selectList(new LambdaQueryWrapperX<PropertyEventNotificationDO>()
                .in(PropertyEventNotificationDO::getStatus, 0, 30)
                .lt(PropertyEventNotificationDO::getRetryCount, 3)
                .and(wrapper -> wrapper.isNull(PropertyEventNotificationDO::getNextRetryTime)
                        .or().le(PropertyEventNotificationDO::getNextRetryTime, now))
                .orderByAsc(PropertyEventNotificationDO::getId)
                .last("LIMIT 200"));
    }

    default Long selectUnsentCount(Long eventId) {
        return selectCount(new LambdaQueryWrapperX<PropertyEventNotificationDO>()
                .eq(PropertyEventNotificationDO::getEventId, eventId)
                .in(PropertyEventNotificationDO::getStatus, 0, 30));
    }

    default List<PropertyEventNotificationDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyEventNotificationDO>()
                .eq(PropertyEventNotificationDO::getEventId, eventId)
                .orderByDesc(PropertyEventNotificationDO::getId));
    }

    default PageResult<PropertyEventNotificationDO> selectPage(PropertyEventNotificationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyEventNotificationDO>()
                .eq(PropertyEventNotificationDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyEventNotificationDO::getEventId, reqVO.getEventId())
                .eqIfPresent(PropertyEventNotificationDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyEventNotificationDO::getNotificationType, reqVO.getNotificationType())
                .orderByDesc(PropertyEventNotificationDO::getId));
    }
}

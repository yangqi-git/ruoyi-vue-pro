package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyServiceRecoveryTaskMapper extends BaseMapperX<PropertyServiceRecoveryTaskDO> {

    default PropertyServiceRecoveryTaskDO selectByEventAndTrigger(Long eventId, Integer triggerType) {
        return selectOne(new LambdaQueryWrapperX<PropertyServiceRecoveryTaskDO>()
                .eq(PropertyServiceRecoveryTaskDO::getEventId, eventId)
                .eq(PropertyServiceRecoveryTaskDO::getTriggerType, triggerType));
    }

    default List<PropertyServiceRecoveryTaskDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyServiceRecoveryTaskDO>()
                .eq(PropertyServiceRecoveryTaskDO::getEventId, eventId)
                .orderByDesc(PropertyServiceRecoveryTaskDO::getId));
    }

    default PageResult<PropertyServiceRecoveryTaskDO> selectPage(PropertyServiceRecoveryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyServiceRecoveryTaskDO>()
                .eq(PropertyServiceRecoveryTaskDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyServiceRecoveryTaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyServiceRecoveryTaskDO::getTriggerType, reqVO.getTriggerType())
                .eqIfPresent(PropertyServiceRecoveryTaskDO::getResponsibleUserId, reqVO.getResponsibleUserId())
                .orderByAsc(PropertyServiceRecoveryTaskDO::getStatus)
                .orderByDesc(PropertyServiceRecoveryTaskDO::getId));
    }
}

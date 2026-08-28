package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;

import java.util.List;

public interface PropertyServiceRecoveryService {
    Long createNegativeReviewTask(PropertyServiceRecoveryCreateReqVO reqVO);
    Long createAutomaticTask(PropertyEventDO event, Integer triggerType, String detail);
    PageResult<PropertyServiceRecoveryTaskDO> getPage(PropertyServiceRecoveryPageReqVO reqVO);
    List<PropertyServiceRecoveryTaskDO> getListByEventId(Long eventId, Long projectId);
    void executeAction(PropertyServiceRecoveryActionReqVO reqVO);
}

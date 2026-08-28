package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventDetailRespVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventDispatchReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventEvidenceCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventMergeReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSplitReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventStatsRespVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventTransferReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;

public interface PropertyEventService {

    Long createEvent(PropertyEventCreateReqVO reqVO);

    PageResult<PropertyEventDO> getEventPage(PropertyEventPageReqVO reqVO);

    PropertyEventDetailRespVO getEventDetail(Long id, Long projectId);

    PropertyEventStatsRespVO getEventStats(Long projectId);

    void dispatchEvent(PropertyEventDispatchReqVO reqVO);

    void transferEvent(PropertyEventTransferReqVO reqVO);

    void mergeEvent(PropertyEventMergeReqVO reqVO);

    Long splitEvent(PropertyEventSplitReqVO reqVO);

    void transitionEvent(PropertyEventActionReqVO reqVO, PropertyEventActionEnum action);

    Long addEvidence(PropertyEventEvidenceCreateReqVO reqVO);
}

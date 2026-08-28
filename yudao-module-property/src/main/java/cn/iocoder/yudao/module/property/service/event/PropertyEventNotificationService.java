package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationReceiptReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;

import java.util.List;

public interface PropertyEventNotificationService {
    int enqueueStatusNotification(PropertyEventDO event, String notificationType, String content);
    int processPendingNotifications();
    PageResult<PropertyEventNotificationDO> getPage(PropertyEventNotificationPageReqVO reqVO);
    List<PropertyEventNotificationDO> getListByEventId(Long eventId, Long projectId);
    void retry(Long id, Long projectId);
    void recordReceipt(PropertyEventNotificationReceiptReqVO reqVO);
}

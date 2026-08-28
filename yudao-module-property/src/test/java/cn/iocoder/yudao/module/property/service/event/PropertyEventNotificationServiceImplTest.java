package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationReceiptReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventNotificationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventRelationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.space.PropertyResidentMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import cn.iocoder.yudao.module.system.api.sms.SmsSendApi;
import cn.iocoder.yudao.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyEventNotificationServiceImplTest {

    @InjectMocks
    private PropertyEventNotificationServiceImpl service;
    @Mock
    private PropertyEventNotificationMapper notificationMapper;
    @Mock
    private PropertyEventMapper eventMapper;
    @Mock
    private PropertyEventRelationMapper relationMapper;
    @Mock
    private PropertyEventTimelineMapper timelineMapper;
    @Mock
    private PropertyResidentMapper residentMapper;
    @Mock
    private PropertyProjectService projectService;
    @Mock
    private SmsSendApi smsSendApi;

    @Test
    void shouldQueueOnlyReachableActiveResidents() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).eventNo("EVT-1").projectId(10L)
                .communityId(20L).houseId(30L).status(10).version(2).build();
        when(relationMapper.selectListByEventId(1L)).thenReturn(List.of());
        when(residentMapper.selectListByHouseId(30L)).thenReturn(List.of(
                PropertyResidentDO.builder().id(101L).name("张三").phone("13800000000").status(0).build(),
                PropertyResidentDO.builder().id(102L).name("李四").phone("").status(0).build(),
                PropertyResidentDO.builder().id(103L).name("王五").phone("13900000000").status(1).build()));

        assertEquals(1, service.enqueueStatusNotification(event, "RECEIVED", "诉求已受理"));

        ArgumentCaptor<PropertyEventNotificationDO> captor = ArgumentCaptor.forClass(PropertyEventNotificationDO.class);
        verify(notificationMapper).insert(captor.capture());
        assertEquals(101L, captor.getValue().getResidentId());
        assertEquals("property-event-status", captor.getValue().getTemplateCode());
        assertEquals(0, captor.getValue().getStatus());
        verify(timelineMapper).insert(any(PropertyEventTimelineDO.class));
    }

    @Test
    void shouldSendPendingMessageAndKeepExternalReceiptKey() {
        PropertyEventNotificationDO notification = PropertyEventNotificationDO.builder()
                .id(8L).eventId(1L).recipientMobile("13800000000")
                .notificationType("DISPATCHED").templateCode("property-event-status")
                .content("已派单").status(0).retryCount(0).build();
        when(notificationMapper.selectPendingList(any())).thenReturn(List.of(notification));
        when(eventMapper.selectById(1L)).thenReturn(PropertyEventDO.builder().eventNo("EVT-1").build());
        when(smsSendApi.sendSingleSmsToMember(any(SmsSendSingleToUserReqDTO.class))).thenReturn(99L);

        assertEquals(1, service.processPendingNotifications());

        assertEquals(10, notification.getStatus());
        assertEquals("99", notification.getExternalMessageId());
        assertNotNull(notification.getSentTime());
        assertNull(notification.getNextRetryTime());
        verify(notificationMapper).updateById(notification);
    }

    @Test
    void shouldRecordDeliveredReceipt() {
        PropertyEventNotificationDO notification = PropertyEventNotificationDO.builder()
                .id(8L).messageNo("MSG-1").projectId(10L).status(10).version(1).build();
        when(notificationMapper.selectByMessageNo("MSG-1")).thenReturn(notification);
        when(notificationMapper.updateById(notification)).thenReturn(1);
        PropertyEventNotificationReceiptReqVO reqVO = new PropertyEventNotificationReceiptReqVO();
        reqVO.setProjectId(10L);
        reqVO.setMessageNo("MSG-1");
        reqVO.setExternalMessageId("provider-1");
        reqVO.setDelivered(true);

        service.recordReceipt(reqVO);

        assertEquals(20, notification.getStatus());
        assertEquals("provider-1", notification.getExternalMessageId());
        assertNotNull(notification.getDeliveredTime());
        assertNull(notification.getFailureReason());
    }
}

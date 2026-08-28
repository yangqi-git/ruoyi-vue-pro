package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyWorkOrderMapper;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyEventSlaEscalationServiceImplTest {

    @InjectMocks
    private PropertyEventSlaEscalationServiceImpl service;
    @Mock
    private PropertyEventMapper eventMapper;
    @Mock
    private PropertyWorkOrderMapper workOrderMapper;
    @Mock
    private PropertyEventTimelineMapper timelineMapper;
    @Mock
    private PropertyServiceRecoveryService recoveryService;

    @Test
    void shouldEscalateWhenResponseIsNearDeadline() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PENDING_CONFIRM.getStatus())
                .responseDeadline(LocalDateTime.now().plusMinutes(5)).slaEscalationMinutes(15)
                .slaEscalationLevel(0).version(0).build();
        when(eventMapper.selectSlaEscalationCandidates()).thenReturn(List.of(event));
        when(eventMapper.updateById(event)).thenReturn(1);

        assertEquals(1, service.processEscalations());

        assertEquals("RESPONSE", event.getSlaEscalationStage());
        assertEquals(1, event.getSlaEscalationLevel());
        ArgumentCaptor<PropertyEventTimelineDO> timeline = ArgumentCaptor.forClass(PropertyEventTimelineDO.class);
        verify(timelineMapper).insert(timeline.capture());
        assertEquals("SLA_ESCALATE", timeline.getValue().getAction());
    }

    @Test
    void shouldReturnPendingAcceptToDispatchPoolWhenOverdue() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PENDING_ACCEPT.getStatus())
                .arrivalDeadline(LocalDateTime.now().minusMinutes(1)).slaEscalationMinutes(15)
                .slaEscalationLevel(1).slaEscalationStage("ARRIVAL").version(0).build();
        PropertyWorkOrderDO order = PropertyWorkOrderDO.builder().id(8L).eventId(1L)
                .status(PropertyEventStatusEnum.PENDING_ACCEPT.getStatus()).build();
        when(eventMapper.selectSlaEscalationCandidates()).thenReturn(List.of(event));
        when(eventMapper.updateById(event)).thenReturn(1);
        when(workOrderMapper.selectLatestByEventId(1L)).thenReturn(order);

        assertEquals(1, service.processEscalations());

        assertEquals(PropertyEventStatusEnum.PENDING_DISPATCH.getStatus(), event.getStatus());
        assertEquals(2, event.getSlaEscalationLevel());
        assertEquals(97, order.getStatus());
        verify(workOrderMapper).updateById(order);
        verify(recoveryService).createAutomaticTask(event, 1, "ARRIVAL SLA 已超时并退回调度池");
    }

    @Test
    void shouldNotEscalatePausedCloseClock() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PENDING_ACCEPTANCE.getStatus())
                .closeDeadline(LocalDateTime.now().minusMinutes(1)).slaPaused(true)
                .slaEscalationMinutes(15).slaEscalationLevel(0).build();
        when(eventMapper.selectSlaEscalationCandidates()).thenReturn(List.of(event));

        assertEquals(0, service.processEscalations());
        verify(eventMapper, never()).updateById(any(PropertyEventDO.class));
        verify(timelineMapper, never()).insert(any(PropertyEventTimelineDO.class));
    }
}

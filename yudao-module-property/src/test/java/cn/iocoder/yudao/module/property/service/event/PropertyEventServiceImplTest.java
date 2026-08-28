package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventDispatchReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventMergeReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSplitReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventTransferReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventEvidenceDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaPauseDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventRelationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventEvidenceMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventCategoryMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaPauseMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaRuleMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventRelationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyServiceRecoveryTaskMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventNotificationMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyWorkOrderMapper;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventActionEnum;
import cn.iocoder.yudao.module.property.enums.event.PropertyEventStatusEnum;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CONCURRENT_UPDATE;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_EVIDENCE_REQUIRED;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_PAUSE_REASON_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyEventServiceImplTest {

    @InjectMocks
    private PropertyEventServiceImpl eventService;
    @Mock
    private PropertyEventMapper eventMapper;
    @Mock
    private PropertyWorkOrderMapper workOrderMapper;
    @Mock
    private PropertyEventTimelineMapper timelineMapper;
    @Mock
    private PropertyEventEvidenceMapper evidenceMapper;
    @Mock
    private PropertyEventCategoryMapper categoryMapper;
    @Mock
    private PropertyEventSlaPauseMapper slaPauseMapper;
    @Mock
    private PropertyEventSlaRuleMapper slaRuleMapper;
    @Mock
    private PropertyEventRelationMapper relationMapper;
    @Mock
    private PropertyServiceRecoveryTaskMapper recoveryTaskMapper;
    @Mock
    private PropertyEventNotificationMapper notificationMapper;
    @Mock
    private PropertyServiceRecoveryService recoveryService;
    @Mock
    private PropertyEventNotificationService notificationService;
    @Mock
    private PropertyProjectService projectService;
    @Mock
    private PropertyEventSlaService slaService;

    @Test
    void shouldCreateEventWithSlaSnapshotAndAuditTimeline() {
        PropertyEventCreateReqVO reqVO = createReqVO();
        LocalDateTime now = LocalDateTime.now();
        when(slaService.calculateSnapshot(any(), any(), any(), any())).thenReturn(
                new PropertyEventSlaService.SlaSnapshot(now.plusMinutes(15), now.plusMinutes(60),
                        now.plusMinutes(240), now.plusMinutes(720), 1L, 2, 15, 60));
        doAnswer(invocation -> {
            PropertyEventDO event = invocation.getArgument(0);
            event.setId(88L);
            return 1;
        }).when(eventMapper).insert(any(PropertyEventDO.class));

        Long id = eventService.createEvent(reqVO);

        assertEquals(88L, id);
        ArgumentCaptor<PropertyEventDO> eventCaptor = ArgumentCaptor.forClass(PropertyEventDO.class);
        verify(eventMapper).insert(eventCaptor.capture());
        PropertyEventDO event = eventCaptor.getValue();
        assertEquals(PropertyEventStatusEnum.PENDING_CONFIRM.getStatus(), event.getStatus());
        assertTrue(event.getEventNo().startsWith("EVT"));
        assertNotNull(event.getResponseDeadline());
        assertNotNull(event.getArrivalDeadline());
        assertNotNull(event.getRecoveryDeadline());
        assertNotNull(event.getCloseDeadline());
        assertEquals(1L, event.getSlaRuleId());
        assertEquals(2, event.getSlaRuleVersion());
        assertEquals(15, event.getSlaEscalationMinutes());
        assertEquals(60, event.getSlaArrivalMinutes());
        assertTrue(event.getResponseDeadline().isBefore(event.getArrivalDeadline()));
        assertTrue(event.getArrivalDeadline().isBefore(event.getRecoveryDeadline()));
        assertTrue(event.getRecoveryDeadline().isBefore(event.getCloseDeadline()));

        ArgumentCaptor<PropertyEventTimelineDO> timelineCaptor =
                ArgumentCaptor.forClass(PropertyEventTimelineDO.class);
        verify(timelineMapper).insert(timelineCaptor.capture());
        assertEquals("CREATE", timelineCaptor.getValue().getAction());
        assertEquals(88L, timelineCaptor.getValue().getEventId());
    }

    @Test
    void shouldReturnExistingEventForSameExternalSource() {
        PropertyEventCreateReqVO reqVO = createReqVO();
        reqVO.setSourceSystem("HOTLINE");
        reqVO.setSourceRecordId("A-100");
        when(eventMapper.selectBySource("HOTLINE", "A-100"))
                .thenReturn(PropertyEventDO.builder().id(9L).build());

        assertEquals(9L, eventService.createEvent(reqVO));
        verify(eventMapper, never()).insert(any(PropertyEventDO.class));
        verify(timelineMapper, never()).insert(any(PropertyEventTimelineDO.class));
    }

    @Test
    void shouldRejectSubmitAcceptanceWithoutEvidence() {
        PropertyEventDO event = PropertyEventDO.builder()
                .id(1L).projectId(10L).status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(3).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        PropertyEventActionReqVO reqVO = actionReqVO(3);
        reqVO.setRecoverySummary("供水已恢复");

        assertServiceException(
                () -> eventService.transitionEvent(reqVO, PropertyEventActionEnum.SUBMIT_ACCEPTANCE),
                EVENT_EVIDENCE_REQUIRED);
        verify(eventMapper, never()).updateById(any(PropertyEventDO.class));
    }

    @Test
    void shouldRejectAcceptanceWhenCategoryRequiredEvidenceTypeIsMissing() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .categoryCode("CUSTOMER_REPAIR")
                .status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(3).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        when(evidenceMapper.selectListForValidation(1L)).thenReturn(List.of(
                PropertyEventEvidenceDO.builder().evidenceType(2).build()));
        when(categoryMapper.selectByProjectAndCode(10L, "CUSTOMER_REPAIR")).thenReturn(
                PropertyEventCategoryDO.builder().requiredEvidenceTypes("1,2").build());
        PropertyEventActionReqVO reqVO = actionReqVO(3);
        reqVO.setRecoverySummary("现场已恢复");

        assertServiceException(
                () -> eventService.transitionEvent(reqVO, PropertyEventActionEnum.SUBMIT_ACCEPTANCE),
                EVENT_EVIDENCE_REQUIRED);
        verify(eventMapper, never()).updateById(any(PropertyEventDO.class));
    }

    @Test
    void shouldRejectStaleVersionBeforeChangingState() {
        PropertyEventDO event = PropertyEventDO.builder()
                .id(1L).projectId(10L).status(PropertyEventStatusEnum.PENDING_CONFIRM.getStatus()).version(4).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);

        assertServiceException(
                () -> eventService.transitionEvent(actionReqVO(3), PropertyEventActionEnum.CONFIRM),
                EVENT_CONCURRENT_UPDATE);
        verify(eventMapper, never()).updateById(any(PropertyEventDO.class));
    }

    @Test
    void shouldNotExposeEventFromAnotherProject() {
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(null);
        assertServiceException(() -> eventService.getEventDetail(1L, 10L), EVENT_NOT_EXISTS);
    }

    @Test
    void shouldTransferByClosingOldOrderWithoutResettingEventSla() {
        LocalDateTime closeDeadline = LocalDateTime.of(2026, 7, 22, 8, 0);
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(2)
                .closeDeadline(closeDeadline).build();
        PropertyWorkOrderDO previous = PropertyWorkOrderDO.builder().id(55L).eventId(1L).projectId(10L)
                .workOrderNo("WO-OLD").assigneeUserId(100L).status(50).version(0).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        when(workOrderMapper.selectLatestByEventId(1L)).thenReturn(previous);
        when(workOrderMapper.updateById(any(PropertyWorkOrderDO.class))).thenReturn(1);
        when(eventMapper.updateById(any(PropertyEventDO.class))).thenReturn(1);
        PropertyEventTransferReqVO reqVO = new PropertyEventTransferReqVO();
        reqVO.setId(1L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(2);
        reqVO.setAssigneeUserId(200L);
        reqVO.setReason("需要持证电工处理");

        eventService.transferEvent(reqVO);

        assertEquals(96, previous.getStatus());
        assertEquals("需要持证电工处理", previous.getTransferReason());
        assertEquals(PropertyEventStatusEnum.PENDING_ACCEPT.getStatus(), event.getStatus());
        assertEquals(closeDeadline, event.getCloseDeadline());
        assertEquals(200L, event.getResponsibleUserId());
        ArgumentCaptor<PropertyWorkOrderDO> nextCaptor = ArgumentCaptor.forClass(PropertyWorkOrderDO.class);
        verify(workOrderMapper).insert(nextCaptor.capture());
        assertEquals(55L, nextCaptor.getValue().getParentWorkOrderId());
        assertEquals(200L, nextCaptor.getValue().getAssigneeUserId());
        verify(timelineMapper).insert(any(PropertyEventTimelineDO.class));
    }

    @Test
    void shouldStartArrivalClockAtFirstDispatch() {
        LocalDateTime staleCreateBasedDeadline = LocalDateTime.now().plusMinutes(10);
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PENDING_DISPATCH.getStatus()).version(2)
                .slaArrivalMinutes(60).arrivalDeadline(staleCreateBasedDeadline).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        when(eventMapper.updateById(event)).thenReturn(1);
        PropertyEventDispatchReqVO reqVO = new PropertyEventDispatchReqVO();
        reqVO.setId(1L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(2);
        reqVO.setAssigneeUserId(100L);

        eventService.dispatchEvent(reqVO);

        assertNotNull(event.getFirstDispatchedTime());
        assertTrue(event.getArrivalDeadline().isAfter(LocalDateTime.now().plusMinutes(59)));
        verify(workOrderMapper).insert(any(PropertyWorkOrderDO.class));
    }

    @Test
    void shouldRejectSlaPauseForNonWhitelistReason() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(2).slaPaused(false).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        PropertyEventActionReqVO reqVO = actionReqVO(2);
        reqVO.setReason("等待内部备件");
        reqVO.setPauseSla(true);
        reqVO.setPauseReasonCode("INTERNAL_RESOURCE");

        assertServiceException(() -> eventService.transitionEvent(reqVO, PropertyEventActionEnum.COLLABORATE),
                EVENT_SLA_PAUSE_REASON_INVALID);
        verify(eventMapper, never()).updateById(any(PropertyEventDO.class));
    }

    @Test
    void shouldResumeAndExtendOnlyCloseDeadlineByPausedDuration() {
        LocalDateTime closeDeadline = LocalDateTime.now().plusHours(4);
        LocalDateTime recoveryDeadline = LocalDateTime.now().plusHours(2);
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L)
                .status(PropertyEventStatusEnum.WAITING_COLLABORATION.getStatus()).version(2)
                .slaPaused(true).slaPausedSeconds(30L).slaPauseReasonCode("EXTERNAL_APPROVAL")
                .slaPauseStartedTime(LocalDateTime.now().minusMinutes(10))
                .recoveryDeadline(recoveryDeadline).closeDeadline(closeDeadline).build();
        PropertyEventSlaPauseDO pause = PropertyEventSlaPauseDO.builder().id(8L).eventId(1L).projectId(10L)
                .reasonCode("EXTERNAL_APPROVAL").startedTime(LocalDateTime.now().minusMinutes(10)).build();
        PropertyWorkOrderDO order = PropertyWorkOrderDO.builder().id(9L).eventId(1L).status(60).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(event);
        when(slaPauseMapper.selectActiveByEventId(1L)).thenReturn(pause);
        when(eventMapper.updateById(any(PropertyEventDO.class))).thenReturn(1);
        when(workOrderMapper.selectLatestByEventId(1L)).thenReturn(order);
        when(workOrderMapper.updateById(any(PropertyWorkOrderDO.class))).thenReturn(1);

        eventService.transitionEvent(actionReqVO(2), PropertyEventActionEnum.RESUME);

        assertEquals(PropertyEventStatusEnum.PROCESSING.getStatus(), event.getStatus());
        assertEquals(false, event.getSlaPaused());
        assertEquals(recoveryDeadline, event.getRecoveryDeadline());
        assertTrue(event.getCloseDeadline().isAfter(closeDeadline.plusMinutes(9)));
        assertTrue(event.getSlaPausedSeconds() >= 600L);
        assertNotNull(pause.getResumedTime());
        verify(slaPauseMapper).updateById(pause);
    }

    @Test
    void shouldMergeIntoMainAndKeepEarliestSlaStart() {
        LocalDateTime mainStart = LocalDateTime.now().minusHours(1);
        LocalDateTime relatedStart = mainStart.minusMinutes(30);
        PropertyEventDO main = PropertyEventDO.builder().id(1L).eventNo("EVT-MAIN").projectId(10L)
                .status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(2).slaStartTime(mainStart)
                .responseDeadline(mainStart.plusMinutes(30)).recoveryDeadline(mainStart.plusHours(4))
                .closeDeadline(mainStart.plusHours(12)).build();
        PropertyEventDO related = PropertyEventDO.builder().id(2L).eventNo("EVT-DUP").projectId(10L)
                .status(PropertyEventStatusEnum.PENDING_DISPATCH.getStatus()).version(1)
                .slaStartTime(relatedStart).build();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(main);
        when(eventMapper.selectByIdAndProjectId(2L, 10L)).thenReturn(related);
        when(eventMapper.updateById(any(PropertyEventDO.class))).thenReturn(1);
        when(workOrderMapper.selectListByEventId(2L)).thenReturn(List.of());
        PropertyEventMergeReqVO reqVO = new PropertyEventMergeReqVO();
        reqVO.setProjectId(10L);
        reqVO.setMainEventId(1L);
        reqVO.setMainVersion(2);
        reqVO.setRelatedEventId(2L);
        reqVO.setRelatedVersion(1);
        reqVO.setReason("同一楼栋同一管网漏水");

        eventService.mergeEvent(reqVO);

        assertEquals(relatedStart, main.getSlaStartTime());
        assertEquals(mainStart, main.getResponseDeadline());
        assertEquals(PropertyEventStatusEnum.MERGED.getStatus(), related.getStatus());
        assertEquals(1L, related.getMainEventId());
        verify(relationMapper).insert(any(PropertyEventRelationDO.class));
        verify(timelineMapper, org.mockito.Mockito.times(2)).insert(any(PropertyEventTimelineDO.class));
    }

    @Test
    void shouldSplitWithIndependentSlaAndInheritedEvidence() {
        PropertyEventDO source = PropertyEventDO.builder().id(1L).eventNo("EVT-SOURCE").projectId(10L)
                .communityId(20L).spaceId(30L).assetId(40L).sourceType(1)
                .status(PropertyEventStatusEnum.PROCESSING.getStatus()).version(2).build();
        PropertyEventEvidenceDO evidence = PropertyEventEvidenceDO.builder().evidenceType(1)
                .fileUrl("https://example.test/before.jpg").description("处置前")
                .submittedTime(LocalDateTime.now()).verified(true).build();
        LocalDateTime now = LocalDateTime.now();
        when(eventMapper.selectByIdAndProjectId(1L, 10L)).thenReturn(source);
        when(categoryMapper.selectByProjectAndCode(10L, "PUBLIC_FACILITY"))
                .thenReturn(PropertyEventCategoryDO.builder().code("PUBLIC_FACILITY").build());
        when(slaService.calculateSnapshot(any(), any(), any(), any())).thenReturn(
                new PropertyEventSlaService.SlaSnapshot(now.plusMinutes(10), now.plusMinutes(60),
                        now.plusHours(4), now.plusHours(12), 5L, 1, 15, 60));
        when(evidenceMapper.selectListForValidation(1L)).thenReturn(List.of(evidence));
        when(eventMapper.updateById(source)).thenReturn(1);
        doAnswer(invocation -> {
            PropertyEventDO child = invocation.getArgument(0);
            child.setId(3L);
            return 1;
        }).when(eventMapper).insert(any(PropertyEventDO.class));
        PropertyEventSplitReqVO reqVO = new PropertyEventSplitReqVO();
        reqVO.setProjectId(10L);
        reqVO.setSourceEventId(1L);
        reqVO.setSourceVersion(2);
        reqVO.setReason("管网与电气需独立责任处理");
        reqVO.setCategoryCode("PUBLIC_FACILITY");
        reqVO.setTitle("电气线路独立排查");
        reqVO.setDescription("拆分电气专业任务");
        reqVO.setUrgencyLevel(3);
        reqVO.setImpactLevel(2);
        reqVO.setSafetyLevel(2);

        assertEquals(3L, eventService.splitEvent(reqVO));

        ArgumentCaptor<PropertyEventDO> childCaptor = ArgumentCaptor.forClass(PropertyEventDO.class);
        verify(eventMapper).insert(childCaptor.capture());
        assertEquals(1L, childCaptor.getValue().getParentEventId());
        assertEquals(5L, childCaptor.getValue().getSlaRuleId());
        verify(evidenceMapper).insert(any(PropertyEventEvidenceDO.class));
        verify(relationMapper).insert(any(PropertyEventRelationDO.class));
    }

    private PropertyEventCreateReqVO createReqVO() {
        PropertyEventCreateReqVO reqVO = new PropertyEventCreateReqVO();
        reqVO.setProjectId(10L);
        reqVO.setSourceType(1);
        reqVO.setCategoryCode("CUSTOMER_REPAIR");
        reqVO.setTitle("地下车库漏水");
        reqVO.setDescription("负一层东侧出现持续漏水");
        reqVO.setUrgencyLevel(3);
        reqVO.setImpactLevel(3);
        reqVO.setSafetyLevel(2);
        reqVO.setConfidence(new BigDecimal("0.9800"));
        return reqVO;
    }

    private PropertyEventActionReqVO actionReqVO(int version) {
        PropertyEventActionReqVO reqVO = new PropertyEventActionReqVO();
        reqVO.setId(1L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(version);
        return reqVO;
    }
}

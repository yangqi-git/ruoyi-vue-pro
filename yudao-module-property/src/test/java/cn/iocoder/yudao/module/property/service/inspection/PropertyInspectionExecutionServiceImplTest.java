package cn.iocoder.yudao.module.property.service.inspection;

import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionIssueActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionTaskResponsibilityReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionRecordSubmitReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionIssueDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionConflictDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionRecordDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionTaskAssignmentLogDO;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionIssueMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionConflictMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPlanMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPointMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionRecordMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionStandardMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionTaskMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionTaskAssignmentLogMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionQualitySampleMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.service.event.PropertyEventService;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyInspectionExecutionServiceImplTest {
    @InjectMocks private PropertyInspectionExecutionServiceImpl service;
    @Mock private PropertyInspectionPlanMapper planMapper;
    @Mock private PropertyInspectionTaskMapper taskMapper;
    @Mock private PropertyInspectionRecordMapper recordMapper;
    @Mock private PropertyInspectionIssueMapper issueMapper;
    @Mock private PropertyInspectionStandardMapper standardMapper;
    @Mock private PropertyInspectionPointMapper pointMapper;
    @Mock private PropertyProjectService projectService;
    @Mock private PropertyEventService eventService;
    @Mock private PropertyEventMapper eventMapper;
    @Mock private PropertyInspectionConflictMapper conflictMapper;
    @Mock private PropertyInspectionTaskAssignmentLogMapper assignmentLogMapper;
    @Mock private PropertyInspectionQualitySampleMapper qualitySampleMapper;

    @Test
    void shouldCreateIssueAndUnifiedEventImmediatelyForMajorAbnormality() {
        PropertyInspectionTaskDO task = PropertyInspectionTaskDO.builder().id(1L).projectId(10L)
                .status(10).version(0).pointIds("20").standardSnapshot("30:2")
                .checkCount(0).abnormalCount(0).build();
        PropertyInspectionPointDO point = PropertyInspectionPointDO.builder().id(20L).projectId(10L)
                .communityId(11L).spaceId(12L).name("1号楼安全出口").riskLevel(3).build();
        PropertyInspectionStandardDO standard = PropertyInspectionStandardDO.builder().id(30L).projectId(10L)
                .standardVersion(2).riskLevel(3).rectificationHours(2).eventCategoryCode("FIRE_SAFETY").build();
        when(taskMapper.selectById(1L)).thenReturn(task);
        when(pointMapper.selectById(20L)).thenReturn(point);
        when(standardMapper.selectById(30L)).thenReturn(standard);
        when(recordMapper.selectListByTaskId(1L)).thenReturn(List.of());
        when(taskMapper.updateById(task)).thenReturn(1);
        when(eventService.createEvent(any(PropertyEventCreateReqVO.class))).thenReturn(77L);
        doAnswer(invocation -> {
            PropertyInspectionRecordDO record = invocation.getArgument(0);
            record.setId(40L);
            return 1;
        }).when(recordMapper).insert(any(PropertyInspectionRecordDO.class));
        doAnswer(invocation -> {
            PropertyInspectionIssueDO issue = invocation.getArgument(0);
            issue.setId(50L);
            return 1;
        }).when(issueMapper).insert(any(PropertyInspectionIssueDO.class));

        PropertyInspectionRecordSubmitReqVO reqVO = abnormalReq();
        assertEquals(40L, service.submitRecord(reqVO));

        assertEquals(1, task.getCheckCount());
        assertEquals(1, task.getAbnormalCount());
        ArgumentCaptor<PropertyInspectionIssueDO> issueCaptor = ArgumentCaptor.forClass(PropertyInspectionIssueDO.class);
        verify(issueMapper).insert(issueCaptor.capture());
        assertNotNull(issueCaptor.getValue().getRectificationDeadline());
        assertEquals(77L, issueCaptor.getValue().getEventId());
        ArgumentCaptor<PropertyEventCreateReqVO> eventCaptor = ArgumentCaptor.forClass(PropertyEventCreateReqVO.class);
        verify(eventService).createEvent(eventCaptor.capture());
        assertEquals(5, eventCaptor.getValue().getSourceType());
        assertEquals("FIRE_SAFETY", eventCaptor.getValue().getCategoryCode());
    }

    @Test
    void shouldReturnFailedReviewToRectification() {
        PropertyInspectionIssueDO issue = PropertyInspectionIssueDO.builder().id(50L).projectId(10L)
                .status(10).rectifierUserId(1L).reviewerUserId(2L).reviewCount(0).version(3).build();
        when(issueMapper.selectById(50L)).thenReturn(issue);
        when(issueMapper.updateById(issue)).thenReturn(1);
        PropertyInspectionIssueActionReqVO reqVO = new PropertyInspectionIssueActionReqVO();
        reqVO.setId(50L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(3);
        reqVO.setAction("REVIEW");
        reqVO.setPassed(false);
        reqVO.setReviewResult("出口仍有占用");

        service.executeIssueAction(reqVO);

        assertEquals(0, issue.getStatus());
        assertEquals(1, issue.getReviewCount());
        assertFalse(issue.getSuspectedUnresolved());
    }

    @Test
    void shouldWriteClosedEventResultBackToInspectionIssue() {
        PropertyInspectionIssueDO issue = PropertyInspectionIssueDO.builder()
                .id(50L).eventId(77L).eventStatus(70).build();
        PropertyEventDO event = PropertyEventDO.builder().id(77L).status(80)
                .recoverySummary("通道已清理").rootCause("堆物管理不到位").solution("增加每日巡查").build();
        when(issueMapper.selectLinkedEventList()).thenReturn(List.of(issue));
        when(eventMapper.selectById(77L)).thenReturn(event);

        assertEquals(1, service.syncLinkedEventResults());

        assertEquals(80, issue.getEventStatus());
        assertEquals("通道已清理；堆物管理不到位；增加每日巡查", issue.getEventResultSnapshot());
        verify(issueMapper).updateById(issue);
    }

    @Test
    void shouldQueueOfflineConflictInsteadOfOverwritingChangedTask() {
        PropertyInspectionTaskDO task = PropertyInspectionTaskDO.builder().id(1L).projectId(10L)
                .status(20).version(4).build();
        when(taskMapper.selectById(1L)).thenReturn(task);
        doAnswer(invocation -> {
            PropertyInspectionConflictDO conflict = invocation.getArgument(0);
            conflict.setId(60L);
            return 1;
        }).when(conflictMapper).insert(any(PropertyInspectionConflictDO.class));
        PropertyInspectionRecordSubmitReqVO reqVO = abnormalReq();
        reqVO.setTaskVersion(2);

        var result = service.submitOfflineRecord(reqVO);

        assertFalse(result.getAccepted());
        assertEquals(60L, result.getConflictId());
        assertEquals("SERVER_TASK_CLOSED_OR_CHANGED", result.getConflictReason());
    }

    @Test
    void shouldPreserveOriginalInspectorWhenTransferringTask() {
        PropertyInspectionTaskDO task = PropertyInspectionTaskDO.builder().id(1L).projectId(10L)
                .originalInspectorUserId(7L).inspectorUserId(7L).status(0).version(2).build();
        when(taskMapper.selectById(1L)).thenReturn(task);
        when(taskMapper.updateById(task)).thenReturn(1);
        PropertyInspectionTaskResponsibilityReqVO reqVO = new PropertyInspectionTaskResponsibilityReqVO();
        reqVO.setId(1L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(2);
        reqVO.setAction("TRANSFER");
        reqVO.setTargetUserId(8L);
        reqVO.setReason("原执行人临时请假");

        service.executeTaskResponsibility(reqVO);

        assertEquals(7L, task.getOriginalInspectorUserId());
        assertEquals(8L, task.getInspectorUserId());
        verify(assignmentLogMapper).insert(any(PropertyInspectionTaskAssignmentLogDO.class));
    }

    private PropertyInspectionRecordSubmitReqVO abnormalReq() {
        PropertyInspectionRecordSubmitReqVO reqVO = new PropertyInspectionRecordSubmitReqVO();
        reqVO.setProjectId(10L);
        reqVO.setTaskId(1L);
        reqVO.setTaskVersion(0);
        reqVO.setPointId(20L);
        reqVO.setStandardId(30L);
        reqVO.setSequenceNo(1);
        reqVO.setVerificationMethod("QR");
        reqVO.setVerificationCode("POINT-20");
        reqVO.setResult(10);
        reqVO.setEvidenceUrls("https://example.com/before.jpg");
        reqVO.setRemark("安全出口被杂物占用");
        reqVO.setClientOperationId("offline-op-1");
        reqVO.setTemporaryControl("安排人员现场看守并设置警示");
        reqVO.setRectifierUserId(1L);
        reqVO.setReviewerUserId(2L);
        return reqVO;
    }
}

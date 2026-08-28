package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryActionReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventTimelineMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyServiceRecoveryTaskMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceRecoveryServiceImplTest {

    @InjectMocks
    private PropertyServiceRecoveryServiceImpl service;
    @Mock
    private PropertyServiceRecoveryTaskMapper recoveryMapper;
    @Mock
    private PropertyEventMapper eventMapper;
    @Mock
    private PropertyEventTimelineMapper timelineMapper;
    @Mock
    private PropertyProjectService projectService;

    @Test
    void shouldCreateAutomaticRecoveryTaskOnlyOncePerTrigger() {
        PropertyEventDO event = PropertyEventDO.builder().id(1L).eventNo("EVT-1").projectId(10L)
                .status(50).version(2).build();
        doAnswer(invocation -> {
            PropertyServiceRecoveryTaskDO task = invocation.getArgument(0);
            task.setId(8L);
            return 1;
        }).when(recoveryMapper).insert(any(PropertyServiceRecoveryTaskDO.class));

        assertEquals(8L, service.createAutomaticTask(event, 1, "恢复 SLA 已超时"));
        verify(timelineMapper).insert(any(PropertyEventTimelineDO.class));

        when(recoveryMapper.selectByEventAndTrigger(1L, 1))
                .thenReturn(PropertyServiceRecoveryTaskDO.builder().id(8L).build());
        assertEquals(8L, service.createAutomaticTask(event, 1, "再次扫描"));
        verify(recoveryMapper).insert(any(PropertyServiceRecoveryTaskDO.class));
    }

    @Test
    void shouldCompleteRecoveryWithoutDeletingOriginalRating() {
        PropertyServiceRecoveryTaskDO task = PropertyServiceRecoveryTaskDO.builder().id(8L).projectId(10L)
                .eventId(1L).recoveryNo("SR-1").triggerType(2).originalRating(1)
                .status(20).version(3).build();
        PropertyEventDO event = PropertyEventDO.builder().id(1L).projectId(10L).status(80).version(5).build();
        when(recoveryMapper.selectById(8L)).thenReturn(task);
        when(recoveryMapper.updateById(task)).thenReturn(1);
        when(eventMapper.selectById(1L)).thenReturn(event);
        PropertyServiceRecoveryActionReqVO reqVO = new PropertyServiceRecoveryActionReqVO();
        reqVO.setId(8L);
        reqVO.setProjectId(10L);
        reqVO.setVersion(3);
        reqVO.setAction("COMPLETE");
        reqVO.setRecoveredSatisfaction(5);

        service.executeAction(reqVO);

        assertEquals(30, task.getStatus());
        assertEquals(1, task.getOriginalRating());
        assertEquals(5, task.getRecoveredSatisfaction());
        assertNotNull(task.getCompletedTime());
    }
}

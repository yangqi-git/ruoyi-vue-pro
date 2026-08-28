package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaRuleMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_RULE_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyEventSlaServiceImplTest {

    @InjectMocks
    private PropertyEventSlaServiceImpl slaService;
    @Mock
    private PropertyEventSlaRuleMapper ruleMapper;
    @Mock
    private PropertyProjectService projectService;

    @Test
    void shouldUseExactCategoryRuleAndKeepRuleVersionSnapshot() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 21, 8, 0);
        when(ruleMapper.selectEnabled(1L, "FIRE_SAFETY", 4)).thenReturn(rule(9L, 3, 5, 15, 30, 60));

        PropertyEventSlaService.SlaSnapshot result =
                slaService.calculateSnapshot(1L, "FIRE_SAFETY", 4, start);

        assertEquals(start.plusMinutes(5), result.responseDeadline());
        assertEquals(start.plusMinutes(15), result.arrivalDeadline());
        assertEquals(start.plusMinutes(30), result.recoveryDeadline());
        assertEquals(start.plusMinutes(60), result.closeDeadline());
        assertEquals(9L, result.ruleId());
        assertEquals(3, result.ruleVersion());
    }

    @Test
    void shouldUseProjectWildcardRuleWhenCategoryHasNoRule() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 21, 8, 0);
        when(ruleMapper.selectEnabled(1L, "OTHER", 2)).thenReturn(null);
        when(ruleMapper.selectEnabled(1L, "*", 2)).thenReturn(rule(10L, 1, 20, 60, 180, 360));

        PropertyEventSlaService.SlaSnapshot result = slaService.calculateSnapshot(1L, "OTHER", 2, start);

        assertEquals(start.plusMinutes(20), result.responseDeadline());
        assertEquals(10L, result.ruleId());
    }

    @Test
    void shouldFallbackToSafeDefaultsWhenProjectHasNoRules() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 21, 8, 0);
        PropertyEventSlaService.SlaSnapshot result = slaService.calculateSnapshot(1L, "OTHER", 3, start);

        assertEquals(start.plusMinutes(15), result.responseDeadline());
        assertEquals(start.plusMinutes(60), result.arrivalDeadline());
        assertEquals(start.plusMinutes(240), result.recoveryDeadline());
        assertEquals(start.plusMinutes(720), result.closeDeadline());
        assertNull(result.ruleId());
    }

    @Test
    void shouldRejectNonMonotonicFourStageSla() {
        PropertyEventSlaRuleSaveReqVO reqVO = new PropertyEventSlaRuleSaveReqVO();
        reqVO.setProjectId(1L);
        reqVO.setCategoryCode("OTHER");
        reqVO.setUrgencyLevel(2);
        reqVO.setResponseMinutes(60);
        reqVO.setArrivalMinutes(30);
        reqVO.setRecoveryMinutes(120);
        reqVO.setCloseMinutes(240);
        reqVO.setEscalationMinutes(10);
        reqVO.setStatus(0);
        assertServiceException(() -> slaService.createRule(reqVO), EVENT_SLA_RULE_INVALID);
    }

    private PropertyEventSlaRuleDO rule(Long id, Integer version, int response, int arrival,
            int recovery, int close) {
        return PropertyEventSlaRuleDO.builder()
                .id(id).version(version)
                .responseMinutes(response).arrivalMinutes(arrival)
                .recoveryMinutes(recovery).closeMinutes(close)
                .build();
    }
}

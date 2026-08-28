package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRulePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;

import java.time.LocalDateTime;

public interface PropertyEventSlaService {
    Long createRule(PropertyEventSlaRuleSaveReqVO reqVO);
    void updateRule(PropertyEventSlaRuleSaveReqVO reqVO);
    void deleteRule(Long id, Long projectId);
    PropertyEventSlaRuleDO getRule(Long id, Long projectId);
    PageResult<PropertyEventSlaRuleDO> getRulePage(PropertyEventSlaRulePageReqVO reqVO);
    SlaSnapshot calculateSnapshot(Long projectId, String categoryCode, Integer urgencyLevel, LocalDateTime startTime);

    record SlaSnapshot(LocalDateTime responseDeadline, LocalDateTime arrivalDeadline,
                       LocalDateTime recoveryDeadline, LocalDateTime closeDeadline,
                       Long ruleId, Integer ruleVersion, Integer escalationMinutes,
                       Integer arrivalMinutes) {
    }
}

package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRulePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventSlaRuleMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventCategoryMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CONCURRENT_UPDATE;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_RULE_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_RULE_INVALID;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_SLA_RULE_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CATEGORY_NOT_EXISTS;

@Service
@Validated
public class PropertyEventSlaServiceImpl implements PropertyEventSlaService {
    @Resource
    private PropertyEventSlaRuleMapper ruleMapper;
    @Resource
    private PropertyProjectService projectService;
    @Resource
    private PropertyEventCategoryMapper categoryMapper;

    @Override
    public Long createRule(PropertyEventSlaRuleSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateTimes(reqVO);
        validateCategory(reqVO);
        validateUnique(null, reqVO);
        PropertyEventSlaRuleDO rule = BeanUtils.toBean(reqVO, PropertyEventSlaRuleDO.class);
        rule.setVersion(0);
        ruleMapper.insert(rule);
        return rule.getId();
    }

    @Override
    public void updateRule(PropertyEventSlaRuleSaveReqVO reqVO) {
        PropertyEventSlaRuleDO existing = validateExists(reqVO.getId(), reqVO.getProjectId());
        if (!Objects.equals(existing.getVersion(), reqVO.getVersion())) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
        validateTimes(reqVO);
        validateCategory(reqVO);
        validateUnique(reqVO.getId(), reqVO);
        if (ruleMapper.updateById(BeanUtils.toBean(reqVO, PropertyEventSlaRuleDO.class)) == 0) {
            throw exception(EVENT_CONCURRENT_UPDATE);
        }
    }

    @Override
    public void deleteRule(Long id, Long projectId) {
        validateExists(id, projectId);
        ruleMapper.deleteById(id);
    }

    @Override
    public PropertyEventSlaRuleDO getRule(Long id, Long projectId) {
        return validateExists(id, projectId);
    }

    @Override
    public PageResult<PropertyEventSlaRuleDO> getRulePage(PropertyEventSlaRulePageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return ruleMapper.selectPage(reqVO);
    }

    @Override
    public SlaSnapshot calculateSnapshot(Long projectId, String categoryCode, Integer urgencyLevel,
            LocalDateTime startTime) {
        PropertyEventSlaRuleDO rule = ruleMapper.selectEnabled(projectId, categoryCode, urgencyLevel);
        if (rule == null) {
            rule = ruleMapper.selectEnabled(projectId, "*", urgencyLevel);
        }
        if (rule != null) {
            return new SlaSnapshot(startTime.plusMinutes(rule.getResponseMinutes()),
                    startTime.plusMinutes(rule.getArrivalMinutes()),
                    startTime.plusMinutes(rule.getRecoveryMinutes()),
                    startTime.plusMinutes(rule.getCloseMinutes()), rule.getId(), rule.getVersion(),
                    rule.getEscalationMinutes(), rule.getArrivalMinutes());
        }
        return defaultSnapshot(urgencyLevel, startTime);
    }

    private SlaSnapshot defaultSnapshot(int urgency, LocalDateTime startTime) {
        return new SlaSnapshot(
                startTime.plusMinutes(minutes(urgency, 5, 15, 30, 60)),
                startTime.plusMinutes(minutes(urgency, 20, 60, 120, 240)),
                startTime.plusMinutes(minutes(urgency, 60, 240, 480, 1440)),
                startTime.plusMinutes(minutes(urgency, 240, 720, 1440, 2880)), null, null,
                (int) minutes(urgency, 5, 15, 30, 60),
                (int) minutes(urgency, 20, 60, 120, 240));
    }

    private long minutes(int urgency, long critical, long urgent, long normal, long low) {
        return switch (urgency) {
            case 4 -> critical;
            case 3 -> urgent;
            case 2 -> normal;
            default -> low;
        };
    }

    private void validateTimes(PropertyEventSlaRuleSaveReqVO reqVO) {
        if (reqVO.getResponseMinutes() > reqVO.getArrivalMinutes()
                || reqVO.getArrivalMinutes() > reqVO.getRecoveryMinutes()
                || reqVO.getRecoveryMinutes() > reqVO.getCloseMinutes()) {
            throw exception(EVENT_SLA_RULE_INVALID);
        }
    }

    private PropertyEventSlaRuleDO validateExists(Long id, Long projectId) {
        PropertyEventSlaRuleDO rule = ruleMapper.selectById(id);
        if (rule == null || !Objects.equals(rule.getProjectId(), projectId)) {
            throw exception(EVENT_SLA_RULE_NOT_EXISTS);
        }
        return rule;
    }

    private void validateUnique(Long id, PropertyEventSlaRuleSaveReqVO reqVO) {
        PropertyEventSlaRuleDO rule = ruleMapper.selectUnique(reqVO.getProjectId(), reqVO.getCategoryCode(),
                reqVO.getUrgencyLevel());
        if (rule != null && !Objects.equals(rule.getId(), id)) {
            throw exception(EVENT_SLA_RULE_EXISTS);
        }
    }

    private void validateCategory(PropertyEventSlaRuleSaveReqVO reqVO) {
        if (!"*".equals(reqVO.getCategoryCode())
                && categoryMapper.selectByProjectAndCode(reqVO.getProjectId(), reqVO.getCategoryCode()) == null) {
            throw exception(EVENT_CATEGORY_NOT_EXISTS);
        }
    }
}

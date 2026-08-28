package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleDO;
import cn.iocoder.yudao.module.property.dal.mysql.charge.rule.PropertyChargeRuleMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyChargeRuleServiceImpl implements PropertyChargeRuleService {

    @Resource private PropertyChargeRuleMapper ruleMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createRule(PropertyChargeRuleSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && ruleMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.CHARGE_RULE_CODE_EXISTS);
        }
        PropertyChargeRuleDO rule = BeanUtils.toBean(reqVO, PropertyChargeRuleDO.class);
        ruleMapper.insert(rule);
        return rule.getId();
    }

    @Override public void updateRule(PropertyChargeRuleSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateRuleExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyChargeRuleDO existing = ruleMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.CHARGE_RULE_CODE_EXISTS);
            }
        }
        PropertyChargeRuleDO rule = BeanUtils.toBean(reqVO, PropertyChargeRuleDO.class);
        ruleMapper.updateById(rule);
    }

    @Override public void deleteRule(Long id) {
        validateRuleExists(id);
        ruleMapper.deleteById(id);
    }

    @Override public PropertyChargeRuleDO getRule(Long id) {
        return ruleMapper.selectById(id);
    }

    @Override public PageResult<PropertyChargeRuleDO> getRulePage(String name, String code, Long itemId, Long projectId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        return ruleMapper.selectPage(name, code, itemId, projectId, communityId, status, pageNo, pageSize);
    }

    @Override public List<PropertyChargeRuleDO> getRuleList() {
        return ruleMapper.selectList();
    }

    @Override public List<PropertyChargeRuleDO> getRuleListByItemId(Long itemId) {
        return ruleMapper.selectListByItemId(itemId);
    }

    @Override public List<PropertyChargeRuleDO> getRuleListByProjectId(Long projectId) {
        return ruleMapper.selectListByProjectId(projectId);
    }

    @Override public List<PropertyChargeRuleDO> getRuleListByCommunityId(Long communityId) {
        return ruleMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyChargeRuleDO> getRuleListByStatus(Integer status) {
        return ruleMapper.selectListByStatus(status);
    }

    private void validateRuleExists(Long id) {
        if (ruleMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CHARGE_RULE_NOT_EXISTS);
        }
    }
}

package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleDO;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyChargeRuleService {

    Long createRule(@Valid PropertyChargeRuleSaveReqVO reqVO);

    void updateRule(@Valid PropertyChargeRuleSaveReqVO reqVO);

    void deleteRule(Long id);

    PropertyChargeRuleDO getRule(Long id);

    PageResult<PropertyChargeRuleDO> getRulePage(String name, String code, Long itemId, Long projectId, Long communityId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyChargeRuleDO> getRuleList();

    List<PropertyChargeRuleDO> getRuleListByItemId(Long itemId);

    List<PropertyChargeRuleDO> getRuleListByProjectId(Long projectId);

    List<PropertyChargeRuleDO> getRuleListByCommunityId(Long communityId);

    List<PropertyChargeRuleDO> getRuleListByStatus(Integer status);
}
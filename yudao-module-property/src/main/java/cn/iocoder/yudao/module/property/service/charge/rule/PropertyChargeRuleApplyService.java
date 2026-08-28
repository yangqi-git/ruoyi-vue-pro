package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleApplyDO;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleApplySaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyChargeRuleApplyService {

    Long createApply(@Valid PropertyChargeRuleApplySaveReqVO reqVO);

    void updateApply(@Valid PropertyChargeRuleApplySaveReqVO reqVO);

    void deleteApply(Long id);

    PropertyChargeRuleApplyDO getApply(Long id);

    PageResult<PropertyChargeRuleApplyDO> getApplyPage(Long ruleId, Long itemId, Long houseId, Long communityId, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyChargeRuleApplyDO> getApplyList();

    List<PropertyChargeRuleApplyDO> getApplyListByRuleId(Long ruleId);

    List<PropertyChargeRuleApplyDO> getApplyListByItemId(Long itemId);

    List<PropertyChargeRuleApplyDO> getApplyListByHouseId(Long houseId);

    List<PropertyChargeRuleApplyDO> getApplyListByCommunityId(Long communityId);

    List<PropertyChargeRuleApplyDO> getApplyListByStatus(Integer status);
}
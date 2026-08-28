package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleHistoryDO;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleHistorySaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyChargeRuleHistoryService {

    Long createHistory(@Valid PropertyChargeRuleHistorySaveReqVO reqVO);

    void updateHistory(@Valid PropertyChargeRuleHistorySaveReqVO reqVO);

    void deleteHistory(Long id);

    PropertyChargeRuleHistoryDO getHistory(Long id);

    PageResult<PropertyChargeRuleHistoryDO> getHistoryPage(Long ruleId, Integer version, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyChargeRuleHistoryDO> getHistoryList();

    List<PropertyChargeRuleHistoryDO> getHistoryListByRuleId(Long ruleId);

    List<PropertyChargeRuleHistoryDO> getHistoryListByStatus(Integer status);
}
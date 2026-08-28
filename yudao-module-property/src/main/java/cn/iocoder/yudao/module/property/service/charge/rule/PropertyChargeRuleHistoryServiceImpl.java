package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleHistorySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleHistoryDO;
import cn.iocoder.yudao.module.property.dal.mysql.charge.rule.PropertyChargeRuleHistoryMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyChargeRuleHistoryServiceImpl implements PropertyChargeRuleHistoryService {

    @Resource private PropertyChargeRuleHistoryMapper historyMapper;

    @Override public Long createHistory(PropertyChargeRuleHistorySaveReqVO reqVO) {
        PropertyChargeRuleHistoryDO history = BeanUtils.toBean(reqVO, PropertyChargeRuleHistoryDO.class);
        historyMapper.insert(history);
        return history.getId();
    }

    @Override public void updateHistory(PropertyChargeRuleHistorySaveReqVO reqVO) {
        validateHistoryExists(reqVO.getId());
        PropertyChargeRuleHistoryDO history = BeanUtils.toBean(reqVO, PropertyChargeRuleHistoryDO.class);
        historyMapper.updateById(history);
    }

    @Override public void deleteHistory(Long id) {
        validateHistoryExists(id);
        historyMapper.deleteById(id);
    }

    @Override public PropertyChargeRuleHistoryDO getHistory(Long id) {
        return historyMapper.selectById(id);
    }

    @Override public PageResult<PropertyChargeRuleHistoryDO> getHistoryPage(Long ruleId, Integer version, Integer status, Integer pageNo, Integer pageSize) {
        return historyMapper.selectPage(ruleId, version, status, pageNo, pageSize);
    }

    @Override public List<PropertyChargeRuleHistoryDO> getHistoryList() {
        return historyMapper.selectList();
    }

    @Override public List<PropertyChargeRuleHistoryDO> getHistoryListByRuleId(Long ruleId) {
        return historyMapper.selectListByRuleId(ruleId);
    }

    @Override public List<PropertyChargeRuleHistoryDO> getHistoryListByStatus(Integer status) {
        return historyMapper.selectListByStatus(status);
    }

    private void validateHistoryExists(Long id) {
        if (historyMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CHARGE_RULE_HISTORY_NOT_EXISTS);
        }
    }
}
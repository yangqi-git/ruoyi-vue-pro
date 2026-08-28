package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeRuleApplySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleApplyDO;
import cn.iocoder.yudao.module.property.dal.mysql.charge.rule.PropertyChargeRuleApplyMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyChargeRuleApplyServiceImpl implements PropertyChargeRuleApplyService {

    @Resource private PropertyChargeRuleApplyMapper applyMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createApply(PropertyChargeRuleApplySaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyChargeRuleApplyDO apply = BeanUtils.toBean(reqVO, PropertyChargeRuleApplyDO.class);
        applyMapper.insert(apply);
        return apply.getId();
    }

    @Override public void updateApply(PropertyChargeRuleApplySaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateApplyExists(reqVO.getId());
        PropertyChargeRuleApplyDO apply = BeanUtils.toBean(reqVO, PropertyChargeRuleApplyDO.class);
        applyMapper.updateById(apply);
    }

    @Override public void deleteApply(Long id) {
        validateApplyExists(id);
        applyMapper.deleteById(id);
    }

    @Override public PropertyChargeRuleApplyDO getApply(Long id) {
        return applyMapper.selectById(id);
    }

    @Override public PageResult<PropertyChargeRuleApplyDO> getApplyPage(Long ruleId, Long itemId, Long houseId, Long communityId, Integer status, Integer pageNo, Integer pageSize) {
        return applyMapper.selectPage(ruleId, itemId, houseId, communityId, status, pageNo, pageSize);
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyList() {
        return applyMapper.selectList();
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyListByRuleId(Long ruleId) {
        return applyMapper.selectListByRuleId(ruleId);
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyListByItemId(Long itemId) {
        return applyMapper.selectListByItemId(itemId);
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyListByHouseId(Long houseId) {
        return applyMapper.selectListByHouseId(houseId);
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyListByCommunityId(Long communityId) {
        return applyMapper.selectListByCommunityId(communityId);
    }

    @Override public List<PropertyChargeRuleApplyDO> getApplyListByStatus(Integer status) {
        return applyMapper.selectListByStatus(status);
    }

    private void validateApplyExists(Long id) {
        if (applyMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CHARGE_RULE_APPLY_NOT_EXISTS);
        }
    }
}

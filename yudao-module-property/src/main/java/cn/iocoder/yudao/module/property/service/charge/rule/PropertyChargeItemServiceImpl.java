package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeItemSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeItemDO;
import cn.iocoder.yudao.module.property.dal.mysql.charge.rule.PropertyChargeItemMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service @Slf4j @Validated
public class PropertyChargeItemServiceImpl implements PropertyChargeItemService {

    @Resource private PropertyChargeItemMapper itemMapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override public Long createItem(PropertyChargeItemSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        if (reqVO.getCode() != null && itemMapper.selectByCode(reqVO.getCode()) != null) {
            throw exception(ErrorCodeConstants.CHARGE_ITEM_CODE_EXISTS);
        }
        PropertyChargeItemDO item = BeanUtils.toBean(reqVO, PropertyChargeItemDO.class);
        itemMapper.insert(item);
        return item.getId();
    }

    @Override public void updateItem(PropertyChargeItemSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateItemExists(reqVO.getId());
        if (reqVO.getCode() != null) {
            PropertyChargeItemDO existing = itemMapper.selectByCode(reqVO.getCode());
            if (existing != null && !existing.getId().equals(reqVO.getId())) {
                throw exception(ErrorCodeConstants.CHARGE_ITEM_CODE_EXISTS);
            }
        }
        PropertyChargeItemDO item = BeanUtils.toBean(reqVO, PropertyChargeItemDO.class);
        itemMapper.updateById(item);
    }

    @Override public void deleteItem(Long id) {
        validateItemExists(id);
        itemMapper.deleteById(id);
    }

    @Override public PropertyChargeItemDO getItem(Long id) {
        return itemMapper.selectById(id);
    }

    @Override public PageResult<PropertyChargeItemDO> getItemPage(String name, String code, Long projectId, Integer itemType, Integer status, Integer pageNo, Integer pageSize) {
        return itemMapper.selectPage(name, code, projectId, itemType, status, pageNo, pageSize);
    }

    @Override public List<PropertyChargeItemDO> getItemList() {
        return itemMapper.selectList();
    }

    @Override public List<PropertyChargeItemDO> getItemListByProjectId(Long projectId) {
        return itemMapper.selectListByProjectId(projectId);
    }

    @Override public List<PropertyChargeItemDO> getItemListByStatus(Integer status) {
        return itemMapper.selectListByStatus(status);
    }

    private void validateItemExists(Long id) {
        if (itemMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CHARGE_ITEM_NOT_EXISTS);
        }
    }
}

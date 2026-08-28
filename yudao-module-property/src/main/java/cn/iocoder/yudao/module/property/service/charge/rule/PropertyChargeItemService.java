package cn.iocoder.yudao.module.property.service.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeItemDO;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.PropertyChargeItemSaveReqVO;
import jakarta.validation.Valid;
import java.util.List;

public interface PropertyChargeItemService {

    Long createItem(@Valid PropertyChargeItemSaveReqVO reqVO);

    void updateItem(@Valid PropertyChargeItemSaveReqVO reqVO);

    void deleteItem(Long id);

    PropertyChargeItemDO getItem(Long id);

    PageResult<PropertyChargeItemDO> getItemPage(String name, String code, Long projectId, Integer itemType, Integer status, Integer pageNo, Integer pageSize);

    List<PropertyChargeItemDO> getItemList();

    List<PropertyChargeItemDO> getItemListByProjectId(Long projectId);

    List<PropertyChargeItemDO> getItemListByStatus(Integer status);
}
package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategoryPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategorySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;

import java.util.List;

public interface PropertyEventCategoryService {
    Long createCategory(PropertyEventCategorySaveReqVO reqVO);
    void updateCategory(PropertyEventCategorySaveReqVO reqVO);
    void deleteCategory(Long id, Long projectId);
    PropertyEventCategoryDO getCategory(Long id, Long projectId);
    PageResult<PropertyEventCategoryDO> getCategoryPage(PropertyEventCategoryPageReqVO reqVO);
    List<PropertyEventCategoryDO> getEnabledCategoryList(Long projectId);
}

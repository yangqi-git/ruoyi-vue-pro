package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategoryPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategorySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventCategoryMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CATEGORY_CODE_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CATEGORY_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.EVENT_CATEGORY_EVIDENCE_INVALID;

@Service
@Validated
public class PropertyEventCategoryServiceImpl implements PropertyEventCategoryService {
    @Resource
    private PropertyEventCategoryMapper categoryMapper;
    @Resource
    private PropertyProjectService projectService;

    @Override
    public Long createCategory(PropertyEventCategorySaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validateEvidenceConfig(reqVO);
        validateCodeUnique(null, reqVO.getProjectId(), reqVO.getCode());
        PropertyEventCategoryDO category = BeanUtils.toBean(reqVO, PropertyEventCategoryDO.class);
        categoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateCategory(PropertyEventCategorySaveReqVO reqVO) {
        validateExists(reqVO.getId(), reqVO.getProjectId());
        validateEvidenceConfig(reqVO);
        validateCodeUnique(reqVO.getId(), reqVO.getProjectId(), reqVO.getCode());
        categoryMapper.updateById(BeanUtils.toBean(reqVO, PropertyEventCategoryDO.class));
    }

    @Override
    public void deleteCategory(Long id, Long projectId) {
        validateExists(id, projectId);
        categoryMapper.deleteById(id);
    }

    @Override
    public PropertyEventCategoryDO getCategory(Long id, Long projectId) {
        return validateExists(id, projectId);
    }

    @Override
    public PageResult<PropertyEventCategoryDO> getCategoryPage(PropertyEventCategoryPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return categoryMapper.selectPage(reqVO);
    }

    @Override
    public List<PropertyEventCategoryDO> getEnabledCategoryList(Long projectId) {
        projectService.validateProject(projectId);
        return categoryMapper.selectEnabledList(projectId);
    }

    private PropertyEventCategoryDO validateExists(Long id, Long projectId) {
        PropertyEventCategoryDO category = categoryMapper.selectById(id);
        if (category == null || !Objects.equals(category.getProjectId(), projectId)) {
            throw exception(EVENT_CATEGORY_NOT_EXISTS);
        }
        return category;
    }

    private void validateCodeUnique(Long id, Long projectId, String code) {
        PropertyEventCategoryDO category = categoryMapper.selectByProjectAndCode(projectId, code);
        if (category != null && !Objects.equals(category.getId(), id)) {
            throw exception(EVENT_CATEGORY_CODE_EXISTS);
        }
    }

    private void validateEvidenceConfig(PropertyEventCategorySaveReqVO reqVO) {
        try {
            List<Integer> evidenceTypes = StrUtil.split(reqVO.getRequiredEvidenceTypes(), ',').stream()
                    .map(String::trim).filter(StrUtil::isNotBlank).map(Integer::valueOf).toList();
            if (evidenceTypes.isEmpty() || evidenceTypes.stream().anyMatch(type -> type < 1 || type > 7)
                    || Boolean.TRUE.equals(reqVO.getCustomerConfirmationRequired()) && !evidenceTypes.contains(4)) {
                throw exception(EVENT_CATEGORY_EVIDENCE_INVALID);
            }
        } catch (NumberFormatException ex) {
            throw exception(EVENT_CATEGORY_EVIDENCE_INVALID);
        }
    }
}

package cn.iocoder.yudao.module.property.service.inspection;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPointSaveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionResourcePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionStandardSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionPointMapper;
import cn.iocoder.yudao.module.property.dal.mysql.inspection.PropertyInspectionStandardMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventCategoryMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_POINT_CODE_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_POINT_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_MAJOR_EVENT_CATEGORY_REQUIRED;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_STANDARD_NOT_EXISTS;
import static cn.iocoder.yudao.module.property.enums.ErrorCodeConstants.INSPECTION_STANDARD_PUBLISHED;

@Service
public class PropertyInspectionResourceServiceImpl implements PropertyInspectionResourceService {
    @Resource private PropertyInspectionStandardMapper standardMapper;
    @Resource private PropertyInspectionPointMapper pointMapper;
    @Resource private PropertyProjectService projectService;
    @Resource private PropertyEventCategoryMapper eventCategoryMapper;

    @Override
    public Long createStandard(PropertyInspectionStandardSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyInspectionStandardDO latest = standardMapper.selectLatestByCode(
                reqVO.getProjectId(), reqVO.getStandardCode());
        PropertyInspectionStandardDO standard = BeanUtils.toBean(reqVO, PropertyInspectionStandardDO.class);
        standard.setId(null);
        standard.setStandardVersion(latest == null ? 1 : latest.getStandardVersion() + 1);
        standard.setPublished(false);
        standard.setVersion(0);
        standardMapper.insert(standard);
        return standard.getId();
    }

    @Override
    public void updateStandard(PropertyInspectionStandardSaveReqVO reqVO) {
        PropertyInspectionStandardDO standard = validateStandard(reqVO.getId(), reqVO.getProjectId());
        if (Boolean.TRUE.equals(standard.getPublished())) {
            throw exception(INSPECTION_STANDARD_PUBLISHED);
        }
        PropertyInspectionStandardDO update = BeanUtils.toBean(reqVO, PropertyInspectionStandardDO.class);
        update.setStandardVersion(standard.getStandardVersion());
        update.setPublished(false);
        standardMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishStandard(Long id, Long projectId) {
        PropertyInspectionStandardDO standard = validateStandard(id, projectId);
        if (standard.getRiskLevel() >= 3 && (StrUtil.isBlank(standard.getEventCategoryCode())
                || eventCategoryMapper.selectByProjectAndCode(projectId, standard.getEventCategoryCode()) == null)) {
            throw exception(INSPECTION_MAJOR_EVENT_CATEGORY_REQUIRED);
        }
        if (!Boolean.TRUE.equals(standard.getPublished())) {
            standard.setPublished(true);
            standardMapper.updateById(standard);
        }
    }

    @Override
    public PageResult<PropertyInspectionStandardDO> getStandardPage(PropertyInspectionResourcePageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return standardMapper.selectPage(reqVO);
    }

    @Override
    public Long createPoint(PropertyInspectionPointSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        validatePointCode(null, reqVO.getProjectId(), reqVO.getPointCode());
        PropertyInspectionPointDO point = BeanUtils.toBean(reqVO, PropertyInspectionPointDO.class);
        point.setVersion(0);
        pointMapper.insert(point);
        return point.getId();
    }

    @Override
    public void updatePoint(PropertyInspectionPointSaveReqVO reqVO) {
        validatePoint(reqVO.getId(), reqVO.getProjectId());
        validatePointCode(reqVO.getId(), reqVO.getProjectId(), reqVO.getPointCode());
        pointMapper.updateById(BeanUtils.toBean(reqVO, PropertyInspectionPointDO.class));
    }

    @Override
    public PageResult<PropertyInspectionPointDO> getPointPage(PropertyInspectionResourcePageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        return pointMapper.selectPage(reqVO);
    }

    private PropertyInspectionStandardDO validateStandard(Long id, Long projectId) {
        PropertyInspectionStandardDO standard = standardMapper.selectById(id);
        if (standard == null || !Objects.equals(standard.getProjectId(), projectId)) {
            throw exception(INSPECTION_STANDARD_NOT_EXISTS);
        }
        return standard;
    }

    private PropertyInspectionPointDO validatePoint(Long id, Long projectId) {
        PropertyInspectionPointDO point = pointMapper.selectById(id);
        if (point == null || !Objects.equals(point.getProjectId(), projectId)) {
            throw exception(INSPECTION_POINT_NOT_EXISTS);
        }
        return point;
    }

    private void validatePointCode(Long id, Long projectId, String code) {
        PropertyInspectionPointDO point = pointMapper.selectByProjectAndCode(projectId, code);
        if (point != null && !Objects.equals(point.getId(), id)) {
            throw exception(INSPECTION_POINT_CODE_EXISTS);
        }
    }
}

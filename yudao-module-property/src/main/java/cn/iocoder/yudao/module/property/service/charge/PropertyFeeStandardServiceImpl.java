package cn.iocoder.yudao.module.property.service.charge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.PropertyFeeStandardDO;
import cn.iocoder.yudao.module.property.dal.mysql.charge.PropertyFeeStandardMapper;
import cn.iocoder.yudao.module.property.enums.ErrorCodeConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.object.BeanUtils.toBean;

@Service @Slf4j @Validated
public class PropertyFeeStandardServiceImpl implements PropertyFeeStandardService {

    @Resource private PropertyFeeStandardMapper mapper;
    @Resource private cn.iocoder.yudao.module.property.service.org.PropertyProjectService projectService;

    @Override @Transactional(rollbackFor = Exception.class)
    public Long createFeeStandard(FeeStandardSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyFeeStandardDO entity = toBean(reqVO, PropertyFeeStandardDO.class);
        entity.setStatus(1);
        mapper.insert(entity);
        return entity.getId();
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void updateFeeStandard(FeeStandardSaveReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PropertyFeeStandardDO exist = validateExists(reqVO.getId());
        PropertyFeeStandardDO entity = toBean(reqVO, PropertyFeeStandardDO.class);
        mapper.updateById(entity);
    }

    @Override @Transactional(rollbackFor = Exception.class)
    public void deleteFeeStandard(Long id) {
        validateExists(id);
        mapper.deleteById(id);
    }

    @Override public PropertyFeeStandardDO getFeeStandard(Long id) {
        return validateExists(id);
    }

    @Override public PageResult<PropertyFeeStandardDO> getFeeStandardPage(FeeStandardPageReqVO pageReqVO) {
        return mapper.selectPage(pageReqVO.getProjectId(), pageReqVO.getHouseType(), pageReqVO.getStatus());
    }

    private PropertyFeeStandardDO validateExists(Long id) {
        PropertyFeeStandardDO entity = mapper.selectById(id);
        if (entity == null) throw exception(ErrorCodeConstants.FEE_STANDARD_NOT_EXISTS);
        return entity;
    }
}

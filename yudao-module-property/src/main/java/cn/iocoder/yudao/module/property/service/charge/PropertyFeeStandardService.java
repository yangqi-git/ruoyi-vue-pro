package cn.iocoder.yudao.module.property.service.charge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.PropertyFeeStandardDO;
import cn.iocoder.yudao.module.property.controller.admin.charge.vo.*;
import jakarta.validation.Valid;

public interface PropertyFeeStandardService {
    Long createFeeStandard(@Valid FeeStandardSaveReqVO reqVO);
    void updateFeeStandard(@Valid FeeStandardSaveReqVO reqVO);
    void deleteFeeStandard(Long id);
    PropertyFeeStandardDO getFeeStandard(Long id);
    PageResult<PropertyFeeStandardDO> getFeeStandardPage(FeeStandardPageReqVO pageReqVO);
}

package cn.iocoder.yudao.module.property.service.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPointSaveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionResourcePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionStandardSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;

public interface PropertyInspectionResourceService {
    Long createStandard(PropertyInspectionStandardSaveReqVO reqVO);
    void updateStandard(PropertyInspectionStandardSaveReqVO reqVO);
    void publishStandard(Long id, Long projectId);
    PageResult<PropertyInspectionStandardDO> getStandardPage(PropertyInspectionResourcePageReqVO reqVO);
    Long createPoint(PropertyInspectionPointSaveReqVO reqVO);
    void updatePoint(PropertyInspectionPointSaveReqVO reqVO);
    PageResult<PropertyInspectionPointDO> getPointPage(PropertyInspectionResourcePageReqVO reqVO);
}

package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionIssueDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyInspectionIssueMapper extends BaseMapperX<PropertyInspectionIssueDO> {
    default List<PropertyInspectionIssueDO> selectLinkedEventList() {
        return selectList(new LambdaQueryWrapperX<PropertyInspectionIssueDO>()
                .isNotNull(PropertyInspectionIssueDO::getEventId)
                .and(item -> item.isNull(PropertyInspectionIssueDO::getEventStatus)
                        .or().ne(PropertyInspectionIssueDO::getEventStatus, 80))
                .orderByAsc(PropertyInspectionIssueDO::getId).last("LIMIT 200"));
    }

    default PageResult<PropertyInspectionIssueDO> selectPage(PropertyInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyInspectionIssueDO>()
                .eq(PropertyInspectionIssueDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyInspectionIssueDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyInspectionIssueDO::getRiskLevel, reqVO.getRiskLevel())
                .orderByAsc(PropertyInspectionIssueDO::getRectificationDeadline)
                .orderByDesc(PropertyInspectionIssueDO::getId));
    }
}

package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyWorkOrderMapper extends BaseMapperX<PropertyWorkOrderDO> {

    default PageResult<PropertyWorkOrderDO> selectPage(PropertyWorkOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PropertyWorkOrderDO>()
                .eq(PropertyWorkOrderDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(PropertyWorkOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PropertyWorkOrderDO::getAssigneeUserId, reqVO.getAssigneeUserId())
                .likeIfPresent(PropertyWorkOrderDO::getWorkOrderNo, reqVO.getKeyword())
                .orderByDesc(PropertyWorkOrderDO::getId));
    }

    default List<PropertyWorkOrderDO> selectListByEventId(Long eventId) {
        return selectList(new LambdaQueryWrapperX<PropertyWorkOrderDO>()
                .eq(PropertyWorkOrderDO::getEventId, eventId)
                .orderByDesc(PropertyWorkOrderDO::getId));
    }

    default PropertyWorkOrderDO selectLatestByEventId(Long eventId) {
        return selectOne(new LambdaQueryWrapperX<PropertyWorkOrderDO>()
                .eq(PropertyWorkOrderDO::getEventId, eventId)
                .orderByDesc(PropertyWorkOrderDO::getId)
                .last("LIMIT 1"));
    }
}

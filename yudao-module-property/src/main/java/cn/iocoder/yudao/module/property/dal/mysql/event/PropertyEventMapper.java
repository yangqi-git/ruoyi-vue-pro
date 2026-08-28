package cn.iocoder.yudao.module.property.dal.mysql.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface PropertyEventMapper extends BaseMapperX<PropertyEventDO> {

    default List<PropertyEventDO> selectSlaEscalationCandidates() {
        return selectList(new LambdaQueryWrapperX<PropertyEventDO>()
                .notIn(PropertyEventDO::getStatus, 80, 98, 99)
                .orderByAsc(PropertyEventDO::getCloseDeadline)
                .last("LIMIT 500"));
    }

    default PropertyEventDO selectByIdAndProjectId(Long id, Long projectId) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventDO>()
                .eq(PropertyEventDO::getId, id)
                .eq(PropertyEventDO::getProjectId, projectId));
    }

    default PropertyEventDO selectBySource(String sourceSystem, String sourceRecordId) {
        return selectOne(new LambdaQueryWrapperX<PropertyEventDO>()
                .eqIfPresent(PropertyEventDO::getSourceSystem, sourceSystem)
                .eqIfPresent(PropertyEventDO::getSourceRecordId, sourceRecordId));
    }

    default PageResult<PropertyEventDO> selectPage(PropertyEventPageReqVO reqVO) {
        IPage<PropertyEventDO> result = selectPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()),
                new LambdaQueryWrapperX<PropertyEventDO>()
                        .eq(PropertyEventDO::getProjectId, reqVO.getProjectId())
                        .eqIfPresent(PropertyEventDO::getStatus, reqVO.getStatus())
                        .eqIfPresent(PropertyEventDO::getUrgencyLevel, reqVO.getUrgencyLevel())
                        .eqIfPresent(PropertyEventDO::getCategoryCode, reqVO.getCategoryCode())
                        .eqIfPresent(PropertyEventDO::getResponsibleUserId, reqVO.getResponsibleUserId())
                        .and(reqVO.getKeyword() != null && !reqVO.getKeyword().isBlank(), wrapper -> wrapper
                                .like(PropertyEventDO::getEventNo, reqVO.getKeyword())
                                .or().like(PropertyEventDO::getTitle, reqVO.getKeyword()))
                        .orderByAsc(PropertyEventDO::getStatus)
                        .orderByDesc(PropertyEventDO::getUrgencyLevel)
                        .orderByDesc(PropertyEventDO::getId));
        return new PageResult<>(result.getRecords(), result.getTotal());
    }

    default Long selectCountByStatus(Long projectId, Collection<Integer> statuses) {
        return selectCount(new LambdaQueryWrapperX<PropertyEventDO>()
                .eq(PropertyEventDO::getProjectId, projectId)
                .in(PropertyEventDO::getStatus, statuses));
    }

    default Long selectOverdueCount(Long projectId, LocalDateTime now) {
        return selectCount(new LambdaQueryWrapperX<PropertyEventDO>()
                .eq(PropertyEventDO::getProjectId, projectId)
                .lt(PropertyEventDO::getCloseDeadline, now)
                .and(wrapper -> wrapper.isNull(PropertyEventDO::getSlaPaused)
                        .or().eq(PropertyEventDO::getSlaPaused, false))
                .notIn(PropertyEventDO::getStatus, 80, 98, 99));
    }

    default Long selectMajorRiskCount(Long projectId) {
        return selectCount(new LambdaQueryWrapperX<PropertyEventDO>()
                .eq(PropertyEventDO::getProjectId, projectId)
                .and(wrapper -> wrapper.ge(PropertyEventDO::getUrgencyLevel, 4)
                        .or().ge(PropertyEventDO::getSafetyLevel, 3))
                .notIn(PropertyEventDO::getStatus, 80, 98, 99));
    }
}

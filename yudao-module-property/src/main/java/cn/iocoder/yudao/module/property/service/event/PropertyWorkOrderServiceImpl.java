package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderRespVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyEventMapper;
import cn.iocoder.yudao.module.property.dal.mysql.event.PropertyWorkOrderMapper;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PropertyWorkOrderServiceImpl implements PropertyWorkOrderService {
    @Resource
    private PropertyWorkOrderMapper workOrderMapper;
    @Resource
    private PropertyEventMapper eventMapper;
    @Resource
    private PropertyProjectService projectService;

    @Override
    public PageResult<PropertyWorkOrderRespVO> getWorkOrderPage(PropertyWorkOrderPageReqVO reqVO) {
        projectService.validateProject(reqVO.getProjectId());
        PageResult<PropertyWorkOrderDO> page = workOrderMapper.selectPage(reqVO);
        if (page.getList().isEmpty()) {
            return new PageResult<>(Collections.emptyList(), page.getTotal());
        }
        Map<Long, PropertyEventDO> events = eventMapper.selectByIds(page.getList().stream()
                        .map(PropertyWorkOrderDO::getEventId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(PropertyEventDO::getId, Function.identity()));
        return new PageResult<>(page.getList().stream().map(order -> convert(order, events.get(order.getEventId())))
                .toList(), page.getTotal());
    }

    private PropertyWorkOrderRespVO convert(PropertyWorkOrderDO order, PropertyEventDO event) {
        return PropertyWorkOrderRespVO.builder()
                .id(order.getId()).workOrderNo(order.getWorkOrderNo()).eventId(order.getEventId())
                .projectId(order.getProjectId()).assignedDeptId(order.getAssignedDeptId())
                .assigneeUserId(order.getAssigneeUserId()).supplierId(order.getSupplierId())
                .status(order.getStatus()).plannedArrivalTime(order.getPlannedArrivalTime())
                .acceptedTime(order.getAcceptedTime()).arrivedTime(order.getArrivedTime())
                .completedTime(order.getCompletedTime()).rejectReason(order.getRejectReason())
                .transferReason(order.getTransferReason()).createTime(order.getCreateTime())
                .eventNo(event == null ? null : event.getEventNo())
                .eventTitle(event == null ? null : event.getTitle())
                .eventStatus(event == null ? null : event.getStatus())
                .urgencyLevel(event == null ? null : event.getUrgencyLevel())
                .closeDeadline(event == null ? null : event.getCloseDeadline())
                .build();
    }
}

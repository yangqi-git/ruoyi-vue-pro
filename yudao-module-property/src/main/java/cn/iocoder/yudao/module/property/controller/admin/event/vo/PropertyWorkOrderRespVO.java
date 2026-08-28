package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyWorkOrderRespVO {
    private Long id;
    private String workOrderNo;
    private Long eventId;
    private String eventNo;
    private String eventTitle;
    private Long projectId;
    private Long assignedDeptId;
    private Long assigneeUserId;
    private Long supplierId;
    private Integer status;
    private Integer eventStatus;
    private Integer urgencyLevel;
    private LocalDateTime closeDeadline;
    private LocalDateTime plannedArrivalTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime arrivedTime;
    private LocalDateTime completedTime;
    private String rejectReason;
    private String transferReason;
    private LocalDateTime createTime;
}

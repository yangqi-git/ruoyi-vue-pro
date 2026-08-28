package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PropertyEventDispatchReqVO {

    @NotNull(message = "事件不能为空")
    private Long id;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "事件版本不能为空")
    private Integer version;
    private Long assignedDeptId;
    @NotNull(message = "执行人不能为空")
    private Long assigneeUserId;
    private Long supplierId;
    private LocalDateTime plannedArrivalTime;
    private String reason;
}

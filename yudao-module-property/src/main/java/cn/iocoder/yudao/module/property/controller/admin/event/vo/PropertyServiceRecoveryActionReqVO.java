package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PropertyServiceRecoveryActionReqVO {
    @NotNull(message = "恢复任务不能为空")
    private Long id;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "版本不能为空")
    private Integer version;
    @NotBlank(message = "动作不能为空")
    private String action;
    private Long responsibleUserId;
    private String contactResult;
    private String recoveryPlan;
    private LocalDateTime planDueTime;
    private Integer recoveredSatisfaction;
}

package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventSlaRuleSaveReqVO {
    private Long id;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotBlank(message = "事件分类不能为空")
    private String categoryCode;
    @NotNull(message = "紧急程度不能为空")
    @Min(1) @Max(4)
    private Integer urgencyLevel;
    @NotNull @Min(1)
    private Integer responseMinutes;
    @NotNull @Min(1)
    private Integer arrivalMinutes;
    @NotNull @Min(1)
    private Integer recoveryMinutes;
    @NotNull @Min(1)
    private Integer closeMinutes;
    @NotNull @Min(1)
    private Integer escalationMinutes;
    private String allowedPauseReasons;
    @NotNull(message = "状态不能为空")
    private Integer status;
    private Integer version;
}

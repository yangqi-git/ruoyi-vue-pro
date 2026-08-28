package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventSplitReqVO {

    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "来源事件不能为空")
    private Long sourceEventId;
    @NotNull(message = "来源事件版本不能为空")
    private Integer sourceVersion;
    @NotBlank(message = "拆分依据不能为空")
    private String reason;
    @NotBlank(message = "子事件分类不能为空")
    private String categoryCode;
    @NotBlank(message = "子事件标题不能为空")
    private String title;
    @NotBlank(message = "子事件描述不能为空")
    private String description;
    @NotNull @Min(1) @Max(4)
    private Integer urgencyLevel;
    @NotNull @Min(1) @Max(4)
    private Integer impactLevel;
    @NotNull @Min(0) @Max(3)
    private Integer safetyLevel;
}

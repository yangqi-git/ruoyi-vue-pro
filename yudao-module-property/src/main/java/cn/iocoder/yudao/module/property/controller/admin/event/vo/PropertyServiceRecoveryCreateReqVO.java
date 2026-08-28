package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyServiceRecoveryCreateReqVO {
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "事件不能为空")
    private Long eventId;
    @NotNull(message = "原评价不能为空")
    @Min(value = 1, message = "原评价必须为 1 至 2 分")
    @Max(value = 2, message = "原评价必须为 1 至 2 分")
    private Integer originalRating;
    @NotBlank(message = "差评内容不能为空")
    private String triggerDetail;
}

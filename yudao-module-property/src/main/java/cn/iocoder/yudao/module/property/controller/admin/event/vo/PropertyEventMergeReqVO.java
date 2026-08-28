package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventMergeReqVO {

    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "主事件不能为空")
    private Long mainEventId;
    @NotNull(message = "主事件版本不能为空")
    private Integer mainVersion;
    @NotNull(message = "被合并事件不能为空")
    private Long relatedEventId;
    @NotNull(message = "被合并事件版本不能为空")
    private Integer relatedVersion;
    @NotBlank(message = "合并依据不能为空")
    private String reason;
}

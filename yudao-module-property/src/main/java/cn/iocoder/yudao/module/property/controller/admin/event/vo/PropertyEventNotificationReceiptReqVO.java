package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventNotificationReceiptReqVO {
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotBlank(message = "消息编号不能为空")
    private String messageNo;
    @NotNull(message = "送达状态不能为空")
    private Boolean delivered;
    private String externalMessageId;
    private String failureReason;
}

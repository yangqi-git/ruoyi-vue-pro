package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PropertyEventNotificationPageReqVO extends PageParam {
    @NotNull(message = "项目不能为空")
    private Long projectId;
    private Long eventId;
    private Integer status;
    private String notificationType;
}

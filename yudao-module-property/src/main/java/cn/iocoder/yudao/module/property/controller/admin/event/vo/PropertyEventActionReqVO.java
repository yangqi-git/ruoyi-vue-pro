package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventActionReqVO {

    @NotNull(message = "事件不能为空")
    private Long id;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "事件版本不能为空")
    private Integer version;
    private String reason;
    private String detail;
    private Boolean pauseSla;
    private String pauseReasonCode;
    private String recoverySummary;
    private String acceptanceResult;
    private String rootCause;
    private String solution;
}

package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventEvidenceCreateReqVO {

    @NotNull(message = "事件不能为空")
    private Long eventId;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotNull(message = "证据类型不能为空")
    private Integer evidenceType;
    @NotBlank(message = "证据文件不能为空")
    private String fileUrl;
    private String description;
}

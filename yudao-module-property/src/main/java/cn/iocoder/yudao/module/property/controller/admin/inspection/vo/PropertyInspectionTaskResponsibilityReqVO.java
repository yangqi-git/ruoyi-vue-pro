package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionTaskResponsibilityReqVO {
    @NotNull private Long id;
    @NotNull private Long projectId;
    @NotNull private Integer version;
    @NotBlank private String action;
    private Long targetUserId;
    @NotBlank private String reason;
}

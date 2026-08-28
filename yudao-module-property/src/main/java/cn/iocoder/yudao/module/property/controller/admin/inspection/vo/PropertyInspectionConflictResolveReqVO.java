package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionConflictResolveReqVO {
    @NotNull private Long id;
    @NotNull private Long projectId;
    @NotNull private Integer version;
    @NotBlank private String resolution;
    @NotBlank private String resolutionRemark;
}

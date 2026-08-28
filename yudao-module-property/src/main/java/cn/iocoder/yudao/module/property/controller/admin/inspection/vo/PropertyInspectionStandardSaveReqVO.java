package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionStandardSaveReqVO {
    private Long id;
    @NotNull private Long projectId;
    @NotBlank private String standardCode;
    @NotBlank private String name;
    @NotBlank private String specialty;
    @NotBlank private String checkMethod;
    @NotBlank private String passCriteria;
    @NotNull @Min(1) @Max(4) private Integer riskLevel;
    @NotBlank private String evidenceTypes;
    @NotNull @Min(1) private Integer rectificationHours;
    private String sopUrl;
    private String eventCategoryCode;
    @NotNull private Integer status;
    private Integer version;
}

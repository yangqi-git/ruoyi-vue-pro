package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionQualitySampleReqVO {
    private Long id;
    @NotNull private Long projectId;
    @NotNull private Long taskId;
    @NotBlank private String sampleType;
    @NotNull private Long reviewerUserId;
    private Integer version;
    @Min(0) @Max(100) private Integer score;
    private Boolean passed;
    private String findings;
    private String improvementActions;
}

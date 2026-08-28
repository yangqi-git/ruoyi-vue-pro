package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionIssueActionReqVO {
    @NotNull private Long id;
    @NotNull private Long projectId;
    @NotNull private Integer version;
    @NotBlank private String action;
    private String rectificationResult;
    private String afterEvidenceUrls;
    private String reviewResult;
    private Boolean passed;
}

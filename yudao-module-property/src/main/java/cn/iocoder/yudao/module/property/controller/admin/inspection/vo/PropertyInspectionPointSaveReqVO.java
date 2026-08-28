package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionPointSaveReqVO {
    private Long id;
    @NotNull private Long projectId;
    private Long communityId;
    private Long spaceId;
    private Long assetId;
    @NotBlank private String pointCode;
    @NotBlank private String name;
    @NotBlank private String pointType;
    private String qrCode;
    private String nfcCode;
    @NotNull @Min(1) @Max(4) private Integer riskLevel;
    @NotNull private Boolean sequenceRequired;
    @NotNull private Integer status;
    private String disableReason;
    private Integer version;
}

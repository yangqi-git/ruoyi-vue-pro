package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyInspectionRecordSubmitReqVO {
    @NotNull private Long projectId;
    @NotNull private Long taskId;
    @NotNull private Integer taskVersion;
    @NotNull private Long pointId;
    @NotNull private Long standardId;
    @NotNull @Min(1) private Integer sequenceNo;
    @NotBlank private String verificationMethod;
    private String verificationCode;
    @NotNull @Min(0) @Max(20) private Integer result;
    @NotBlank private String evidenceUrls;
    private String remark;
    private String exceptionReasonCode;
    @NotBlank private String clientOperationId;
    private String temporaryControl;
    private Long rectifierUserId;
    private Long reviewerUserId;
}

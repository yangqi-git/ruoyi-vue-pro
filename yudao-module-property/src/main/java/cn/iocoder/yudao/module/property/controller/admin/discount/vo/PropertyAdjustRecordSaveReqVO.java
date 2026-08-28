package cn.iocoder.yudao.module.property.controller.admin.discount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 调价记录创建/更新 Request VO")
@Data
public class PropertyAdjustRecordSaveReqVO {
    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "调价单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "调价单号不能为空")
    private String adjustNo;

    @Schema(description = "账单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "账单ID不能为空")
    private Long billId;

    @Schema(description = "房屋ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房屋ID不能为空")
    private Long houseId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "调价类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "调价类型不能为空")
    private Integer adjustType;

    @Schema(description = "原金额")
    private BigDecimal originalAmount;

    @Schema(description = "调整后金额")
    private BigDecimal adjustedAmount;

    @Schema(description = "调整差额")
    private BigDecimal adjustDifference;

    @Schema(description = "审批状态")
    private Integer approvalStatus;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注")
    private String approvalRemark;

    @Schema(description = "生效时间")
    private LocalDateTime effectiveTime;

    @Schema(description = "备注")
    private String remark;
}

package cn.iocoder.yudao.module.property.controller.admin.settlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 结算批次创建/更新 Request VO")
@Data
public class PropertySettlementBatchSaveReqVO {
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "批次号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "批次号不能为空")
    private String batchNo;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "批次类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "批次类型不能为空")
    private Integer batchType;

    @Schema(description = "开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    private LocalDate endDate;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已结算金额")
    private BigDecimal settledAmount;

    @Schema(description = "批次状态")
    private Integer batchStatus;

    @Schema(description = "结算人ID")
    private Long settlerId;

    @Schema(description = "结算时间")
    private LocalDateTime settleTime;

    @Schema(description = "审批状态")
    private Integer approvalStatus;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "备注")
    private String remark;
}

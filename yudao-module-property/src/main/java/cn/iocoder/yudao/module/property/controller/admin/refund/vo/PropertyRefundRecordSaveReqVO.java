package cn.iocoder.yudao.module.property.controller.admin.refund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "退款单创建/修改 Request VO")
@Data
public class PropertyRefundRecordSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "退款ID") private Long id;
    @Schema(description = "退款编号") private String refundNo;
    @NotNull(message = "收银记录ID不能为空")
    @Schema(description = "收银记录ID") private Long cashierRecordId;
    @NotNull(message = "账单ID不能为空")
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @NotNull(message = "退款类型不能为空")
    @Schema(description = "退款类型") private Integer refundType;
    @NotNull(message = "总金额不能为空")
    @Schema(description = "总金额") private BigDecimal totalAmount;
    @Schema(description = "已退金额") private BigDecimal refundedAmount;
    @NotNull(message = "退款状态不能为空")
    @Schema(description = "退款状态") private Integer refundStatus;
    @NotNull(message = "审批状态不能为空")
    @Schema(description = "审批状态") private Integer approvalStatus;
    @Schema(description = "备注") private String remark;
}

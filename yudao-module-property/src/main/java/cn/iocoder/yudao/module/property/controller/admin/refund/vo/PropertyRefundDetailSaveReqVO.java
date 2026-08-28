package cn.iocoder.yudao.module.property.controller.admin.refund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "退款明细创建/修改 Request VO")
@Data
public class PropertyRefundDetailSaveReqVO {
    @Schema(description = "明细ID") private Long id;
    @NotNull(message = "退款单ID不能为空")
    @Schema(description = "退款单ID") private Long refundId;
    @Schema(description = "账单明细ID") private Long billDetailId;
    @Schema(description = "项目名称") private String itemName;
    @NotNull(message = "金额不能为空")
    @Schema(description = "金额") private BigDecimal amount;
    @Schema(description = "已退金额") private BigDecimal refundedAmount;
    @NotNull(message = "退款状态不能为空")
    @Schema(description = "退款状态") private Integer refundStatus;
    @Schema(description = "备注") private String remark;
}
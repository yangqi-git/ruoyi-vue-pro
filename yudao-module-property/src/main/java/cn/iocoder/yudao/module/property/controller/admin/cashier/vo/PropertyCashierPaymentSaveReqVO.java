package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "支付渠道明细创建/修改 Request VO")
@Data
public class PropertyCashierPaymentSaveReqVO {
    @Schema(description = "支付ID") private Long id;
    @NotNull(message = "收银记录ID不能为空")
    @Schema(description = "收银记录ID") private Long recordId;
    @Schema(description = "支付编号") private String paymentNo;
    @NotNull(message = "支付渠道不能为空")
    @Schema(description = "支付渠道") private String payChannel;
    @NotNull(message = "金额不能为空")
    @Schema(description = "金额") private BigDecimal amount;
    @NotNull(message = "支付状态不能为空")
    @Schema(description = "支付状态") private Integer payStatus;
    @Schema(description = "备注") private String remark;
}
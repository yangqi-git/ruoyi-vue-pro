package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "核销明细创建/修改 Request VO")
@Data
public class PropertyWriteOffDetailSaveReqVO {
    @Schema(description = "核销ID") private Long id;
    @NotNull(message = "收银记录ID不能为空")
    @Schema(description = "收银记录ID") private Long recordId;
    @Schema(description = "支付ID") private Long paymentId;
    @NotNull(message = "账单ID不能为空")
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "账单明细ID") private Long billDetailId;
    @NotNull(message = "金额不能为空")
    @Schema(description = "金额") private BigDecimal amount;
    @NotNull(message = "核销类型不能为空")
    @Schema(description = "核销类型") private Integer writeOffType;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}
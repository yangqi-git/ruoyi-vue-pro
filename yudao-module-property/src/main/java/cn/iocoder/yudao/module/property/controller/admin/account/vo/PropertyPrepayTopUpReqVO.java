package cn.iocoder.yudao.module.property.controller.admin.account.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "预存款充值 Request VO")
@Data
public class PropertyPrepayTopUpReqVO {
    @Schema(description = "账户ID", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull private Long accountId;
    @Schema(description = "充值金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull @DecimalMin(value = "1.00", message = "充值金额不能低于1元")
    @DecimalMax(value = "50000.00", message = "单笔充值不能超过50000元")
    private BigDecimal amount;
    @Schema(description = "支付渠道") @NotBlank(message = "支付渠道不能为空") private String payChannel;
}

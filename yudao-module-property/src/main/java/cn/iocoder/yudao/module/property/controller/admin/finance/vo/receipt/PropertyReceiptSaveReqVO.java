package cn.iocoder.yudao.module.property.controller.admin.finance.vo.receipt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "票据创建 Request VO")
@Data
public class PropertyReceiptSaveReqVO {
    @Schema(description = "票据类型") @NotBlank(message = "票据类型不能为空") private String receiptType;
    @Schema(description = "关联业务类型") private String relatedBizType;
    @Schema(description = "关联业务ID") private Long relatedBizId;
    @Schema(description = "付款人ID") private Long payerId;
    @Schema(description = "付款人名称") private String payerName;
    @Schema(description = "金额") @NotNull(message = "金额不能为空") private BigDecimal amount;
    @Schema(description = "开具人") private String issuer;
    @Schema(description = "备注") @Size(max = 255) private String remark;
}

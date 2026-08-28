package cn.iocoder.yudao.module.property.controller.admin.finance.vo.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "支出记录创建/修改 Request VO")
@Data
public class PropertyExpenseSaveReqVO {
    @Schema(description = "编号") private Long id;
    @Schema(description = "支出类型") @NotBlank(message = "支出类型不能为空") private String expenseType;
    @Schema(description = "支出分类") private String expenseCategory;
    @Schema(description = "金额") @NotNull(message = "金额不能为空") @DecimalMin(value = "0.01", message = "金额必须大于0") private BigDecimal amount;
    @Schema(description = "收款方ID") private Long payeeId;
    @Schema(description = "收款方名称") private String payeeName;
    @Schema(description = "附件URL") private String attachUrls;
    @Schema(description = "备注") @Size(max = 255) private String remark;
}

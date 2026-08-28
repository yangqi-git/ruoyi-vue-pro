package cn.iocoder.yudao.module.property.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "账单明细创建/修改 Request VO")
@Data
public class PropertyBillDetailSaveReqVO {
    @Schema(description = "明细ID") private Long id;
    @NotNull(message = "账单ID不能为空")
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "收费项目ID") private Long itemId;
    @Schema(description = "规则ID") private Long ruleId;
    @Schema(description = "项目名称") private String itemName;
    @NotNull(message = "收费类型不能为空")
    @Schema(description = "收费类型") private Integer chargeType;
    @NotNull(message = "计算类型不能为空")
    @Schema(description = "计算类型") private Integer calcType;
    @NotNull(message = "价格不能为空")
    @Schema(description = "价格") private BigDecimal price;
    @NotNull(message = "数量不能为空")
    @Schema(description = "数量") private BigDecimal quantity;
    @NotNull(message = "金额不能为空")
    @Schema(description = "金额") private BigDecimal amount;
    @Schema(description = "已付金额") private BigDecimal paidAmount;
    @NotNull(message = "支付状态不能为空")
    @Schema(description = "支付状态") private Integer payStatus;
    @Schema(description = "备注") private String remark;
}
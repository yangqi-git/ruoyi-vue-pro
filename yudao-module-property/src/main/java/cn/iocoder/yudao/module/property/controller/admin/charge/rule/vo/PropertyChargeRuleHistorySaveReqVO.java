package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "规则历史创建/修改 Request VO")
@Data
public class PropertyChargeRuleHistorySaveReqVO {
    @Schema(description = "历史ID") private Long id;
    @NotNull(message = "规则ID不能为空")
    @Schema(description = "规则ID") private Long ruleId;
    @NotNull(message = "版本号不能为空")
    @Schema(description = "版本号") private Integer version;
    @NotNull(message = "价格不能为空")
    @Schema(description = "价格") private BigDecimal price;
    @NotNull(message = "计算类型不能为空")
    @Schema(description = "计算类型") private Integer calcType;
    @Schema(description = "单位") private String unit;
    @NotNull(message = "账期周期不能为空")
    @Schema(description = "账期周期") private Integer billingCycle;
    @Schema(description = "生效日期") private LocalDate effectiveDate;
    @Schema(description = "失效日期") private LocalDate expiryDate;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}
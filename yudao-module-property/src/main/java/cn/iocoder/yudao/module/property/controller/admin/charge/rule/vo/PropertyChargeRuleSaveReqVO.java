package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "收费规则创建/修改 Request VO")
@Data
public class PropertyChargeRuleSaveReqVO {
    @Schema(description = "规则ID") private Long id;
    @NotBlank(message = "规则名称不能为空")
    @Schema(description = "规则名称") private String name;
    @Schema(description = "规则编码") private String code;
    @NotNull(message = "收费项目ID不能为空")
    @Schema(description = "收费项目ID") private Long itemId;
    @Schema(description = "物业项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目不能为空") private Long projectId;
    @Schema(description = "小区ID") private Long communityId;
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

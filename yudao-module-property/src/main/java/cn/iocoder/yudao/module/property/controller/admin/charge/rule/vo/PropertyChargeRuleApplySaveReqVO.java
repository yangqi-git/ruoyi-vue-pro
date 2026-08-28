package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Schema(description = "规则应用创建/修改 Request VO")
@Data
public class PropertyChargeRuleApplySaveReqVO {
    @Schema(description = "应用ID") private Long id;
    @NotNull(message = "规则ID不能为空")
    @Schema(description = "规则ID") private Long ruleId;
    @Schema(description = "收费项目ID") private Long itemId;
    @NotNull(message = "房屋ID不能为空")
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目不能为空") private Long projectId;
    @Schema(description = "应用类型") private Integer applyType;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}

package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "收费项目创建/修改 Request VO")
@Data
public class PropertyChargeItemSaveReqVO {
    @Schema(description = "项目ID") private Long id;
    @NotBlank(message = "项目名称不能为空")
    @Schema(description = "项目名称") private String name;
    @Schema(description = "项目编码") private String code;
    @Schema(description = "物业项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目不能为空") private Long projectId;
    @NotNull(message = "项目类型不能为空")
    @Schema(description = "项目类型") private Integer itemType;
    @NotNull(message = "收费类型不能为空")
    @Schema(description = "收费类型") private Integer chargeType;
    @NotNull(message = "计算类型不能为空")
    @Schema(description = "计算类型") private Integer calcType;
    @Schema(description = "单位") private String unit;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}

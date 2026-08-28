package cn.iocoder.yudao.module.property.controller.admin.finance.vo.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "支出审批 Request VO")
@Data
public class PropertyExpenseApproveReqVO {
    @Schema(description = "支出记录ID", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull private Long id;
    @Schema(description = "审批状态(1通过/2驳回)", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull private Integer approvalStatus;
}

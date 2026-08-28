package cn.iocoder.yudao.module.property.controller.admin.discount.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 优惠/调价审批 Request VO")
@Data
public class PropertyApproveReqVO {
    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "记录ID不能为空")
    private Long id;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    @Schema(description = "审批备注")
    private String approvalRemark;
}
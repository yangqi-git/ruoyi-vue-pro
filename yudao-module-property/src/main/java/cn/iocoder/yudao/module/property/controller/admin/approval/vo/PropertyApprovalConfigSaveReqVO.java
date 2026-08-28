package cn.iocoder.yudao.module.property.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 审批阈值配置创建/更新 Request VO")
@Data
public class PropertyApprovalConfigSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "审批类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批类型不能为空")
    private Integer approvalType;

    @Schema(description = "最小金额")
    private BigDecimal minAmount;

    @Schema(description = "最大金额")
    private BigDecimal maxAmount;

    @Schema(description = "审批级别")
    private Integer approvalLevel;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

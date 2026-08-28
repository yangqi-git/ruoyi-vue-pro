package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "收银记录创建/修改 Request VO")
@Data
public class PropertyCashierRecordSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "记录ID") private Long id;
    @Schema(description = "记录编号") private String recordNo;
    @NotNull(message = "账单ID不能为空")
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "住户ID") private Long residentId;
    @NotNull(message = "支付类型不能为空")
    @Schema(description = "支付类型") private Integer payType;
    @NotNull(message = "总金额不能为空")
    @Schema(description = "总金额") private BigDecimal totalAmount;
    @Schema(description = "已付金额") private BigDecimal paidAmount;
    @NotNull(message = "支付状态不能为空")
    @Schema(description = "支付状态") private Integer payStatus;
    @NotNull(message = "记录状态不能为空")
    @Schema(description = "记录状态") private Integer recordStatus;
    @Schema(description = "备注") private String remark;
}

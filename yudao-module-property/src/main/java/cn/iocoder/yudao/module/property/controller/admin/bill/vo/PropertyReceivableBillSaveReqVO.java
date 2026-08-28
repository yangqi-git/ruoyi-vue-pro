package cn.iocoder.yudao.module.property.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "应收账单创建/修改 Request VO")
@Data
public class PropertyReceivableBillSaveReqVO {
    @Schema(description = "账单ID") private Long id;
    @Schema(description = "账单编号") private String billNo;
    @NotNull(message = "房屋ID不能为空")
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目不能为空") private Long projectId;
    @Schema(description = "住户ID") private Long residentId;
    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型") private Integer billType;
    @NotNull(message = "账期不能为空")
    @Schema(description = "账期") private Integer billPeriod;
    @NotNull(message = "账单日期不能为空")
    @Schema(description = "账单日期") private LocalDate billDate;
    @Schema(description = "到期日期") private LocalDate dueDate;
    @NotNull(message = "总金额不能为空")
    @Schema(description = "总金额") private BigDecimal totalAmount;
    @Schema(description = "已付金额") private BigDecimal paidAmount;
    @Schema(description = "欠款金额") private BigDecimal outstandingAmount;
    @NotNull(message = "支付状态不能为空")
    @Schema(description = "支付状态") private Integer payStatus;
    @NotNull(message = "账单状态不能为空")
    @Schema(description = "账单状态") private Integer billStatus;
    @Schema(description = "备注") private String remark;
}

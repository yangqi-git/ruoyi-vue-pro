package cn.iocoder.yudao.module.property.controller.admin.charge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "费用标准创建/修改 Request VO")
@Data
public class FeeStandardSaveReqVO {
    @Schema(description = "编号", example = "1") private Long id;
    @Schema(description = "项目ID", example = "1") @NotNull(message = "项目ID不能为空") private Long projectId;
    @Schema(description = "房屋类型", example = "住宅") @NotBlank(message = "房屋类型不能为空") private String houseType;
    @Schema(description = "费用名称", example = "物业服务费") @NotBlank(message = "费用名称不能为空") @Size(max = 64) private String feeName;
    @Schema(description = "单价", example = "3.5") @NotNull(message = "单价不能为空") @DecimalMin(value = "0.01", message = "单价必须大于0") private BigDecimal unitPrice;
    @Schema(description = "收费周期(月)", example = "1") @NotNull(message = "收费周期不能为空") private Integer chargeCycle;
    @Schema(description = "滞纳金日费率", example = "0.0005") private BigDecimal lateFeeRate;
    @Schema(description = "减免规则JSON") private String reliefConfig;
    @Schema(description = "生效日期") @NotNull(message = "生效日期不能为空") private LocalDate effectiveDate;
    @Schema(description = "失效日期") private LocalDate expireDate;
    @Schema(description = "备注") @Size(max = 255) private String remark;
}

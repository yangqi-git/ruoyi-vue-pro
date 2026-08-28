package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 车位租赁创建/更新 Request VO")
@Data
public class PropertyParkingLeaseSaveReqVO {
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "租赁单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "租赁单号不能为空")
    private String leaseNo;

    @Schema(description = "车位ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车位ID不能为空")
    private Long parkingSpotId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "住户ID")
    private Long residentId;

    @Schema(description = "车辆ID")
    private Long vehicleId;

    @Schema(description = "租赁类型")
    private Integer leaseType;

    @Schema(description = "租金")
    private BigDecimal rentAmount;

    @Schema(description = "押金")
    private BigDecimal depositAmount;

    @Schema(description = "开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    private LocalDate endDate;

    @Schema(description = "租赁状态")
    private Integer leaseStatus;

    @Schema(description = "审批状态")
    private Integer approvalStatus;

    @Schema(description = "备注")
    private String remark;
}

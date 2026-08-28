package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 车辆创建/更新 Request VO")
@Data
public class PropertyVehicleSaveReqVO {
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车牌号不能为空")
    private String plateNo;

    @Schema(description = "车辆类型")
    private String vehicleType;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "颜色")
    private String color;

    @Schema(description = "住户ID")
    private Long residentId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "车辆状态")
    private Integer vehicleStatus;

    @Schema(description = "备注")
    private String remark;
}

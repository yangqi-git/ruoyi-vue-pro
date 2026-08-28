package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 车场创建/更新 Request VO")
@Data
public class PropertyParkingLotSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "车场名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车场名称不能为空")
    private String name;

    @Schema(description = "车场编码")
    private String code;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "车场类型")
    private Integer parkingType;

    @Schema(description = "总车位数")
    private Integer totalSpots;

    @Schema(description = "可用车位数")
    private Integer availableSpots;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}

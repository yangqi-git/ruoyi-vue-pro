package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 车位创建/更新 Request VO")
@Data
public class PropertyParkingSpotSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "ID", example = "1")
    private Long id;

    @Schema(description = "车位编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车位编号不能为空")
    private String spotNo;

    @Schema(description = "车场ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车场ID不能为空")
    private Long parkingLotId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "车位类型")
    private Integer spotType;

    @Schema(description = "车位状态")
    private Integer spotStatus;

    @Schema(description = "面积")
    private BigDecimal area;

    @Schema(description = "楼层号")
    private Integer floorNo;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "备注")
    private String remark;
}

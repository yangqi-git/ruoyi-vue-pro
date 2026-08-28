package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "房屋创建/修改 Request VO")
@Data
public class PropertyHouseSaveReqVO {
    @Schema(description = "房屋ID") private Long id;
    @NotBlank(message = "房屋名称不能为空")
    @Schema(description = "房屋名称") private String name;
    @Schema(description = "房屋编码") private String code;
    @NotNull(message = "楼层ID不能为空")
    @Schema(description = "楼层ID") private Long floorId;
    @Schema(description = "单元ID") private Long unitId;
    @Schema(description = "楼栋ID") private Long buildingId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;
    @Schema(description = "房屋类型") private Integer houseType;
    @Schema(description = "房屋状态") private Integer houseStatus;
    @Schema(description = "建筑面积") private BigDecimal buildArea;
    @Schema(description = "套内面积") private BigDecimal innerArea;
    @Schema(description = "公摊面积") private BigDecimal sharedArea;
    @Schema(description = "卧室数") private Integer bedroomCount;
    @Schema(description = "客厅数") private Integer livingRoomCount;
    @Schema(description = "卫生间数") private Integer bathroomCount;
    @Schema(description = "厨房数") private Integer kitchenCount;
    @Schema(description = "朝向") private String orientation;
    @Schema(description = "建造日期") private LocalDate buildDate;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}

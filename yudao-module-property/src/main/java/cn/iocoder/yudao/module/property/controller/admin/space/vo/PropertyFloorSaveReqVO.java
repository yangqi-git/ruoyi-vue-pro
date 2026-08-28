package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "楼层创建/修改 Request VO")
@Data
public class PropertyFloorSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "楼层ID") private Long id;
    @NotBlank(message = "楼层名称不能为空")
    @Schema(description = "楼层名称") private String name;
    @Schema(description = "楼层编码") private String code;
    @NotNull(message = "单元ID不能为空")
    @Schema(description = "单元ID") private Long unitId;
    @Schema(description = "楼栋ID") private Long buildingId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "楼层号") private Integer floorNo;
    @Schema(description = "房屋数") private Integer houseCount;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}

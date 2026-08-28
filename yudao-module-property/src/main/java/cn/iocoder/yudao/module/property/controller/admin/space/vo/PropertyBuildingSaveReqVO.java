package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "楼栋创建/修改 Request VO")
@Data
public class PropertyBuildingSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "楼栋ID") private Long id;
    @NotBlank(message = "楼栋名称不能为空")
    @Schema(description = "楼栋名称") private String name;
    @Schema(description = "楼栋编码") private String code;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "楼栋类型") private Integer buildingType;
    @Schema(description = "楼层数") private Integer floorCount;
    @Schema(description = "单元数") private Integer unitCount;
    @Schema(description = "房屋数") private Integer houseCount;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "地址") private String address;
    @Schema(description = "建造日期") private LocalDate buildDate;
    @Schema(description = "备注") private String remark;
}

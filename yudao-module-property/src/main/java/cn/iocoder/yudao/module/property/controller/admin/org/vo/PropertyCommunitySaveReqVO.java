package cn.iocoder.yudao.module.property.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "小区创建/修改 Request VO")
@Data
public class PropertyCommunitySaveReqVO {
    @Schema(description = "小区ID") private Long id;
    @NotBlank(message = "小区名称不能为空")
    @Schema(description = "小区名称") private String name;
    @Schema(description = "小区编码") private String code;
    @NotNull(message = "项目ID不能为空")
    @Schema(description = "项目ID") private Long projectId;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "地址") private String address;
    @Schema(description = "总面积") private Double totalArea;
    @Schema(description = "楼栋数") private Integer buildingCount;
    @Schema(description = "单元数") private Integer unitCount;
    @Schema(description = "房屋数") private Integer houseCount;
    @Schema(description = "联系人") private String contactName;
    @Schema(description = "联系电话") private String contactPhone;
    @Schema(description = "开业日期") private LocalDate openDate;
    @Schema(description = "备注") private String remark;
}
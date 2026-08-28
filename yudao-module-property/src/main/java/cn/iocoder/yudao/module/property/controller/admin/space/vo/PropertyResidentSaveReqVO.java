package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "住户创建/修改 Request VO")
@Data
public class PropertyResidentSaveReqVO {
    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @jakarta.validation.constraints.NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "住户ID") private Long id;
    @NotBlank(message = "住户姓名不能为空")
    @Schema(description = "住户姓名") private String name;
    @Schema(description = "身份证号") private String idCard;
    @Schema(description = "联系电话") private String phone;
    @NotNull(message = "房屋ID不能为空")
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @NotNull(message = "住户类型不能为空")
    @Schema(description = "住户类型") private Integer residentType;
    @Schema(description = "性别") private Integer gender;
    @Schema(description = "出生日期") private LocalDate birthDate;
    @Schema(description = "入住日期") private LocalDate moveInDate;
    @Schema(description = "搬出日期") private LocalDate moveOutDate;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "备注") private String remark;
}

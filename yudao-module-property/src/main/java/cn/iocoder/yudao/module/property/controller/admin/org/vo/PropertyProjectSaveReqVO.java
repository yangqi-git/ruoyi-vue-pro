package cn.iocoder.yudao.module.property.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "物业项目创建/修改 Request VO")
@Data
public class PropertyProjectSaveReqVO {
    @Schema(description = "项目ID") private Long id;
    @NotBlank(message = "项目名称不能为空")
    @Schema(description = "项目名称") private String name;
    @Schema(description = "项目编码") private String code;
    @Schema(description = "父项目ID") private Long parentId;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态") private Integer status;
    @Schema(description = "地址") private String address;
    @Schema(description = "联系人") private String contactName;
    @Schema(description = "联系电话") private String contactPhone;
    @Schema(description = "开始日期") private LocalDate startDate;
    @Schema(description = "结束日期") private LocalDate endDate;
    @Schema(description = "备注") private String remark;
}
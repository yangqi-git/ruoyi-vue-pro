package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 物业事件创建 Request VO")
@Data
public class PropertyEventCreateReqVO {

    @NotNull(message = "项目不能为空")
    private Long projectId;
    private Long communityId;
    private Long spaceId;
    private Long houseId;
    private Long assetId;

    @NotNull(message = "事件来源不能为空")
    private Integer sourceType;
    private String sourceSystem;
    private String sourceRecordId;

    @NotBlank(message = "事件分类不能为空")
    private String categoryCode;
    @NotBlank(message = "事件标题不能为空")
    private String title;
    @NotBlank(message = "事件描述不能为空")
    private String description;

    @NotNull(message = "紧急程度不能为空")
    @Min(value = 1, message = "紧急程度不正确")
    @Max(value = 4, message = "紧急程度不正确")
    private Integer urgencyLevel;

    @NotNull(message = "影响程度不能为空")
    @Min(value = 1, message = "影响程度不正确")
    @Max(value = 4, message = "影响程度不正确")
    private Integer impactLevel;

    @NotNull(message = "安全等级不能为空")
    @Min(value = 0, message = "安全等级不正确")
    @Max(value = 3, message = "安全等级不正确")
    private Integer safetyLevel;
    private BigDecimal confidence;
}

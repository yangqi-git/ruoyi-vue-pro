package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyEventCategorySaveReqVO {
    private Long id;
    @NotNull(message = "项目不能为空")
    private Long projectId;
    @NotBlank(message = "分类编码不能为空")
    private String code;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    private Long parentId;
    private String description;
    @NotNull(message = "默认紧急程度不能为空")
    @Min(1) @Max(4)
    private Integer defaultUrgencyLevel;
    @NotNull(message = "重复识别时间窗不能为空")
    @Min(0)
    private Integer duplicateWindowMinutes;
    @NotBlank(message = "关闭证据要求不能为空")
    private String requiredEvidenceTypes;
    @NotNull(message = "客户确认要求不能为空")
    private Boolean customerConfirmationRequired;
    @NotNull(message = "排序不能为空")
    private Integer sort;
    @NotNull(message = "状态不能为空")
    private Integer status;
}

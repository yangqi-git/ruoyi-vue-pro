package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionPageReqVO extends PageParam {
    @NotNull private Long projectId;
    private Integer status;
    private String specialty;
    private Long inspectorUserId;
    private Integer riskLevel;
}

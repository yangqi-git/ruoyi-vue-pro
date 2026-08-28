package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "收费项目分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyChargeItemPageReqVO extends PageParam {
    @Schema(description = "项目名称") private String name;
    @Schema(description = "项目编码") private String code;
    @Schema(description = "物业项目ID") private Long projectId;
    @Schema(description = "项目类型") private Integer itemType;
    @Schema(description = "状态") private Integer status;
}
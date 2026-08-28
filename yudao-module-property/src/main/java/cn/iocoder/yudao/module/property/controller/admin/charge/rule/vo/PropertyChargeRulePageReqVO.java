package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "收费规则分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyChargeRulePageReqVO extends PageParam {
    @Schema(description = "规则名称") private String name;
    @Schema(description = "规则编码") private String code;
    @Schema(description = "收费项目ID") private Long itemId;
    @Schema(description = "物业项目ID") private Long projectId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "状态") private Integer status;
}
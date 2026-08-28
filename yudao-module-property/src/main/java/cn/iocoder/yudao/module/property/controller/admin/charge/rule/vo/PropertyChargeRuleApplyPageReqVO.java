package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "规则应用分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyChargeRuleApplyPageReqVO extends PageParam {
    @Schema(description = "规则ID") private Long ruleId;
    @Schema(description = "收费项目ID") private Long itemId;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "状态") private Integer status;
}
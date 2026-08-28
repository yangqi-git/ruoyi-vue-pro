package cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "规则历史分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyChargeRuleHistoryPageReqVO extends PageParam {
    @Schema(description = "规则ID") private Long ruleId;
    @Schema(description = "版本号") private Integer version;
    @Schema(description = "状态") private Integer status;
}
package cn.iocoder.yudao.module.property.controller.admin.refund.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "退款明细分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyRefundDetailPageReqVO extends PageParam {
    @Schema(description = "退款单ID") private Long refundId;
    @Schema(description = "账单明细ID") private Long billDetailId;
    @Schema(description = "退款状态") private Integer refundStatus;
}
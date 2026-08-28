package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "支付渠道明细分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyCashierPaymentPageReqVO extends PageParam {
    @Schema(description = "收银记录ID") private Long recordId;
    @Schema(description = "支付渠道") private String payChannel;
    @Schema(description = "支付状态") private Integer payStatus;
}
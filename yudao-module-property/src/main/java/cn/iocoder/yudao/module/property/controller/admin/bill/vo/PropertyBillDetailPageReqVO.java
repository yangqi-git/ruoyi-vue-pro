package cn.iocoder.yudao.module.property.controller.admin.bill.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "账单明细分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyBillDetailPageReqVO extends PageParam {
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "收费项目ID") private Long itemId;
    @Schema(description = "支付状态") private Integer payStatus;
}
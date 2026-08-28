package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "核销明细分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyWriteOffDetailPageReqVO extends PageParam {
    @Schema(description = "收银记录ID") private Long recordId;
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "核销类型") private Integer writeOffType;
    @Schema(description = "状态") private Integer status;
}
package cn.iocoder.yudao.module.property.controller.admin.finance.vo.receipt;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "票据分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyReceiptPageReqVO extends PageParam {
    @Schema(description = "票据类型") private String receiptType;
    @Schema(description = "付款人ID") private Long payerId;
    @Schema(description = "开始日期(yyyy-MM-dd)") private String beginDate;
    @Schema(description = "结束日期(yyyy-MM-dd)") private String endDate;
}

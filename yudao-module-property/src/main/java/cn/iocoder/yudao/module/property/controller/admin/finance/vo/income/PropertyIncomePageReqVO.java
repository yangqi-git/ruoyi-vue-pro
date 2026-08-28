package cn.iocoder.yudao.module.property.controller.admin.finance.vo.income;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "收入记录分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyIncomePageReqVO extends PageParam {
    @Schema(description = "付款人ID") private Long payerId;
    @Schema(description = "收入类型") private String incomeType;
    @Schema(description = "支付渠道") private String payChannel;
    @Schema(description = "确认状态") private Integer confirmed;
    @Schema(description = "开始日期(yyyy-MM-dd)") private String beginDate;
    @Schema(description = "结束日期(yyyy-MM-dd)") private String endDate;
}

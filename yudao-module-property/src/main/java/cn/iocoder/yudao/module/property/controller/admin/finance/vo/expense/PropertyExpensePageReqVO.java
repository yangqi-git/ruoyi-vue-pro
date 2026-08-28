package cn.iocoder.yudao.module.property.controller.admin.finance.vo.expense;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "支出记录分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyExpensePageReqVO extends PageParam {
    @Schema(description = "支出类型") private String expenseType;
    @Schema(description = "支出分类") private String expenseCategory;
    @Schema(description = "审批状态") private Integer approvalStatus;
    @Schema(description = "开始日期(yyyy-MM-dd)") private String beginDate;
    @Schema(description = "结束日期(yyyy-MM-dd)") private String endDate;
}

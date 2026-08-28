package cn.iocoder.yudao.module.property.controller.admin.finance.vo.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "支出记录 Response VO")
@Data
public class PropertyExpenseRespVO {
    private Long id;
    private String expenseNo;
    private String expenseType;
    private String expenseCategory;
    private BigDecimal amount;
    private Long payeeId;
    private String payeeName;
    private Integer approvalStatus;
    private String approvedBy;
    private LocalDateTime approvedTime;
    private LocalDate expenseDate;
    private Long submitterId;
    private String submitterName;
    private String attachUrls;
    private String remark;
}

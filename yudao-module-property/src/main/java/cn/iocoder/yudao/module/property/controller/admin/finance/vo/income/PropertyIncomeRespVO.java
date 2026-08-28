package cn.iocoder.yudao.module.property.controller.admin.finance.vo.income;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "收入记录 Response VO")
@Data
public class PropertyIncomeRespVO {
    private Long id;
    private String incomeNo;
    private String incomeType;
    private Long feeRecordId;
    private BigDecimal amount;
    private Long payerId;
    private String payerName;
    private String payChannel;
    private String transactionNo;
    private String invoiceNo;
    private LocalDate incomeDate;
    private Integer confirmed;
    private String confirmedBy;
    private LocalDateTime confirmedTime;
    private String remark;
}

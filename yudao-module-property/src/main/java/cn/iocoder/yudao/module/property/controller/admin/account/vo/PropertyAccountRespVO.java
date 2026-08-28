package cn.iocoder.yudao.module.property.controller.admin.account.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "业主账户 Response VO")
@Data
public class PropertyAccountRespVO {
    private Long id;
    private Long ownerId;
    private String accountNo;
    private BigDecimal balance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalArrears;
    private Integer status;
    private LocalDate openDate;
    private LocalDateTime lastPayDate;
}

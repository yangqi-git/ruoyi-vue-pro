package cn.iocoder.yudao.module.property.controller.admin.account.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "预存款流水 Response VO")
@Data
public class PropertyPrepayRecordRespVO {
    private Long id;
    private String recordNo;
    private Long accountId;
    private Integer recordType;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String relatedBizType;
    private Long relatedBizId;
    private String payChannel;
    private String transactionNo;
    private LocalDateTime transactionTime;
    private String remark;
}

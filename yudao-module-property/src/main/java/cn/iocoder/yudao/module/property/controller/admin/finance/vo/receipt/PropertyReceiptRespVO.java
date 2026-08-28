package cn.iocoder.yudao.module.property.controller.admin.finance.vo.receipt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "票据 Response VO")
@Data
public class PropertyReceiptRespVO {
    private Long id;
    private String receiptNo;
    private String receiptType;
    private String relatedBizType;
    private Long relatedBizId;
    private Long payerId;
    private String payerName;
    private BigDecimal amount;
    private LocalDate issueDate;
    private String issuer;
    private Integer printCount;
    private Integer status;
    private String remark;
}

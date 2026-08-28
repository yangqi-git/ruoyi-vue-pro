package cn.iocoder.yudao.module.property.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_income")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyIncomeDO extends TenantBaseDO {
    @TableId private Long id;
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

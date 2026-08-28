package cn.iocoder.yudao.module.property.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_expense")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyExpenseDO extends TenantBaseDO {
    @TableId private Long id;
    private String expenseNo;
    private String expenseType;
    private String expenseCategory;
    private BigDecimal amount;
    private Long payeeId;
    private String payeeName;
    private Integer approvalStatus;
    private String approvedBy;
    private LocalDateTime approvedTime;
    private String attachUrls;
    private LocalDate expenseDate;
    private Long submitterId;
    private String submitterName;
    private String remark;
}

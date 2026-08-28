package cn.iocoder.yudao.module.property.dal.dataobject.account;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_owner_account")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyOwnerAccountDO extends TenantBaseDO {
    @TableId private Long id;
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

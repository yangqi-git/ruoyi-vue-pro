package cn.iocoder.yudao.module.property.dal.dataobject.cashier;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_cashier_payment")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyCashierPaymentDO extends TenantBaseDO {
    @TableId private Long id;
    private Long recordId;
    private String paymentNo;
    private String payChannel;
    private BigDecimal amount;
    private Integer payStatus;
    private LocalDateTime payTime;
    private String remark;
}
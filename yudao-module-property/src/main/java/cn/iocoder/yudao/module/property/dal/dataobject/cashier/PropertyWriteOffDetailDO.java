package cn.iocoder.yudao.module.property.dal.dataobject.cashier;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_write_off_detail")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyWriteOffDetailDO extends TenantBaseDO {
    @TableId private Long id;
    private Long recordId;
    private Long paymentId;
    private Long billId;
    private Long billDetailId;
    private BigDecimal amount;
    private Integer writeOffType;
    private Integer status;
    private LocalDateTime writeOffTime;
    private String remark;
}
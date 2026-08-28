package cn.iocoder.yudao.module.property.dal.dataobject.refund;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_refund_detail")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyRefundDetailDO extends TenantBaseDO {
    @TableId private Long id;
    private Long refundId;
    private Long billDetailId;
    private String itemName;
    private BigDecimal amount;
    private BigDecimal refundedAmount;
    private Integer refundStatus;
    private LocalDateTime refundTime;
    private String remark;
}
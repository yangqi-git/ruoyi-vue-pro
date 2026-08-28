package cn.iocoder.yudao.module.property.dal.dataobject.refund;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_refund_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyRefundRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String refundNo;
    private Long cashierRecordId;
    private Long billId;
    private Long houseId;
    private Long communityId;
    private Integer refundType;
    private BigDecimal totalAmount;
    private BigDecimal refundedAmount;
    private Integer refundStatus;
    private Integer approvalStatus;
    private LocalDateTime refundTime;
    private String remark;
}

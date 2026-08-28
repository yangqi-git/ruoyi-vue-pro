package cn.iocoder.yudao.module.property.dal.dataobject.settlement;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_settlement_batch")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertySettlementBatchDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String batchNo;
    private Long communityId;
    private Integer batchType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private BigDecimal settledAmount;
    private Integer batchStatus;
    private Long settlerId;
    private LocalDateTime settleTime;
    private Integer approvalStatus;
    private Long approverId;
    private LocalDateTime approvalTime;
    private String remark;
}

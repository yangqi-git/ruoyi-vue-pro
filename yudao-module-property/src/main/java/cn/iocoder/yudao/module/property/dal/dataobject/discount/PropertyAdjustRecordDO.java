package cn.iocoder.yudao.module.property.dal.dataobject.discount;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_adjust_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyAdjustRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private String adjustNo;
    private Long billId;
    private Long houseId;
    private Long communityId;
    private Integer adjustType;
    private BigDecimal originalAmount;
    private BigDecimal adjustedAmount;
    private BigDecimal adjustDifference;
    private Integer approvalStatus;
    private Long approverId;
    private LocalDateTime approvalTime;
    private String approvalRemark;
    private LocalDateTime effectiveTime;
    private String remark;
}
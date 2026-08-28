package cn.iocoder.yudao.module.property.dal.dataobject.discount;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_discount_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyDiscountRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String discountNo;
    private Long billId;
    private Long houseId;
    private Long communityId;
    private Integer discountType;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private Integer approvalStatus;
    private Long approverId;
    private LocalDateTime approvalTime;
    private String approvalRemark;
    private LocalDateTime effectiveTime;
    private String remark;
}

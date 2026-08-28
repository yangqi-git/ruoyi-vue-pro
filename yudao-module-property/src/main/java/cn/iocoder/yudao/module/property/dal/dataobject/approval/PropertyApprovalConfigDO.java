package cn.iocoder.yudao.module.property.dal.dataobject.approval;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;

@TableName("property_approval_config")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyApprovalConfigDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private Long communityId;
    private Integer approvalType;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Integer approvalLevel;
    private Long approverId;
    private Integer status;
    private String remark;
}

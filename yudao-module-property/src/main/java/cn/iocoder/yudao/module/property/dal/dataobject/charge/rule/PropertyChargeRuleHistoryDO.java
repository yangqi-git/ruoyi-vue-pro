package cn.iocoder.yudao.module.property.dal.dataobject.charge.rule;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("property_charge_rule_history")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyChargeRuleHistoryDO extends TenantBaseDO {
    @TableId private Long id;
    private Long ruleId;
    private Integer version;
    private BigDecimal price;
    private Integer calcType;
    private String unit;
    private Integer billingCycle;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private Integer status;
    private String remark;
}
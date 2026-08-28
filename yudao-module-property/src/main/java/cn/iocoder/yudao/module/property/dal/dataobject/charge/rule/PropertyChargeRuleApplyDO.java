package cn.iocoder.yudao.module.property.dal.dataobject.charge.rule;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("property_charge_rule_apply")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyChargeRuleApplyDO extends TenantBaseDO {
    @TableId private Long id;
    private Long ruleId;
    private Long itemId;
    private Long houseId;
    private Long communityId;
    private Long projectId;
    private Integer applyType;
    private Integer status;
    private String remark;
}
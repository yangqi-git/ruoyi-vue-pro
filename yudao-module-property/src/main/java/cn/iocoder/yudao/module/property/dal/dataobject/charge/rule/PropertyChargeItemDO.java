package cn.iocoder.yudao.module.property.dal.dataobject.charge.rule;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("property_charge_item")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyChargeItemDO extends TenantBaseDO {
    @TableId private Long id;
    private String name;
    private String code;
    private Long projectId;
    private Integer itemType;
    private Integer chargeType;
    private Integer calcType;
    private String unit;
    private Integer status;
    private String remark;
}
package cn.iocoder.yudao.module.property.dal.dataobject.bill;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;

@TableName("property_bill_detail")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyBillDetailDO extends TenantBaseDO {
    @TableId private Long id;
    private Long billId;
    private Long itemId;
    private Long ruleId;
    private String itemName;
    private Integer chargeType;
    private Integer calcType;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private Integer payStatus;
    private String remark;
}
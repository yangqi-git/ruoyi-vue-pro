package cn.iocoder.yudao.module.property.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("property_receipt")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyReceiptDO extends TenantBaseDO {
    @TableId private Long id;
    private String receiptNo;
    private String receiptType;
    private String relatedBizType;
    private Long relatedBizId;
    private Long payerId;
    private String payerName;
    private BigDecimal amount;
    private LocalDate issueDate;
    private String issuer;
    private Integer printCount;
    private Integer status;
    private String remark;
}

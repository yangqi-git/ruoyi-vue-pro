package cn.iocoder.yudao.module.property.dal.dataobject.finance;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_prepay_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyPrepayRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private String recordNo;
    private Long accountId;
    private Integer recordType;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String relatedBizType;
    private Long relatedBizId;
    private String payChannel;
    private String transactionNo;
    private LocalDateTime transactionTime;
    private String remark;
}

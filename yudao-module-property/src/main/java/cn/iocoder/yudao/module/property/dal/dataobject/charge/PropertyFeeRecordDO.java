package cn.iocoder.yudao.module.property.dal.dataobject.charge;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_fee_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyFeeRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private String billNo;
    private Long ownerId;
    private Long houseId;
    private Long feeStandardId;
    private String feePeriod;
    private BigDecimal houseArea;
    private BigDecimal baseAmount;
    private BigDecimal adjustAmount;
    private BigDecimal reliefAmount;
    private BigDecimal lateFeeAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Integer payStatus;
    private LocalDate payDeadline;
    private LocalDateTime paidTime;
}

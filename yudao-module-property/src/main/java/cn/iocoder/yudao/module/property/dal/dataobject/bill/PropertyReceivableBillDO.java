package cn.iocoder.yudao.module.property.dal.dataobject.bill;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_receivable_bill")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyReceivableBillDO extends TenantBaseDO {
    @TableId private Long id;
    private String billNo;
    private Long houseId;
    private Long communityId;
    private Long projectId;
    private Long residentId;
    private Integer billType;
    private Integer billPeriod;
    private LocalDate billDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal outstandingAmount;
    private Integer payStatus;
    private Integer billStatus;
    private LocalDateTime payTime;
    private String remark;
}
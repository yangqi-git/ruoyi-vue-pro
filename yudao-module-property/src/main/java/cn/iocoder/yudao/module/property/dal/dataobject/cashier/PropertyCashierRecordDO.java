package cn.iocoder.yudao.module.property.dal.dataobject.cashier;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_cashier_record")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyCashierRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String recordNo;
    private Long billId;
    private Long houseId;
    private Long communityId;
    private Long residentId;
    private Integer payType;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Integer payStatus;
    private Integer recordStatus;
    private LocalDateTime payTime;
    private String remark;
}

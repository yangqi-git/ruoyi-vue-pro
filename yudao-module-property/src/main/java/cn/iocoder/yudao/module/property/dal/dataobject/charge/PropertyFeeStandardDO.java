package cn.iocoder.yudao.module.property.dal.dataobject.charge;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("property_fee_standard")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyFeeStandardDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String houseType;
    private String feeName;
    private BigDecimal unitPrice;
    private Integer chargeCycle;
    private BigDecimal lateFeeRate;
    private String reliefConfig;
    private LocalDate effectiveDate;
    private LocalDate expireDate;
    private String remark;
    private Integer status;
}

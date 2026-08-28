package cn.iocoder.yudao.module.property.dal.dataobject.parking;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("property_parking_lease")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyParkingLeaseDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String leaseNo;
    private Long parkingSpotId;
    private Long communityId;
    private Long residentId;
    private Long vehicleId;
    private Integer leaseType;
    private BigDecimal rentAmount;
    private BigDecimal depositAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer leaseStatus;
    private Integer approvalStatus;
    private String remark;
}

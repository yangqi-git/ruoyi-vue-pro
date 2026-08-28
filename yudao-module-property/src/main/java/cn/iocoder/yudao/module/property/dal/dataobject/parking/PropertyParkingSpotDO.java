package cn.iocoder.yudao.module.property.dal.dataobject.parking;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;

@TableName("property_parking_spot")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyParkingSpotDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String spotNo;
    private Long parkingLotId;
    private Long communityId;
    private Integer spotType;
    private Integer spotStatus;
    private BigDecimal area;
    private Integer floorNo;
    private String location;
    private String remark;
}

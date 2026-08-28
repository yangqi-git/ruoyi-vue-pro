package cn.iocoder.yudao.module.property.dal.dataobject.parking;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

@TableName("property_parking_lot")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyParkingLotDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String name;
    private String code;
    private Long communityId;
    private Integer parkingType;
    private Integer totalSpots;
    private Integer availableSpots;
    private String address;
    private Integer status;
    private String remark;
}

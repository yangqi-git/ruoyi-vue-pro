package cn.iocoder.yudao.module.property.dal.dataobject.parking;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("property_vehicle")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyVehicleDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String plateNo;
    private String vehicleType;
    private String brand;
    private String color;
    private Long residentId;
    private Long communityId;
    private Integer vehicleStatus;
    private String remark;
}

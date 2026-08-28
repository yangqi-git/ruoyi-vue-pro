package cn.iocoder.yudao.module.property.dal.dataobject.space;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

@TableName("property_building")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyBuildingDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String name;
    private String code;
    private Long communityId;
    private Integer buildingType;
    private Integer floorCount;
    private Integer unitCount;
    private Integer houseCount;
    private Integer status;
    private String address;
    private LocalDate buildDate;
    private String remark;
}

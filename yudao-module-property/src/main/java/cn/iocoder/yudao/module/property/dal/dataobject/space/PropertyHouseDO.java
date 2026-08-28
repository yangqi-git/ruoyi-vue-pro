package cn.iocoder.yudao.module.property.dal.dataobject.space;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("property_house")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyHouseDO extends TenantBaseDO {
    @TableId private Long id;
    private String name;
    private String code;
    private Long floorId;
    private Long unitId;
    private Long buildingId;
    private Long communityId;
    private Long projectId;
    private Integer houseType;
    private Integer houseStatus;
    private BigDecimal buildArea;
    private BigDecimal innerArea;
    private BigDecimal sharedArea;
    private Integer bedroomCount;
    private Integer livingRoomCount;
    private Integer bathroomCount;
    private Integer kitchenCount;
    private String orientation;
    private LocalDate buildDate;
    private Integer status;
    private String remark;
}
package cn.iocoder.yudao.module.property.dal.dataobject.space;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("property_unit")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyUnitDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String name;
    private String code;
    private Long buildingId;
    private Long communityId;
    private Integer floorCount;
    private Integer houseCount;
    private Integer status;
    private String remark;
}

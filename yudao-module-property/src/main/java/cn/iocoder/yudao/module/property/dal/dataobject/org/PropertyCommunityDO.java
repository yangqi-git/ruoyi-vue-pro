package cn.iocoder.yudao.module.property.dal.dataobject.org;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

@TableName("property_community")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyCommunityDO extends TenantBaseDO {
    @TableId private Long id;
    private String name;
    private String code;
    private Long projectId;
    private Integer status;
    private String address;
    private Double totalArea;
    private Integer buildingCount;
    private Integer unitCount;
    private Integer houseCount;
    private String contactName;
    private String contactPhone;
    private LocalDate openDate;
    private String remark;
}
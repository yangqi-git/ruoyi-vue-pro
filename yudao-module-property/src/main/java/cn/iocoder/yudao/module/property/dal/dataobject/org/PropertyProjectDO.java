package cn.iocoder.yudao.module.property.dal.dataobject.org;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

@TableName("property_project")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyProjectDO extends TenantBaseDO {
    @TableId private Long id;
    private String name;
    private String code;
    private Long parentId;
    private Integer status;
    private String address;
    private String contactName;
    private String contactPhone;
    private LocalDate startDate;
    private LocalDate endDate;
    private String remark;
}
package cn.iocoder.yudao.module.property.dal.dataobject.space;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.time.LocalDate;

@TableName("property_resident")
@Data @EqualsAndHashCode(callSuper = true)
@Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyResidentDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String name;
    private String idCard;
    private String phone;
    private Long houseId;
    private Long communityId;
    private Integer residentType;
    private Integer gender;
    private LocalDate birthDate;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private Integer status;
    private String remark;
}

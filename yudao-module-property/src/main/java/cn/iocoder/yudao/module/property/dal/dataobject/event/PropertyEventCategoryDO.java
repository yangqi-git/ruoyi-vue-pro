package cn.iocoder.yudao.module.property.dal.dataobject.event;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_event_category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventCategoryDO extends TenantBaseDO {
    @TableId
    private Long id;
    private Long projectId;
    private String code;
    private String name;
    private Long parentId;
    private String description;
    private Integer defaultUrgencyLevel;
    private Integer duplicateWindowMinutes;
    private String requiredEvidenceTypes;
    private Boolean customerConfirmationRequired;
    private Integer sort;
    private Integer status;
}

package cn.iocoder.yudao.module.property.dal.dataobject.inspection;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_inspection_standard")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyInspectionStandardDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String standardCode;
    private String name;
    private String specialty;
    private String checkMethod;
    private String passCriteria;
    private Integer riskLevel;
    private String evidenceTypes;
    private Integer rectificationHours;
    private String sopUrl;
    private String eventCategoryCode;
    private Integer standardVersion;
    private Integer status;
    private Boolean published;
    @Version private Integer version;
}

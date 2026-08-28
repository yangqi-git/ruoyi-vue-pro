package cn.iocoder.yudao.module.property.dal.dataobject.event;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_event_sla_rule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventSlaRuleDO extends TenantBaseDO {
    @TableId
    private Long id;
    private Long projectId;
    private String categoryCode;
    private Integer urgencyLevel;
    private Integer responseMinutes;
    private Integer arrivalMinutes;
    private Integer recoveryMinutes;
    private Integer closeMinutes;
    private Integer escalationMinutes;
    private String allowedPauseReasons;
    private Integer status;
    @Version
    private Integer version;
}

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

import java.time.LocalDateTime;

@TableName("property_service_recovery_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyServiceRecoveryTaskDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String recoveryNo;
    private Long projectId;
    private Long eventId;
    private Integer triggerType;
    private String triggerDetail;
    private Integer originalRating;
    private Integer status;
    private Long responsibleUserId;
    private LocalDateTime contactTime;
    private String contactResult;
    private String recoveryPlan;
    private LocalDateTime planDueTime;
    private LocalDateTime completedTime;
    private Integer recoveredSatisfaction;
    @Version
    private Integer version;
}

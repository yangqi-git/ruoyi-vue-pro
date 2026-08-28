package cn.iocoder.yudao.module.property.dal.dataobject.event;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("property_event_sla_pause")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventSlaPauseDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long eventId;
    private Long projectId;
    private String reasonCode;
    private String reason;
    private LocalDateTime startedTime;
    private LocalDateTime resumedTime;
    private Long durationSeconds;
    private Long pauseUserId;
    private Long resumeUserId;
}

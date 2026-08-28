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

@TableName("property_event_notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventNotificationDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String messageNo;
    private Long projectId;
    private Long eventId;
    private Long residentId;
    private String recipientName;
    private String recipientMobile;
    private String notificationType;
    private String channel;
    private String templateCode;
    private String content;
    private String idempotencyKey;
    private Integer status;
    private Integer retryCount;
    private LocalDateTime nextRetryTime;
    private LocalDateTime sentTime;
    private LocalDateTime deliveredTime;
    private String externalMessageId;
    private String failureReason;
    @Version
    private Integer version;
}

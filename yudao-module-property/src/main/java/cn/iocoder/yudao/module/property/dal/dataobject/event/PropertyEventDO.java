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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("property_event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String eventNo;
    private Long projectId;
    private Long communityId;
    private Long spaceId;
    private Long houseId;
    private Long assetId;
    private Integer sourceType;
    private String sourceSystem;
    private String sourceRecordId;
    private String categoryCode;
    private String title;
    private String description;
    private Integer urgencyLevel;
    private Integer impactLevel;
    private Integer safetyLevel;
    private BigDecimal confidence;
    private Integer status;
    private Long responsibleDeptId;
    private Long responsibleUserId;
    private String collaboratorIds;
    private Long slaRuleId;
    private Integer slaRuleVersion;
    private Integer slaArrivalMinutes;
    private LocalDateTime slaStartTime;
    private LocalDateTime responseDeadline;
    private LocalDateTime arrivalDeadline;
    private LocalDateTime recoveryDeadline;
    private LocalDateTime closeDeadline;
    private LocalDateTime respondedTime;
    private LocalDateTime firstDispatchedTime;
    private LocalDateTime arrivedTime;
    private LocalDateTime recoveredTime;
    private LocalDateTime closedTime;
    private String blockReason;
    private Boolean slaPaused;
    private String slaPauseReasonCode;
    private LocalDateTime slaPauseStartedTime;
    private Long slaPausedSeconds;
    private Integer slaEscalationMinutes;
    private String slaEscalationStage;
    private Integer slaEscalationLevel;
    private LocalDateTime slaEscalatedTime;
    private Long mainEventId;
    private Long parentEventId;
    private String splitReason;
    private String cancelReason;
    private String recoverySummary;
    private String acceptanceResult;
    private String rootCause;
    private String solution;
    private Integer reopenCount;
    @Version
    private Integer version;
}

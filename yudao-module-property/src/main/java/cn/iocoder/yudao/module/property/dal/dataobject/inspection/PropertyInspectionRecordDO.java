package cn.iocoder.yudao.module.property.dal.dataobject.inspection;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName("property_inspection_record")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionRecordDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private Long taskId;
    private Long pointId;
    private Long standardId;
    private Integer standardVersion;
    private Integer sequenceNo;
    private String verificationMethod;
    private String verificationCode;
    private Integer result;
    private String evidenceUrls;
    private String remark;
    private String exceptionReasonCode;
    private String clientOperationId;
    private Long submittedUserId;
    private LocalDateTime submittedTime;
    private Boolean suspicious;
    private String suspiciousReason;
    private Long conflictId;
    private Boolean retainedAfterConflict;
}

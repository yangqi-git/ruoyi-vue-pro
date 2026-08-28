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

import java.time.LocalDateTime;

@TableName("property_inspection_conflict")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionConflictDO extends TenantBaseDO {
    @TableId private Long id;
    private String conflictNo;
    private Long projectId;
    private Long taskId;
    private Integer clientTaskVersion;
    private Integer serverTaskVersion;
    private Integer serverTaskStatus;
    private Long pointId;
    private Long standardId;
    private Integer sequenceNo;
    private String verificationMethod;
    private String verificationCode;
    private Integer result;
    private String evidenceUrls;
    private String remark;
    private String exceptionReasonCode;
    private String clientOperationId;
    private String temporaryControl;
    private Long rectifierUserId;
    private Long reviewerUserId;
    private String conflictReason;
    private Integer status;
    private String resolution;
    private String resolutionRemark;
    private Long replacementTaskId;
    private Long resolverUserId;
    private LocalDateTime resolvedTime;
    @Version private Integer version;
}

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

@TableName("property_inspection_issue")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionIssueDO extends TenantBaseDO {
    @TableId private Long id;
    private String issueNo;
    private Long projectId;
    private Long taskId;
    private Long recordId;
    private Long pointId;
    private Long standardId;
    private Integer riskLevel;
    private String description;
    private String temporaryControl;
    private Long rectifierUserId;
    private Long reviewerUserId;
    private LocalDateTime rectificationDeadline;
    private Integer status;
    private String beforeEvidenceUrls;
    private String rectificationResult;
    private String afterEvidenceUrls;
    private String reviewResult;
    private Integer reviewCount;
    private Boolean suspectedUnresolved;
    private Long eventId;
    private Integer eventStatus;
    private LocalDateTime eventClosedTime;
    private String eventResultSnapshot;
    private LocalDateTime rectifiedTime;
    private LocalDateTime reviewedTime;
    @Version private Integer version;
}

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

@TableName("property_inspection_quality_sample")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionQualitySampleDO extends TenantBaseDO {
    @TableId private Long id;
    private String sampleNo;
    private Long projectId;
    private Long taskId;
    private String sampleType;
    private Long reviewerUserId;
    private Integer recordCount;
    private Integer suspiciousCount;
    private Integer score;
    private Boolean passed;
    private String findings;
    private String improvementActions;
    private Integer status;
    private LocalDateTime completedTime;
    @Version private Integer version;
}

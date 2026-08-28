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

@TableName("property_inspection_task")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionTaskDO extends TenantBaseDO {
    @TableId private Long id;
    private String taskNo;
    private Long projectId;
    private Long planId;
    private String planName;
    private String specialty;
    private String standardSnapshot;
    private String pointIds;
    private LocalDateTime plannedStartTime;
    private LocalDateTime plannedEndTime;
    private Long originalInspectorUserId;
    private Long inspectorUserId;
    private Integer status;
    private LocalDateTime startedTime;
    private LocalDateTime submittedTime;
    private Integer checkCount;
    private Integer abnormalCount;
    private Boolean onTime;
    private String terminateReason;
    @Version private Integer version;
}

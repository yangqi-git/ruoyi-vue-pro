package cn.iocoder.yudao.module.property.dal.dataobject.inspection;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_inspection_task_assignment_log")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionTaskAssignmentLogDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private Long taskId;
    private String action;
    private Long fromUserId;
    private Long toUserId;
    private Integer fromStatus;
    private Integer toStatus;
    private String reason;
    private Long operatorUserId;
    private Integer taskVersion;
}

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

@TableName("property_event_work_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyWorkOrderDO extends TenantBaseDO {

    @TableId
    private Long id;
    private String workOrderNo;
    private Long eventId;
    private Long parentWorkOrderId;
    private Long projectId;
    private Long assignedDeptId;
    private Long assigneeUserId;
    private Long supplierId;
    private Integer status;
    private LocalDateTime plannedArrivalTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime arrivedTime;
    private LocalDateTime completedTime;
    private String rejectReason;
    private String transferReason;
    private String processResult;
    @Version
    private Integer version;
}

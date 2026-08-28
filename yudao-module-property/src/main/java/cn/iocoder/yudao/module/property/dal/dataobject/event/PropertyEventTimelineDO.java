package cn.iocoder.yudao.module.property.dal.dataobject.event;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_event_timeline")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventTimelineDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long eventId;
    private Long projectId;
    private String action;
    private Integer fromStatus;
    private Integer toStatus;
    private Long operatorUserId;
    private String reason;
    private String detail;
    private Integer eventVersion;
}

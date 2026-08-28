package cn.iocoder.yudao.module.property.dal.dataobject.event;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("property_event_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyEventRelationDO extends TenantBaseDO {

    @TableId
    private Long id;
    private Long projectId;
    private Long mainEventId;
    private Long relatedEventId;
    private String relationType;
    private String reason;
    private Long operatorUserId;
}

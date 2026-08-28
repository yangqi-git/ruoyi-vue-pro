package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventEvidenceDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaPauseDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventRelationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventTimelineDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyWorkOrderDO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyEventDetailRespVO {
    private PropertyEventDO event;
    private List<PropertyWorkOrderDO> workOrders;
    private List<PropertyEventEvidenceDO> evidences;
    private List<PropertyEventSlaPauseDO> slaPauses;
    private List<PropertyEventRelationDO> relations;
    private List<PropertyServiceRecoveryTaskDO> recoveryTasks;
    private List<PropertyEventNotificationDO> notifications;
    private List<PropertyEventTimelineDO> timeline;
}

package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyEventStatsRespVO {
    private Long pendingConfirm;
    private Long pendingDispatch;
    private Long pendingAcceptance;
    private Long processing;
    private Long overdue;
    private Long majorRisk;
}

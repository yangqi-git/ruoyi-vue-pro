package cn.iocoder.yudao.module.property.job.event;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.property.service.event.PropertyEventSlaEscalationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PropertyEventSlaEscalationJob implements JobHandler {

    @Resource
    private PropertyEventSlaEscalationService escalationService;

    @Override
    @TenantJob
    public String execute(String param) {
        return StrUtil.format("物业事件 SLA 自动升级 {} 个", escalationService.processEscalations());
    }
}

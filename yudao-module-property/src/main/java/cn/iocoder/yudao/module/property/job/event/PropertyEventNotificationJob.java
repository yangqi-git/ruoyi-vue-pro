package cn.iocoder.yudao.module.property.job.event;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.property.service.event.PropertyEventNotificationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PropertyEventNotificationJob implements JobHandler {

    @Resource
    private PropertyEventNotificationService notificationService;

    @Override
    @TenantJob
    public String execute(String param) {
        return StrUtil.format("物业事件外部消息处理 {} 条", notificationService.processPendingNotifications());
    }
}

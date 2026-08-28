package cn.iocoder.yudao.module.property.job.inspection;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.property.service.inspection.PropertyInspectionExecutionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PropertyInspectionTaskGenerateJob implements JobHandler {
    @Resource private PropertyInspectionExecutionService executionService;

    @Override
    @TenantJob
    public String execute(String param) {
        return StrUtil.format("物业巡检计划生成任务 {} 条，回写关联事件 {} 条",
                executionService.generateDueTasks(), executionService.syncLinkedEventResults());
    }
}

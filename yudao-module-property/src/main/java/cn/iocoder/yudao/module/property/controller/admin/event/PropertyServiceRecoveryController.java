package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryActionReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryCreateReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyServiceRecoveryPageReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyServiceRecoveryTaskDO;
import cn.iocoder.yudao.module.property.service.event.PropertyServiceRecoveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业服务恢复")
@RestController
@RequestMapping("/property/event/recovery")
@Validated
public class PropertyServiceRecoveryController {

    @Resource
    private PropertyServiceRecoveryService recoveryService;

    @GetMapping("/page")
    @Operation(summary = "获得服务恢复任务分页")
    @PreAuthorize("@ss.hasPermission('property:event:recovery:query')")
    public CommonResult<PageResult<PropertyServiceRecoveryTaskDO>> getPage(
            @Valid PropertyServiceRecoveryPageReqVO reqVO) {
        return success(recoveryService.getPage(reqVO));
    }

    @PostMapping("/create-negative-review")
    @Operation(summary = "根据差评创建服务恢复任务")
    @PreAuthorize("@ss.hasPermission('property:event:recovery:create')")
    public CommonResult<Long> createNegativeReviewTask(
            @Valid @RequestBody PropertyServiceRecoveryCreateReqVO reqVO) {
        return success(recoveryService.createNegativeReviewTask(reqVO));
    }

    @PutMapping("/action")
    @Operation(summary = "执行服务恢复任务动作")
    @PreAuthorize("@ss.hasPermission('property:event:recovery:update')")
    public CommonResult<Boolean> executeAction(@Valid @RequestBody PropertyServiceRecoveryActionReqVO reqVO) {
        recoveryService.executeAction(reqVO);
        return success(true);
    }
}

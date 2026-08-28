package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventNotificationReceiptReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventNotificationDO;
import cn.iocoder.yudao.module.property.service.event.PropertyEventNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业事件外部消息")
@RestController
@RequestMapping("/property/event/notification")
@Validated
public class PropertyEventNotificationController {

    @Resource
    private PropertyEventNotificationService notificationService;

    @GetMapping("/page")
    @Operation(summary = "获得事件外部消息分页")
    @PreAuthorize("@ss.hasPermission('property:event:notification:query')")
    public CommonResult<PageResult<PropertyEventNotificationDO>> getPage(
            @Valid PropertyEventNotificationPageReqVO reqVO) {
        return success(notificationService.getPage(reqVO));
    }

    @PutMapping("/retry")
    @Operation(summary = "重试事件外部消息")
    @PreAuthorize("@ss.hasPermission('property:event:notification:retry')")
    public CommonResult<Boolean> retry(@RequestParam("id") @NotNull Long id,
            @RequestParam("projectId") @NotNull Long projectId) {
        notificationService.retry(id, projectId);
        return success(true);
    }

    @PostMapping("/receipt")
    @Operation(summary = "记录渠道送达回执")
    @PreAuthorize("@ss.hasPermission('property:event:notification:receipt')")
    public CommonResult<Boolean> recordReceipt(
            @Valid @RequestBody PropertyEventNotificationReceiptReqVO reqVO) {
        notificationService.recordReceipt(reqVO);
        return success(true);
    }
}

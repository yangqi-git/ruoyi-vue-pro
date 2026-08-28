package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderRespVO;
import cn.iocoder.yudao.module.property.service.event.PropertyWorkOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业事件工单中心")
@RestController
@RequestMapping("/property/event/work-order")
@Validated
public class PropertyWorkOrderController {
    @Resource
    private PropertyWorkOrderService workOrderService;

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('property:event:work-order:query')")
    public CommonResult<PageResult<PropertyWorkOrderRespVO>> page(@Valid PropertyWorkOrderPageReqVO reqVO) {
        return success(workOrderService.getWorkOrderPage(reqVO));
    }
}

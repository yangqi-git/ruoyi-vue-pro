package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRulePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventSlaRuleDO;
import cn.iocoder.yudao.module.property.service.event.PropertyEventSlaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业事件 SLA 规则")
@RestController
@RequestMapping("/property/event/sla-rule")
@Validated
public class PropertyEventSlaRuleController {
    @Resource
    private PropertyEventSlaService slaService;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('property:event:sla:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyEventSlaRuleSaveReqVO reqVO) {
        return success(slaService.createRule(reqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('property:event:sla:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyEventSlaRuleSaveReqVO reqVO) {
        slaService.updateRule(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('property:event:sla:delete')")
    public CommonResult<Boolean> delete(@RequestParam Long id, @RequestParam Long projectId) {
        slaService.deleteRule(id, projectId);
        return success(true);
    }

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('property:event:sla:query')")
    public CommonResult<PropertyEventSlaRuleDO> get(@RequestParam Long id, @RequestParam Long projectId) {
        return success(slaService.getRule(id, projectId));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('property:event:sla:query')")
    public CommonResult<PageResult<PropertyEventSlaRuleDO>> page(@Valid PropertyEventSlaRulePageReqVO reqVO) {
        return success(slaService.getRulePage(reqVO));
    }
}

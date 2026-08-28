package cn.iocoder.yudao.module.property.controller.admin.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleDO;
import cn.iocoder.yudao.module.property.service.charge.rule.PropertyChargeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 收费规则")
@RestController @RequestMapping("/property/charge/rule") @Validated
public class PropertyChargeRuleController {

    @Resource private PropertyChargeRuleService ruleService;

    @PostMapping("/create") @Operation(summary = "创建收费规则")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyChargeRuleSaveReqVO reqVO) {
        return success(ruleService.createRule(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新收费规则")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyChargeRuleSaveReqVO reqVO) {
        ruleService.updateRule(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除收费规则")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        ruleService.deleteRule(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得收费规则")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<PropertyChargeRuleDO> get(@RequestParam("id") Long id) {
        return success(ruleService.getRule(id));
    }

    @GetMapping("/page") @Operation(summary = "收费规则分页")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<PageResult<PropertyChargeRuleDO>> page(@Valid PropertyChargeRulePageReqVO pageReqVO) {
        return success(ruleService.getRulePage(pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getItemId(), pageReqVO.getProjectId(), pageReqVO.getCommunityId(),
                pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "收费规则列表")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<List<PropertyChargeRuleDO>> list() {
        return success(ruleService.getRuleList());
    }

    @GetMapping("/list-by-item") @Operation(summary = "按收费项目获取规则列表")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<List<PropertyChargeRuleDO>> listByItem(@RequestParam("itemId") Long itemId) {
        return success(ruleService.getRuleListByItemId(itemId));
    }

    @GetMapping("/list-by-project") @Operation(summary = "按项目获取规则列表")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<List<PropertyChargeRuleDO>> listByProject(@RequestParam("projectId") Long projectId) {
        return success(ruleService.getRuleListByProjectId(projectId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取规则列表")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<List<PropertyChargeRuleDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(ruleService.getRuleListByCommunityId(communityId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取规则列表")
    @PreAuthorize("@ss.hasPermission('property:charge:rule:query')")
    public CommonResult<List<PropertyChargeRuleDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(ruleService.getRuleListByStatus(status));
    }
}
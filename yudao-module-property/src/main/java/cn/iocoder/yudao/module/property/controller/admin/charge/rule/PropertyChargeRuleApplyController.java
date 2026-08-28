package cn.iocoder.yudao.module.property.controller.admin.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleApplyDO;
import cn.iocoder.yudao.module.property.service.charge.rule.PropertyChargeRuleApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 规则应用")
@RestController @RequestMapping("/property/charge/apply") @Validated
public class PropertyChargeRuleApplyController {

    @Resource private PropertyChargeRuleApplyService applyService;

    @PostMapping("/create") @Operation(summary = "创建规则应用")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyChargeRuleApplySaveReqVO reqVO) {
        return success(applyService.createApply(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新规则应用")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyChargeRuleApplySaveReqVO reqVO) {
        applyService.updateApply(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除规则应用")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        applyService.deleteApply(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得规则应用")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:query')")
    public CommonResult<PropertyChargeRuleApplyDO> get(@RequestParam("id") Long id) {
        return success(applyService.getApply(id));
    }

    @GetMapping("/page") @Operation(summary = "规则应用分页")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:query')")
    public CommonResult<PageResult<PropertyChargeRuleApplyDO>> page(@Valid PropertyChargeRuleApplyPageReqVO pageReqVO) {
        return success(applyService.getApplyPage(pageReqVO.getRuleId(), pageReqVO.getItemId(),
                pageReqVO.getHouseId(), pageReqVO.getCommunityId(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "规则应用列表")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:query')")
    public CommonResult<List<PropertyChargeRuleApplyDO>> list() {
        return success(applyService.getApplyList());
    }

    @GetMapping("/list-by-rule") @Operation(summary = "按规则获取应用列表")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:query')")
    public CommonResult<List<PropertyChargeRuleApplyDO>> listByRule(@RequestParam("ruleId") Long ruleId) {
        return success(applyService.getApplyListByRuleId(ruleId));
    }

    @GetMapping("/list-by-house") @Operation(summary = "按房屋获取应用列表")
    @PreAuthorize("@ss.hasPermission('property:charge:apply:query')")
    public CommonResult<List<PropertyChargeRuleApplyDO>> listByHouse(@RequestParam("houseId") Long houseId) {
        return success(applyService.getApplyListByHouseId(houseId));
    }
}
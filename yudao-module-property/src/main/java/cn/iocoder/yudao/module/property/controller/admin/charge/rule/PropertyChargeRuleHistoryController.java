package cn.iocoder.yudao.module.property.controller.admin.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeRuleHistoryDO;
import cn.iocoder.yudao.module.property.service.charge.rule.PropertyChargeRuleHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 规则历史")
@RestController @RequestMapping("/property/charge/history") @Validated
public class PropertyChargeRuleHistoryController {

    @Resource private PropertyChargeRuleHistoryService historyService;

    @PostMapping("/create") @Operation(summary = "创建规则历史")
    @PreAuthorize("@ss.hasPermission('property:charge:history:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyChargeRuleHistorySaveReqVO reqVO) {
        return success(historyService.createHistory(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新规则历史")
    @PreAuthorize("@ss.hasPermission('property:charge:history:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyChargeRuleHistorySaveReqVO reqVO) {
        historyService.updateHistory(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除规则历史")
    @PreAuthorize("@ss.hasPermission('property:charge:history:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        historyService.deleteHistory(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得规则历史")
    @PreAuthorize("@ss.hasPermission('property:charge:history:query')")
    public CommonResult<PropertyChargeRuleHistoryDO> get(@RequestParam("id") Long id) {
        return success(historyService.getHistory(id));
    }

    @GetMapping("/page") @Operation(summary = "规则历史分页")
    @PreAuthorize("@ss.hasPermission('property:charge:history:query')")
    public CommonResult<PageResult<PropertyChargeRuleHistoryDO>> page(@Valid PropertyChargeRuleHistoryPageReqVO pageReqVO) {
        return success(historyService.getHistoryPage(pageReqVO.getRuleId(), pageReqVO.getVersion(),
                pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "规则历史列表")
    @PreAuthorize("@ss.hasPermission('property:charge:history:query')")
    public CommonResult<List<PropertyChargeRuleHistoryDO>> list() {
        return success(historyService.getHistoryList());
    }

    @GetMapping("/list-by-rule") @Operation(summary = "按规则获取历史列表")
    @PreAuthorize("@ss.hasPermission('property:charge:history:query')")
    public CommonResult<List<PropertyChargeRuleHistoryDO>> listByRule(@RequestParam("ruleId") Long ruleId) {
        return success(historyService.getHistoryListByRuleId(ruleId));
    }
}
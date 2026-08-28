package cn.iocoder.yudao.module.property.controller.admin.approval;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.approval.PropertyApprovalConfigDO;
import cn.iocoder.yudao.module.property.service.approval.PropertyApprovalConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 审批阈值配置")
@RestController @RequestMapping("/property/approval/config") @Validated
public class PropertyApprovalConfigController {

    @Resource private PropertyApprovalConfigService approvalConfigService;

    @PostMapping("/create") @Operation(summary = "创建审批阈值配置")
    @PreAuthorize("@ss.hasPermission('property:approval:config:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyApprovalConfigSaveReqVO reqVO) {
        return success(approvalConfigService.createApprovalConfig(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新审批阈值配置")
    @PreAuthorize("@ss.hasPermission('property:approval:config:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyApprovalConfigSaveReqVO reqVO) {
        approvalConfigService.updateApprovalConfig(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除审批阈值配置")
    @PreAuthorize("@ss.hasPermission('property:approval:config:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        approvalConfigService.deleteApprovalConfig(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得审批阈值配置")
    @PreAuthorize("@ss.hasPermission('property:approval:config:query')")
    public CommonResult<PropertyApprovalConfigDO> get(@RequestParam("id") Long id) {
        return success(approvalConfigService.getApprovalConfig(id));
    }

    @GetMapping("/page") @Operation(summary = "审批阈值配置分页")
    @PreAuthorize("@ss.hasPermission('property:approval:config:query')")
    public CommonResult<PageResult<PropertyApprovalConfigDO>> page(@Valid PropertyApprovalConfigPageReqVO pageReqVO) {
        return success(approvalConfigService.getApprovalConfigPage(pageReqVO.getProjectId(), pageReqVO.getCommunityId(),
                pageReqVO.getApprovalType(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取审批阈值配置列表")
    @PreAuthorize("@ss.hasPermission('property:approval:config:query')")
    public CommonResult<List<PropertyApprovalConfigDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(approvalConfigService.getApprovalConfigListByCommunityId(communityId));
    }

    @GetMapping("/active-list") @Operation(summary = "获取启用的审批阈值配置列表")
    @PreAuthorize("@ss.hasPermission('property:approval:config:query')")
    public CommonResult<List<PropertyApprovalConfigDO>> activeList(@RequestParam("communityId") Long communityId) {
        return success(approvalConfigService.getActiveApprovalConfigList(communityId));
    }
}

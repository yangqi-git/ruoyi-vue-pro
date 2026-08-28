package cn.iocoder.yudao.module.property.controller.admin.org;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyCommunityDO;
import cn.iocoder.yudao.module.property.service.org.PropertyCommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 小区")
@RestController @RequestMapping("/property/org/community") @Validated
public class PropertyCommunityController {

    @Resource private PropertyCommunityService communityService;

    @PostMapping("/create") @Operation(summary = "创建小区")
    @PreAuthorize("@ss.hasPermission('property:org:community:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyCommunitySaveReqVO reqVO) {
        return success(communityService.createCommunity(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新小区")
    @PreAuthorize("@ss.hasPermission('property:org:community:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyCommunitySaveReqVO reqVO) {
        communityService.updateCommunity(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除小区")
    @PreAuthorize("@ss.hasPermission('property:org:community:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        communityService.deleteCommunity(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得小区")
    @PreAuthorize("@ss.hasPermission('property:org:community:query')")
    public CommonResult<PropertyCommunityDO> get(@RequestParam("id") Long id) {
        return success(communityService.getCommunity(id));
    }

    @GetMapping("/page") @Operation(summary = "小区分页")
    @PreAuthorize("@ss.hasPermission('property:org:community:query')")
    public CommonResult<PageResult<PropertyCommunityDO>> page(@Valid PropertyCommunityPageReqVO pageReqVO) {
        return success(communityService.getCommunityPage(pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getProjectId(), pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "小区列表")
    @PreAuthorize("@ss.hasPermission('property:org:community:query')")
    public CommonResult<List<PropertyCommunityDO>> list() {
        return success(communityService.getCommunityList());
    }

    @GetMapping("/list-by-project") @Operation(summary = "按项目获取小区列表")
    @PreAuthorize("@ss.hasPermission('property:org:community:query')")
    public CommonResult<List<PropertyCommunityDO>> listByProject(@RequestParam("projectId") Long projectId) {
        return success(communityService.getCommunityListByProjectId(projectId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取小区列表")
    @PreAuthorize("@ss.hasPermission('property:org:community:query')")
    public CommonResult<List<PropertyCommunityDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(communityService.getCommunityListByStatus(status));
    }
}
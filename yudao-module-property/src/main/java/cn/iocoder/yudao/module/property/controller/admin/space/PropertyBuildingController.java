package cn.iocoder.yudao.module.property.controller.admin.space;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyBuildingDO;
import cn.iocoder.yudao.module.property.service.space.PropertyBuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 楼栋")
@RestController @RequestMapping("/property/space/building") @Validated
public class PropertyBuildingController {

    @Resource private PropertyBuildingService buildingService;

    @PostMapping("/create") @Operation(summary = "创建楼栋")
    @PreAuthorize("@ss.hasPermission('property:space:building:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyBuildingSaveReqVO reqVO) {
        return success(buildingService.createBuilding(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新楼栋")
    @PreAuthorize("@ss.hasPermission('property:space:building:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyBuildingSaveReqVO reqVO) {
        buildingService.updateBuilding(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除楼栋")
    @PreAuthorize("@ss.hasPermission('property:space:building:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        buildingService.deleteBuilding(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得楼栋")
    @PreAuthorize("@ss.hasPermission('property:space:building:query')")
    public CommonResult<PropertyBuildingDO> get(@RequestParam("id") Long id) {
        return success(buildingService.getBuilding(id));
    }

    @GetMapping("/page") @Operation(summary = "楼栋分页")
    @PreAuthorize("@ss.hasPermission('property:space:building:query')")
    public CommonResult<PageResult<PropertyBuildingDO>> page(@Valid PropertyBuildingPageReqVO pageReqVO) {
        return success(buildingService.getBuildingPage(pageReqVO.getProjectId(), pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getCommunityId(), pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "楼栋列表")
    @PreAuthorize("@ss.hasPermission('property:space:building:query')")
    public CommonResult<List<PropertyBuildingDO>> list() {
        return success(buildingService.getBuildingList());
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取楼栋列表")
    @PreAuthorize("@ss.hasPermission('property:space:building:query')")
    public CommonResult<List<PropertyBuildingDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(buildingService.getBuildingListByCommunityId(communityId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取楼栋列表")
    @PreAuthorize("@ss.hasPermission('property:space:building:query')")
    public CommonResult<List<PropertyBuildingDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(buildingService.getBuildingListByStatus(status));
    }
}

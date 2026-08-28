package cn.iocoder.yudao.module.property.controller.admin.space;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyFloorDO;
import cn.iocoder.yudao.module.property.service.space.PropertyFloorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 楼层")
@RestController @RequestMapping("/property/space/floor") @Validated
public class PropertyFloorController {

    @Resource private PropertyFloorService floorService;

    @PostMapping("/create") @Operation(summary = "创建楼层")
    @PreAuthorize("@ss.hasPermission('property:space:floor:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyFloorSaveReqVO reqVO) {
        return success(floorService.createFloor(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新楼层")
    @PreAuthorize("@ss.hasPermission('property:space:floor:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyFloorSaveReqVO reqVO) {
        floorService.updateFloor(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除楼层")
    @PreAuthorize("@ss.hasPermission('property:space:floor:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        floorService.deleteFloor(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得楼层")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<PropertyFloorDO> get(@RequestParam("id") Long id) {
        return success(floorService.getFloor(id));
    }

    @GetMapping("/page") @Operation(summary = "楼层分页")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<PageResult<PropertyFloorDO>> page(@Valid PropertyFloorPageReqVO pageReqVO) {
        return success(floorService.getFloorPage(pageReqVO.getProjectId(), pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getUnitId(), pageReqVO.getBuildingId(), pageReqVO.getCommunityId(),
                pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "楼层列表")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<List<PropertyFloorDO>> list() {
        return success(floorService.getFloorList());
    }

    @GetMapping("/list-by-unit") @Operation(summary = "按单元获取楼层列表")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<List<PropertyFloorDO>> listByUnit(@RequestParam("unitId") Long unitId) {
        return success(floorService.getFloorListByUnitId(unitId));
    }

    @GetMapping("/list-by-building") @Operation(summary = "按楼栋获取楼层列表")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<List<PropertyFloorDO>> listByBuilding(@RequestParam("buildingId") Long buildingId) {
        return success(floorService.getFloorListByBuildingId(buildingId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取楼层列表")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<List<PropertyFloorDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(floorService.getFloorListByCommunityId(communityId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取楼层列表")
    @PreAuthorize("@ss.hasPermission('property:space:floor:query')")
    public CommonResult<List<PropertyFloorDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(floorService.getFloorListByStatus(status));
    }
}

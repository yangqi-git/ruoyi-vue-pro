package cn.iocoder.yudao.module.property.controller.admin.space;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyHouseDO;
import cn.iocoder.yudao.module.property.service.space.PropertyHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 房屋")
@RestController @RequestMapping("/property/space/house") @Validated
public class PropertyHouseController {

    @Resource private PropertyHouseService houseService;

    @PostMapping("/create") @Operation(summary = "创建房屋")
    @PreAuthorize("@ss.hasPermission('property:space:house:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyHouseSaveReqVO reqVO) {
        return success(houseService.createHouse(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新房屋")
    @PreAuthorize("@ss.hasPermission('property:space:house:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyHouseSaveReqVO reqVO) {
        houseService.updateHouse(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除房屋")
    @PreAuthorize("@ss.hasPermission('property:space:house:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        houseService.deleteHouse(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得房屋")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<PropertyHouseDO> get(@RequestParam("id") Long id) {
        return success(houseService.getHouse(id));
    }

    @GetMapping("/page") @Operation(summary = "房屋分页")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<PageResult<PropertyHouseDO>> page(@Valid PropertyHousePageReqVO pageReqVO) {
        return success(houseService.getHousePage(pageReqVO.getProjectId(), pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getFloorId(), pageReqVO.getUnitId(), pageReqVO.getBuildingId(),
                pageReqVO.getCommunityId(), pageReqVO.getHouseStatus(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> list() {
        return success(houseService.getHouseList());
    }

    @GetMapping("/list-by-floor") @Operation(summary = "按楼层获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByFloor(@RequestParam("floorId") Long floorId) {
        return success(houseService.getHouseListByFloorId(floorId));
    }

    @GetMapping("/list-by-unit") @Operation(summary = "按单元获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByUnit(@RequestParam("unitId") Long unitId) {
        return success(houseService.getHouseListByUnitId(unitId));
    }

    @GetMapping("/list-by-building") @Operation(summary = "按楼栋获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByBuilding(@RequestParam("buildingId") Long buildingId) {
        return success(houseService.getHouseListByBuildingId(buildingId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(houseService.getHouseListByCommunityId(communityId));
    }

    @GetMapping("/list-by-house-status") @Operation(summary = "按房屋状态获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByHouseStatus(@RequestParam("houseStatus") Integer houseStatus) {
        return success(houseService.getHouseListByHouseStatus(houseStatus));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取房屋列表")
    @PreAuthorize("@ss.hasPermission('property:space:house:query')")
    public CommonResult<List<PropertyHouseDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(houseService.getHouseListByStatus(status));
    }
}

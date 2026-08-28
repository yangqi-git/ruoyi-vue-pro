package cn.iocoder.yudao.module.property.controller.admin.space;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyUnitDO;
import cn.iocoder.yudao.module.property.service.space.PropertyUnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 单元")
@RestController @RequestMapping("/property/space/unit") @Validated
public class PropertyUnitController {

    @Resource private PropertyUnitService unitService;

    @PostMapping("/create") @Operation(summary = "创建单元")
    @PreAuthorize("@ss.hasPermission('property:space:unit:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyUnitSaveReqVO reqVO) {
        return success(unitService.createUnit(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新单元")
    @PreAuthorize("@ss.hasPermission('property:space:unit:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyUnitSaveReqVO reqVO) {
        unitService.updateUnit(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除单元")
    @PreAuthorize("@ss.hasPermission('property:space:unit:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        unitService.deleteUnit(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得单元")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<PropertyUnitDO> get(@RequestParam("id") Long id) {
        return success(unitService.getUnit(id));
    }

    @GetMapping("/page") @Operation(summary = "单元分页")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<PageResult<PropertyUnitDO>> page(@Valid PropertyUnitPageReqVO pageReqVO) {
        return success(unitService.getUnitPage(pageReqVO.getProjectId(), pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getBuildingId(), pageReqVO.getCommunityId(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "单元列表")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<List<PropertyUnitDO>> list() {
        return success(unitService.getUnitList());
    }

    @GetMapping("/list-by-building") @Operation(summary = "按楼栋获取单元列表")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<List<PropertyUnitDO>> listByBuilding(@RequestParam("buildingId") Long buildingId) {
        return success(unitService.getUnitListByBuildingId(buildingId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取单元列表")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<List<PropertyUnitDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(unitService.getUnitListByCommunityId(communityId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取单元列表")
    @PreAuthorize("@ss.hasPermission('property:space:unit:query')")
    public CommonResult<List<PropertyUnitDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(unitService.getUnitListByStatus(status));
    }
}

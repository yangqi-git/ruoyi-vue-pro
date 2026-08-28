package cn.iocoder.yudao.module.property.controller.admin.space;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.space.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.space.PropertyResidentDO;
import cn.iocoder.yudao.module.property.service.space.PropertyResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 住户")
@RestController @RequestMapping("/property/space/resident") @Validated
public class PropertyResidentController {

    @Resource private PropertyResidentService residentService;

    @PostMapping("/create") @Operation(summary = "创建住户")
    @PreAuthorize("@ss.hasPermission('property:space:resident:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyResidentSaveReqVO reqVO) {
        return success(residentService.createResident(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新住户")
    @PreAuthorize("@ss.hasPermission('property:space:resident:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyResidentSaveReqVO reqVO) {
        residentService.updateResident(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除住户")
    @PreAuthorize("@ss.hasPermission('property:space:resident:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        residentService.deleteResident(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得住户")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<PropertyResidentDO> get(@RequestParam("id") Long id) {
        return success(residentService.getResident(id));
    }

    @GetMapping("/page") @Operation(summary = "住户分页")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<PageResult<PropertyResidentDO>> page(@Valid PropertyResidentPageReqVO pageReqVO) {
        return success(residentService.getResidentPage(pageReqVO.getProjectId(), pageReqVO.getName(), pageReqVO.getIdCard(),
                pageReqVO.getHouseId(), pageReqVO.getCommunityId(), pageReqVO.getResidentType(),
                pageReqVO.getStatus(), pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "住户列表")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<List<PropertyResidentDO>> list() {
        return success(residentService.getResidentList());
    }

    @GetMapping("/list-by-house") @Operation(summary = "按房屋获取住户列表")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<List<PropertyResidentDO>> listByHouse(@RequestParam("houseId") Long houseId) {
        return success(residentService.getResidentListByHouseId(houseId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取住户列表")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<List<PropertyResidentDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(residentService.getResidentListByCommunityId(communityId));
    }

    @GetMapping("/list-by-type") @Operation(summary = "按类型获取住户列表")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<List<PropertyResidentDO>> listByType(@RequestParam("residentType") Integer residentType) {
        return success(residentService.getResidentListByResidentType(residentType));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取住户列表")
    @PreAuthorize("@ss.hasPermission('property:space:resident:query')")
    public CommonResult<List<PropertyResidentDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(residentService.getResidentListByStatus(status));
    }
}

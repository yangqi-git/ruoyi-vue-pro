package cn.iocoder.yudao.module.property.controller.admin.parking;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyVehicleDO;
import cn.iocoder.yudao.module.property.service.parking.PropertyVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 车辆管理")
@RestController @RequestMapping("/property/parking/vehicle") @Validated
public class PropertyVehicleController {

    @Resource private PropertyVehicleService vehicleService;

    @PostMapping("/create") @Operation(summary = "创建车辆")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyVehicleSaveReqVO reqVO) {
        return success(vehicleService.createVehicle(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新车辆")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyVehicleSaveReqVO reqVO) {
        vehicleService.updateVehicle(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除车辆")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        vehicleService.deleteVehicle(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得车辆")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:query')")
    public CommonResult<PropertyVehicleDO> get(@RequestParam("id") Long id) {
        return success(vehicleService.getVehicle(id));
    }

    @GetMapping("/page") @Operation(summary = "车辆分页")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:query')")
    public CommonResult<PageResult<PropertyVehicleDO>> page(@Valid PropertyVehiclePageReqVO pageReqVO) {
        return success(vehicleService.getVehiclePage(pageReqVO.getProjectId(), pageReqVO.getCommunityId(),
                pageReqVO.getResidentId(), pageReqVO.getVehicleStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-resident") @Operation(summary = "按住户获取车辆列表")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:query')")
    public CommonResult<List<PropertyVehicleDO>> listByResident(@RequestParam("residentId") Long residentId) {
        return success(vehicleService.getVehicleListByResidentId(residentId));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取车辆列表")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:query')")
    public CommonResult<List<PropertyVehicleDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(vehicleService.getVehicleListByCommunityId(communityId));
    }

    @GetMapping("/get-by-plate") @Operation(summary = "按车牌号获取车辆")
    @PreAuthorize("@ss.hasPermission('property:parking:vehicle:query')")
    public CommonResult<PropertyVehicleDO> getByPlate(@RequestParam("plateNo") String plateNo) {
        return success(vehicleService.getVehicleByPlateNo(plateNo));
    }
}

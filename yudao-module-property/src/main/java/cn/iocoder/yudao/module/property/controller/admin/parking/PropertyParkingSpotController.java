package cn.iocoder.yudao.module.property.controller.admin.parking;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingSpotDO;
import cn.iocoder.yudao.module.property.service.parking.PropertyParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 车位管理")
@RestController @RequestMapping("/property/parking/spot") @Validated
public class PropertyParkingSpotController {

    @Resource private PropertyParkingSpotService parkingSpotService;

    @PostMapping("/create") @Operation(summary = "创建车位")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyParkingSpotSaveReqVO reqVO) {
        return success(parkingSpotService.createParkingSpot(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新车位")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyParkingSpotSaveReqVO reqVO) {
        parkingSpotService.updateParkingSpot(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除车位")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        parkingSpotService.deleteParkingSpot(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得车位")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:query')")
    public CommonResult<PropertyParkingSpotDO> get(@RequestParam("id") Long id) {
        return success(parkingSpotService.getParkingSpot(id));
    }

    @GetMapping("/page") @Operation(summary = "车位分页")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:query')")
    public CommonResult<PageResult<PropertyParkingSpotDO>> page(@Valid PropertyParkingSpotPageReqVO pageReqVO) {
        return success(parkingSpotService.getParkingSpotPage(pageReqVO.getProjectId(), pageReqVO.getParkingLotId(),
                pageReqVO.getCommunityId(), pageReqVO.getSpotType(), pageReqVO.getSpotStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-lot") @Operation(summary = "按车场获取车位列表")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:query')")
    public CommonResult<List<PropertyParkingSpotDO>> listByLot(@RequestParam("parkingLotId") Long parkingLotId) {
        return success(parkingSpotService.getParkingSpotListByParkingLotId(parkingLotId));
    }

    @GetMapping("/available") @Operation(summary = "获取可用车位列表")
    @PreAuthorize("@ss.hasPermission('property:parking:spot:query')")
    public CommonResult<List<PropertyParkingSpotDO>> available(@RequestParam("communityId") Long communityId) {
        return success(parkingSpotService.getAvailableSpots(communityId));
    }
}

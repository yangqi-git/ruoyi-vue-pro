package cn.iocoder.yudao.module.property.controller.admin.parking;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLotDO;
import cn.iocoder.yudao.module.property.service.parking.PropertyParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 车场管理")
@RestController @RequestMapping("/property/parking/lot") @Validated
public class PropertyParkingLotController {

    @Resource private PropertyParkingLotService parkingLotService;

    @PostMapping("/create") @Operation(summary = "创建车场")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyParkingLotSaveReqVO reqVO) {
        return success(parkingLotService.createParkingLot(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新车场")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyParkingLotSaveReqVO reqVO) {
        parkingLotService.updateParkingLot(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除车场")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        parkingLotService.deleteParkingLot(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得车场")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:query')")
    public CommonResult<PropertyParkingLotDO> get(@RequestParam("id") Long id) {
        return success(parkingLotService.getParkingLot(id));
    }

    @GetMapping("/page") @Operation(summary = "车场分页")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:query')")
    public CommonResult<PageResult<PropertyParkingLotDO>> page(@Valid PropertyParkingLotPageReqVO pageReqVO) {
        return success(parkingLotService.getParkingLotPage(pageReqVO.getProjectId(), pageReqVO.getCommunityId(),
                pageReqVO.getParkingType(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-community") @Operation(summary = "按小区获取车场列表")
    @PreAuthorize("@ss.hasPermission('property:parking:lot:query')")
    public CommonResult<List<PropertyParkingLotDO>> listByCommunity(@RequestParam("communityId") Long communityId) {
        return success(parkingLotService.getParkingLotListByCommunityId(communityId));
    }
}

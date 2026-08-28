package cn.iocoder.yudao.module.property.controller.admin.parking;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.discount.vo.PropertyApproveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.parking.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.parking.PropertyParkingLeaseDO;
import cn.iocoder.yudao.module.property.service.parking.PropertyParkingLeaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 车位租赁")
@RestController @RequestMapping("/property/parking/lease") @Validated
public class PropertyParkingLeaseController {

    @Resource private PropertyParkingLeaseService parkingLeaseService;

    @PostMapping("/create") @Operation(summary = "创建车位租赁")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyParkingLeaseSaveReqVO reqVO) {
        return success(parkingLeaseService.createParkingLease(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新车位租赁")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyParkingLeaseSaveReqVO reqVO) {
        parkingLeaseService.updateParkingLease(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除车位租赁")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        parkingLeaseService.deleteParkingLease(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得车位租赁")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:query')")
    public CommonResult<PropertyParkingLeaseDO> get(@RequestParam("id") Long id) {
        return success(parkingLeaseService.getParkingLease(id));
    }

    @GetMapping("/page") @Operation(summary = "车位租赁分页")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:query')")
    public CommonResult<PageResult<PropertyParkingLeaseDO>> page(@Valid PropertyParkingLeasePageReqVO pageReqVO) {
        return success(parkingLeaseService.getParkingLeasePage(pageReqVO.getProjectId(), pageReqVO.getParkingSpotId(),
                pageReqVO.getCommunityId(), pageReqVO.getResidentId(),
                pageReqVO.getLeaseType(), pageReqVO.getLeaseStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list-by-spot") @Operation(summary = "按车位获取租赁列表")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:query')")
    public CommonResult<List<PropertyParkingLeaseDO>> listBySpot(@RequestParam("parkingSpotId") Long parkingSpotId) {
        return success(parkingLeaseService.getParkingLeaseListByParkingSpotId(parkingSpotId));
    }

    @GetMapping("/list-by-resident") @Operation(summary = "按住户获取租赁列表")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:query')")
    public CommonResult<List<PropertyParkingLeaseDO>> listByResident(@RequestParam("residentId") Long residentId) {
        return success(parkingLeaseService.getParkingLeaseListByResidentId(residentId));
    }

    @PostMapping("/approve") @Operation(summary = "审批车位租赁")
    @PreAuthorize("@ss.hasPermission('property:parking:lease:approve')")
    public CommonResult<Boolean> approve(@Valid @RequestBody PropertyApproveReqVO reqVO) {
        parkingLeaseService.approveParkingLease(reqVO.getId(), null, reqVO.getApprovalStatus(), reqVO.getApprovalRemark());
        return success(true);
    }
}

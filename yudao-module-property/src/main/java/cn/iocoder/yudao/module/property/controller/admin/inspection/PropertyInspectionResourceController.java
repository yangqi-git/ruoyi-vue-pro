package cn.iocoder.yudao.module.property.controller.admin.inspection;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionPointSaveReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionResourcePageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.inspection.vo.PropertyInspectionStandardSaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionPointDO;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionStandardDO;
import cn.iocoder.yudao.module.property.service.inspection.PropertyInspectionResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业巡检标准与点位")
@RestController
@RequestMapping("/property/inspection/resource")
@Validated
public class PropertyInspectionResourceController {
    @Resource private PropertyInspectionResourceService resourceService;

    @PostMapping("/standard/create")
    @PreAuthorize("@ss.hasPermission('property:inspection:standard:create')")
    public CommonResult<Long> createStandard(@Valid @RequestBody PropertyInspectionStandardSaveReqVO reqVO) {
        return success(resourceService.createStandard(reqVO));
    }

    @PutMapping("/standard/update")
    @PreAuthorize("@ss.hasPermission('property:inspection:standard:update')")
    public CommonResult<Boolean> updateStandard(@Valid @RequestBody PropertyInspectionStandardSaveReqVO reqVO) {
        resourceService.updateStandard(reqVO);
        return success(true);
    }

    @PutMapping("/standard/publish")
    @PreAuthorize("@ss.hasPermission('property:inspection:standard:publish')")
    public CommonResult<Boolean> publishStandard(@RequestParam Long id, @RequestParam Long projectId) {
        resourceService.publishStandard(id, projectId);
        return success(true);
    }

    @GetMapping("/standard/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:standard:query')")
    public CommonResult<PageResult<PropertyInspectionStandardDO>> standardPage(
            @Valid PropertyInspectionResourcePageReqVO reqVO) {
        return success(resourceService.getStandardPage(reqVO));
    }

    @PostMapping("/point/create")
    @PreAuthorize("@ss.hasPermission('property:inspection:point:create')")
    public CommonResult<Long> createPoint(@Valid @RequestBody PropertyInspectionPointSaveReqVO reqVO) {
        return success(resourceService.createPoint(reqVO));
    }

    @PutMapping("/point/update")
    @PreAuthorize("@ss.hasPermission('property:inspection:point:update')")
    public CommonResult<Boolean> updatePoint(@Valid @RequestBody PropertyInspectionPointSaveReqVO reqVO) {
        resourceService.updatePoint(reqVO);
        return success(true);
    }

    @GetMapping("/point/page")
    @PreAuthorize("@ss.hasPermission('property:inspection:point:query')")
    public CommonResult<PageResult<PropertyInspectionPointDO>> pointPage(
            @Valid PropertyInspectionResourcePageReqVO reqVO) {
        return success(resourceService.getPointPage(reqVO));
    }
}

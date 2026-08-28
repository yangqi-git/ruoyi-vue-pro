package cn.iocoder.yudao.module.property.controller.admin.org;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyProjectDO;
import cn.iocoder.yudao.module.property.service.org.PropertyProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业项目")
@RestController
@RequestMapping("/property/org/project")
@Validated
public class PropertyProjectController {

    @Resource
    private PropertyProjectService projectService;

    @PostMapping("/create") @Operation(summary = "创建物业项目")
    @PreAuthorize("@ss.hasPermission('property:org:project:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyProjectSaveReqVO reqVO) {
        return success(projectService.createProject(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新物业项目")
    @PreAuthorize("@ss.hasPermission('property:org:project:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyProjectSaveReqVO reqVO) {
        projectService.updateProject(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除物业项目")
    @PreAuthorize("@ss.hasPermission('property:org:project:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得物业项目")
    @PreAuthorize("@ss.hasPermission('property:org:project:query')")
    public CommonResult<PropertyProjectDO> get(@RequestParam("id") Long id) {
        return success(projectService.getProject(id));
    }

    @GetMapping("/page") @Operation(summary = "物业项目分页")
    @PreAuthorize("@ss.hasPermission('property:org:project:query')")
    public CommonResult<PageResult<PropertyProjectDO>> page(@Valid PropertyProjectPageReqVO pageReqVO) {
        return success(projectService.getProjectPage(pageReqVO.getName(), pageReqVO.getCode(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "物业项目列表")
    @PreAuthorize("@ss.hasPermission('property:org:project:query')")
    public CommonResult<List<PropertyProjectDO>> list() {
        return success(projectService.getProjectList());
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取物业项目列表")
    @PreAuthorize("@ss.hasPermission('property:org:project:query')")
    public CommonResult<List<PropertyProjectDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(projectService.getProjectListByStatus(status));
    }
}
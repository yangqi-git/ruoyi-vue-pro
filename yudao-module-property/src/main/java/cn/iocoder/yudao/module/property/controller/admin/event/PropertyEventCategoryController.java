package cn.iocoder.yudao.module.property.controller.admin.event;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategoryPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyEventCategorySaveReqVO;
import cn.iocoder.yudao.module.property.dal.dataobject.event.PropertyEventCategoryDO;
import cn.iocoder.yudao.module.property.service.event.PropertyEventCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 物业事件分类")
@RestController
@RequestMapping("/property/event/category")
@Validated
public class PropertyEventCategoryController {
    @Resource
    private PropertyEventCategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermission('property:event:category:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyEventCategorySaveReqVO reqVO) {
        return success(categoryService.createCategory(reqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('property:event:category:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyEventCategorySaveReqVO reqVO) {
        categoryService.updateCategory(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('property:event:category:delete')")
    public CommonResult<Boolean> delete(@RequestParam Long id, @RequestParam Long projectId) {
        categoryService.deleteCategory(id, projectId);
        return success(true);
    }

    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('property:event:category:query')")
    public CommonResult<PropertyEventCategoryDO> get(@RequestParam Long id, @RequestParam Long projectId) {
        return success(categoryService.getCategory(id, projectId));
    }

    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('property:event:category:query')")
    public CommonResult<PageResult<PropertyEventCategoryDO>> page(@Valid PropertyEventCategoryPageReqVO reqVO) {
        return success(categoryService.getCategoryPage(reqVO));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得当前项目启用的事件分类")
    @PreAuthorize("@ss.hasAnyPermissions('property:event:query','property:event:category:query')")
    public CommonResult<List<PropertyEventCategoryDO>> simpleList(@RequestParam Long projectId) {
        return success(categoryService.getEnabledCategoryList(projectId));
    }
}

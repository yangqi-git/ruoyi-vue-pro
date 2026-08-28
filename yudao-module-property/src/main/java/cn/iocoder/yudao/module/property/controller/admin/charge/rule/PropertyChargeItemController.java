package cn.iocoder.yudao.module.property.controller.admin.charge.rule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.charge.rule.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.rule.PropertyChargeItemDO;
import cn.iocoder.yudao.module.property.service.charge.rule.PropertyChargeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 收费项目")
@RestController @RequestMapping("/property/charge/item") @Validated
public class PropertyChargeItemController {

    @Resource private PropertyChargeItemService itemService;

    @PostMapping("/create") @Operation(summary = "创建收费项目")
    @PreAuthorize("@ss.hasPermission('property:charge:item:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyChargeItemSaveReqVO reqVO) {
        return success(itemService.createItem(reqVO));
    }

    @PutMapping("/update") @Operation(summary = "更新收费项目")
    @PreAuthorize("@ss.hasPermission('property:charge:item:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PropertyChargeItemSaveReqVO reqVO) {
        itemService.updateItem(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete") @Operation(summary = "删除收费项目")
    @PreAuthorize("@ss.hasPermission('property:charge:item:delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
        return success(true);
    }

    @GetMapping("/get") @Operation(summary = "获得收费项目")
    @PreAuthorize("@ss.hasPermission('property:charge:item:query')")
    public CommonResult<PropertyChargeItemDO> get(@RequestParam("id") Long id) {
        return success(itemService.getItem(id));
    }

    @GetMapping("/page") @Operation(summary = "收费项目分页")
    @PreAuthorize("@ss.hasPermission('property:charge:item:query')")
    public CommonResult<PageResult<PropertyChargeItemDO>> page(@Valid PropertyChargeItemPageReqVO pageReqVO) {
        return success(itemService.getItemPage(pageReqVO.getName(), pageReqVO.getCode(),
                pageReqVO.getProjectId(), pageReqVO.getItemType(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @GetMapping("/list") @Operation(summary = "收费项目列表")
    @PreAuthorize("@ss.hasPermission('property:charge:item:query')")
    public CommonResult<List<PropertyChargeItemDO>> list() {
        return success(itemService.getItemList());
    }

    @GetMapping("/list-by-project") @Operation(summary = "按项目获取收费项目列表")
    @PreAuthorize("@ss.hasPermission('property:charge:item:query')")
    public CommonResult<List<PropertyChargeItemDO>> listByProject(@RequestParam("projectId") Long projectId) {
        return success(itemService.getItemListByProjectId(projectId));
    }

    @GetMapping("/list-by-status") @Operation(summary = "按状态获取收费项目列表")
    @PreAuthorize("@ss.hasPermission('property:charge:item:query')")
    public CommonResult<List<PropertyChargeItemDO>> listByStatus(@RequestParam("status") Integer status) {
        return success(itemService.getItemListByStatus(status));
    }
}
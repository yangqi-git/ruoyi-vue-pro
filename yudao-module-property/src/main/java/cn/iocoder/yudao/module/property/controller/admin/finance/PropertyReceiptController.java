package cn.iocoder.yudao.module.property.controller.admin.finance;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.finance.vo.receipt.*;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyReceiptDO;
import cn.iocoder.yudao.module.property.service.finance.PropertyReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.object.BeanUtils.toBean;

@Tag(name = "管理后台 - 票据管理")
@RestController @RequestMapping("/property/finance/receipt") @Validated
public class PropertyReceiptController {

    @Resource private PropertyReceiptService receiptService;

    @PostMapping("/create") @Operation(summary = "开具票据")
    @PreAuthorize("@ss.hasPermission('property:finance:receipt:create')")
    public CommonResult<Long> create(@Valid @RequestBody PropertyReceiptSaveReqVO reqVO) {
        return success(receiptService.createReceipt(toBean(reqVO, PropertyReceiptDO.class)));
    }

    @GetMapping("/get") @Operation(summary = "获得票据")
    @PreAuthorize("@ss.hasPermission('property:finance:receipt:query')")
    public CommonResult<PropertyReceiptDO> get(@RequestParam("id") Long id) {
        return success(receiptService.getReceipt(id));
    }

    @GetMapping("/page") @Operation(summary = "票据分页")
    @PreAuthorize("@ss.hasPermission('property:finance:receipt:query')")
    public CommonResult<PageResult<PropertyReceiptDO>> page(@Valid PropertyReceiptPageReqVO pageReqVO) {
        return success(receiptService.getReceiptPage(pageReqVO.getReceiptType(), pageReqVO.getPayerId(),
                pageReqVO.getBeginDate(), pageReqVO.getEndDate(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }
}

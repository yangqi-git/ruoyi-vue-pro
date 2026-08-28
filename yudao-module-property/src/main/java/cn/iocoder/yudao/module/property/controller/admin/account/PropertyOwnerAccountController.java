package cn.iocoder.yudao.module.property.controller.admin.account;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.account.vo.*;
import cn.iocoder.yudao.module.property.dal.dataobject.account.PropertyOwnerAccountDO;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyPrepayRecordDO;
import cn.iocoder.yudao.module.property.service.account.PropertyOwnerAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 业主账户")
@RestController @RequestMapping("/property/account") @Validated
public class PropertyOwnerAccountController {

    @Resource private PropertyOwnerAccountService accountService;

    @GetMapping("/get") @Operation(summary = "获得账户")
    @PreAuthorize("@ss.hasPermission('property:account:query')")
    public CommonResult<PropertyOwnerAccountDO> get(@RequestParam("id") Long id) {
        return success(accountService.getAccount(id));
    }

    @GetMapping("/page") @Operation(summary = "账户分页")
    @PreAuthorize("@ss.hasPermission('property:account:query')")
    public CommonResult<PageResult<PropertyOwnerAccountDO>> page(@Valid PropertyAccountPageReqVO pageReqVO) {
        return success(accountService.getAccountPage(pageReqVO.getOwnerId(), pageReqVO.getStatus(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }

    @PostMapping("/top-up") @Operation(summary = "预存款充值")
    @PreAuthorize("@ss.hasPermission('property:account:top-up')")
    public CommonResult<Boolean> topUp(@Valid @RequestBody PropertyPrepayTopUpReqVO reqVO) {
        accountService.topUp(reqVO.getAccountId(), reqVO.getAmount(), reqVO.getPayChannel());
        return success(true);
    }

    @GetMapping("/prepay-record/page") @Operation(summary = "预存款流水分页")
    @PreAuthorize("@ss.hasPermission('property:account:query')")
    public CommonResult<PageResult<PropertyPrepayRecordDO>> prepayRecordPage(@Valid PropertyPrepayRecordPageReqVO pageReqVO) {
        return success(accountService.getPrepayRecordPage(pageReqVO.getAccountId(), pageReqVO.getRecordType(),
                pageReqVO.getBeginTime(), pageReqVO.getEndTime(),
                pageReqVO.getPageNo(), pageReqVO.getPageSize()));
    }
}

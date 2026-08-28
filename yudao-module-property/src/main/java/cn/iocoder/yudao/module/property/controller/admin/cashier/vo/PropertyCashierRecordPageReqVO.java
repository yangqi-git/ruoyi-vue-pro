package cn.iocoder.yudao.module.property.controller.admin.cashier.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "收银记录分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyCashierRecordPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;

    @Schema(description = "记录编号") private String recordNo;
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "支付类型") private Integer payType;
    @Schema(description = "支付状态") private Integer payStatus;
    @Schema(description = "记录状态") private Integer recordStatus;
}

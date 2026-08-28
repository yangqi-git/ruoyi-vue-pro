package cn.iocoder.yudao.module.property.controller.admin.refund.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "退款单分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyRefundRecordPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;

    @Schema(description = "退款编号") private String refundNo;
    @Schema(description = "收银记录ID") private Long cashierRecordId;
    @Schema(description = "账单ID") private Long billId;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "退款类型") private Integer refundType;
    @Schema(description = "退款状态") private Integer refundStatus;
    @Schema(description = "审批状态") private Integer approvalStatus;
}

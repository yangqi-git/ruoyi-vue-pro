package cn.iocoder.yudao.module.property.controller.admin.discount.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 调价记录分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyAdjustRecordPageReqVO extends PageParam {
    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "房屋ID")
    private Long houseId;

    @Schema(description = "账单ID")
    private Long billId;

    @Schema(description = "调价类型")
    private Integer adjustType;

    @Schema(description = "审批状态")
    private Integer approvalStatus;
}
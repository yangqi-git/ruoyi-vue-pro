package cn.iocoder.yudao.module.property.controller.admin.settlement.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 结算批次分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertySettlementBatchPageReqVO extends PageParam {
    private Long projectId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "批次类型")
    private Integer batchType;

    @Schema(description = "批次状态")
    private Integer batchStatus;

    @Schema(description = "审批状态")
    private Integer approvalStatus;
}

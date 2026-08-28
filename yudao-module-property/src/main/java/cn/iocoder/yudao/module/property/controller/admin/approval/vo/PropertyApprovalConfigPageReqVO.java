package cn.iocoder.yudao.module.property.controller.admin.approval.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 审批阈值配置分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyApprovalConfigPageReqVO extends PageParam {
    private Long projectId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "审批类型")
    private Integer approvalType;

    @Schema(description = "状态")
    private Integer status;
}

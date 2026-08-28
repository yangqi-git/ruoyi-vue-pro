package cn.iocoder.yudao.module.property.controller.admin.bill.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "应收账单分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyReceivableBillPageReqVO extends PageParam {
    @Schema(description = "账单编号") private String billNo;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "项目ID") private Long projectId;
    @Schema(description = "账单类型") private Integer billType;
    @Schema(description = "支付状态") private Integer payStatus;
    @Schema(description = "账单状态") private Integer billStatus;
}
package cn.iocoder.yudao.module.property.controller.admin.charge.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "费用标准分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class FeeStandardPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;
    @Schema(description = "房屋类型") private String houseType;
    @Schema(description = "状态(0停用/1启用)") private Integer status;
}

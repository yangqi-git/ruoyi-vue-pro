package cn.iocoder.yudao.module.property.controller.admin.account.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "业主账户分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyAccountPageReqVO extends PageParam {
    @Schema(description = "业主ID") private Long ownerId;
    @Schema(description = "账户状态") private Integer status;
}

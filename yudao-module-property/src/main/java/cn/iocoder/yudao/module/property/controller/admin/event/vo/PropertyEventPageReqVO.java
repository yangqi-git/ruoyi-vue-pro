package cn.iocoder.yudao.module.property.controller.admin.event.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 物业事件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PropertyEventPageReqVO extends PageParam {

    @NotNull(message = "项目不能为空")
    private Long projectId;
    private Integer status;
    private Integer urgencyLevel;
    private String categoryCode;
    private Long responsibleUserId;
    private String keyword;
}

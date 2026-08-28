package cn.iocoder.yudao.module.property.controller.admin.org.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "物业项目分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyProjectPageReqVO extends PageParam {
    @Schema(description = "项目名称") private String name;
    @Schema(description = "项目编码") private String code;
    @Schema(description = "状态") private Integer status;
}
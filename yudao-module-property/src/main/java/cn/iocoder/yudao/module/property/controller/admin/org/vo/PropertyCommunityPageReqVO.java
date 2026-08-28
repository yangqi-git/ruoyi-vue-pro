package cn.iocoder.yudao.module.property.controller.admin.org.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "小区分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyCommunityPageReqVO extends PageParam {
    @Schema(description = "小区名称") private String name;
    @Schema(description = "小区编码") private String code;
    @Schema(description = "项目ID") private Long projectId;
    @Schema(description = "状态") private Integer status;
}
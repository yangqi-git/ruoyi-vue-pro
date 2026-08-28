package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "楼栋分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyBuildingPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;

    @Schema(description = "楼栋名称") private String name;
    @Schema(description = "楼栋编码") private String code;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "状态") private Integer status;
}

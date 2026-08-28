package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "楼层分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyFloorPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;

    @Schema(description = "楼层名称") private String name;
    @Schema(description = "楼层编码") private String code;
    @Schema(description = "单元ID") private Long unitId;
    @Schema(description = "楼栋ID") private Long buildingId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "状态") private Integer status;
}

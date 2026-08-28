package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "房屋分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyHousePageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;
    @Schema(description = "房屋名称") private String name;
    @Schema(description = "房屋编码") private String code;
    @Schema(description = "楼层ID") private Long floorId;
    @Schema(description = "单元ID") private Long unitId;
    @Schema(description = "楼栋ID") private Long buildingId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "房屋状态") private Integer houseStatus;
    @Schema(description = "状态") private Integer status;
}

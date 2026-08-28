package cn.iocoder.yudao.module.property.controller.admin.space.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "住户分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyResidentPageReqVO extends PageParam {
    @Schema(description = "项目ID") private Long projectId;

    @Schema(description = "住户姓名") private String name;
    @Schema(description = "身份证号") private String idCard;
    @Schema(description = "房屋ID") private Long houseId;
    @Schema(description = "小区ID") private Long communityId;
    @Schema(description = "住户类型") private Integer residentType;
    @Schema(description = "状态") private Integer status;
}

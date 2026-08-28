package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 车场分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyParkingLotPageReqVO extends PageParam {
    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "车场类型")
    private Integer parkingType;

    @Schema(description = "状态")
    private Integer status;
}

package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 车位分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyParkingSpotPageReqVO extends PageParam {
    private Long projectId;

    @Schema(description = "车场ID")
    private Long parkingLotId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "车位类型")
    private Integer spotType;

    @Schema(description = "车位状态")
    private Integer spotStatus;
}

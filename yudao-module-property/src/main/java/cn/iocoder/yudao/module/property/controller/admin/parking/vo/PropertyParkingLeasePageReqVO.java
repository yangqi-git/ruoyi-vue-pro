package cn.iocoder.yudao.module.property.controller.admin.parking.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 车位租赁分页 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyParkingLeasePageReqVO extends PageParam {
    private Long projectId;

    @Schema(description = "车位ID")
    private Long parkingSpotId;

    @Schema(description = "小区ID")
    private Long communityId;

    @Schema(description = "住户ID")
    private Long residentId;

    @Schema(description = "租赁类型")
    private Integer leaseType;

    @Schema(description = "租赁状态")
    private Integer leaseStatus;
}

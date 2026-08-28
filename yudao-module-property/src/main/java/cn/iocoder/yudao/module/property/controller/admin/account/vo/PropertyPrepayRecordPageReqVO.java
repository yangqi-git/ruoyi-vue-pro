package cn.iocoder.yudao.module.property.controller.admin.account.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "预存款流水查询 Request VO")
@Data @EqualsAndHashCode(callSuper = true)
public class PropertyPrepayRecordPageReqVO extends PageParam {
    @Schema(description = "账户ID") private Long accountId;
    @Schema(description = "流水类型") private Integer recordType;
    @Schema(description = "开始时间(yyyy-MM-ddTHH:mm:ss)") private String beginTime;
    @Schema(description = "结束时间(yyyy-MM-ddTHH:mm:ss)") private String endTime;
}

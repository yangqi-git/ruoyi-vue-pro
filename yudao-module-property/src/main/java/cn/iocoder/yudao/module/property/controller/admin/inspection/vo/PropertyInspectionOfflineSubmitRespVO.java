package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PropertyInspectionOfflineSubmitRespVO {
    private Boolean accepted;
    private Long recordId;
    private Long conflictId;
    private String conflictReason;
}

package cn.iocoder.yudao.module.property.controller.admin.inspection.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PropertyInspectionPlanSaveReqVO {
    private Long id;
    @NotNull private Long projectId;
    @NotBlank private String name;
    @NotBlank private String specialty;
    @NotBlank private String standardIds;
    @NotBlank private String pointIds;
    @NotBlank private String frequencyType;
    @NotNull @Min(1) private Integer intervalValue;
    @NotNull @Min(1) private Integer windowMinutes;
    @NotNull private LocalDate effectiveStart;
    private LocalDate effectiveEnd;
    @NotNull private Boolean skipHolidays;
    @NotBlank private String supplementPolicy;
    @NotNull private Long inspectorUserId;
    @NotNull private Integer status;
    private Integer version;
}

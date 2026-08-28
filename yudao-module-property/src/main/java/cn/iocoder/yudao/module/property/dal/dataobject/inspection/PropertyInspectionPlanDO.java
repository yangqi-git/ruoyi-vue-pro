package cn.iocoder.yudao.module.property.dal.dataobject.inspection;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("property_inspection_plan")
@Data @Builder @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper = true)
public class PropertyInspectionPlanDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private String name;
    private String specialty;
    private String standardIds;
    private String pointIds;
    private String frequencyType;
    private Integer intervalValue;
    private Integer windowMinutes;
    private LocalDate effectiveStart;
    private LocalDate effectiveEnd;
    private Boolean skipHolidays;
    private String supplementPolicy;
    private Long inspectorUserId;
    private Integer status;
    private LocalDateTime nextGenerateTime;
    @Version private Integer version;
}

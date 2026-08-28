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

@TableName("property_inspection_point")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PropertyInspectionPointDO extends TenantBaseDO {
    @TableId private Long id;
    private Long projectId;
    private Long communityId;
    private Long spaceId;
    private Long assetId;
    private String pointCode;
    private String name;
    private String pointType;
    private String qrCode;
    private String nfcCode;
    private Integer riskLevel;
    private Boolean sequenceRequired;
    private Integer status;
    private String disableReason;
    @Version private Integer version;
}

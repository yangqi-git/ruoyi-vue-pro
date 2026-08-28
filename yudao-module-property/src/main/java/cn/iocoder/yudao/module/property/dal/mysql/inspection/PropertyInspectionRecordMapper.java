package cn.iocoder.yudao.module.property.dal.mysql.inspection;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.inspection.PropertyInspectionRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyInspectionRecordMapper extends BaseMapperX<PropertyInspectionRecordDO> {
    default PropertyInspectionRecordDO selectByClientOperationId(String clientOperationId) {
        return selectOne(PropertyInspectionRecordDO::getClientOperationId, clientOperationId);
    }

    default List<PropertyInspectionRecordDO> selectListByTaskId(Long taskId) {
        return selectList(new LambdaQueryWrapperX<PropertyInspectionRecordDO>()
                .eq(PropertyInspectionRecordDO::getTaskId, taskId)
                .orderByAsc(PropertyInspectionRecordDO::getSequenceNo));
    }
}

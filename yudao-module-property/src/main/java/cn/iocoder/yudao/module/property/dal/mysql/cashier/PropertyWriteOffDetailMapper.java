package cn.iocoder.yudao.module.property.dal.mysql.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyWriteOffDetailDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyWriteOffDetailMapper extends BaseMapperX<PropertyWriteOffDetailDO> {

    default PageResult<PropertyWriteOffDetailDO> selectPage(Long recordId, Long billId, Integer writeOffType, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyWriteOffDetailDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyWriteOffDetailDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyWriteOffDetailDO>()
                .eqIfPresent(PropertyWriteOffDetailDO::getRecordId, recordId)
                .eqIfPresent(PropertyWriteOffDetailDO::getBillId, billId)
                .eqIfPresent(PropertyWriteOffDetailDO::getWriteOffType, writeOffType)
                .eqIfPresent(PropertyWriteOffDetailDO::getStatus, status)
                .
        orderByDesc(PropertyWriteOffDetailDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyWriteOffDetailDO> selectListByRecordId(Long recordId) {
        return selectList(PropertyWriteOffDetailDO::getRecordId, recordId);
    }

    default List<PropertyWriteOffDetailDO> selectListByBillId(Long billId) {
        return selectList(PropertyWriteOffDetailDO::getBillId, billId);
    }

    default List<PropertyWriteOffDetailDO> selectListByStatus(Integer status) {
        return selectList(PropertyWriteOffDetailDO::getStatus, status);
    }

    default List<PropertyWriteOffDetailDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyWriteOffDetailDO>()
                .orderByDesc(PropertyWriteOffDetailDO::getId));
    }
}
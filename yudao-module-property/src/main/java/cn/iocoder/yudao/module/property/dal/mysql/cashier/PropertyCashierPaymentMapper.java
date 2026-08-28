package cn.iocoder.yudao.module.property.dal.mysql.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierPaymentDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyCashierPaymentMapper extends BaseMapperX<PropertyCashierPaymentDO> {

    default PageResult<PropertyCashierPaymentDO> selectPage(Long recordId, String payChannel, Integer payStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyCashierPaymentDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyCashierPaymentDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyCashierPaymentDO>()
                .eqIfPresent(PropertyCashierPaymentDO::getRecordId, recordId)
                .eqIfPresent(PropertyCashierPaymentDO::getPayChannel, payChannel)
                .eqIfPresent(PropertyCashierPaymentDO::getPayStatus, payStatus)
                .
        orderByDesc(PropertyCashierPaymentDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyCashierPaymentDO> selectListByRecordId(Long recordId) {
        return selectList(PropertyCashierPaymentDO::getRecordId, recordId);
    }

    default List<PropertyCashierPaymentDO> selectListByPayStatus(Integer payStatus) {
        return selectList(PropertyCashierPaymentDO::getPayStatus, payStatus);
    }

    default List<PropertyCashierPaymentDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyCashierPaymentDO>()
                .orderByDesc(PropertyCashierPaymentDO::getId));
    }
}
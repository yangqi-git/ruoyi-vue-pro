package cn.iocoder.yudao.module.property.dal.mysql.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyBillDetailDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyBillDetailMapper extends BaseMapperX<PropertyBillDetailDO> {

    default PageResult<PropertyBillDetailDO> selectPage(Long billId, Long itemId, Integer payStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyBillDetailDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyBillDetailDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyBillDetailDO>()
                .eqIfPresent(PropertyBillDetailDO::getBillId, billId)
                .eqIfPresent(PropertyBillDetailDO::getItemId, itemId)
                .eqIfPresent(PropertyBillDetailDO::getPayStatus, payStatus)
                .
        orderByDesc(PropertyBillDetailDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyBillDetailDO> selectListByBillId(Long billId) {
        return selectList(PropertyBillDetailDO::getBillId, billId);
    }

    default List<PropertyBillDetailDO> selectListByItemId(Long itemId) {
        return selectList(PropertyBillDetailDO::getItemId, itemId);
    }

    default List<PropertyBillDetailDO> selectListByPayStatus(Integer payStatus) {
        return selectList(PropertyBillDetailDO::getPayStatus, payStatus);
    }

    default List<PropertyBillDetailDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyBillDetailDO>()
                .orderByDesc(PropertyBillDetailDO::getId));
    }
}
package cn.iocoder.yudao.module.property.dal.mysql.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundDetailDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyRefundDetailMapper extends BaseMapperX<PropertyRefundDetailDO> {

    default PageResult<PropertyRefundDetailDO> selectPage(Long refundId, Long billDetailId, Integer refundStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyRefundDetailDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyRefundDetailDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyRefundDetailDO>()
                .eqIfPresent(PropertyRefundDetailDO::getRefundId, refundId)
                .eqIfPresent(PropertyRefundDetailDO::getBillDetailId, billDetailId)
                .eqIfPresent(PropertyRefundDetailDO::getRefundStatus, refundStatus)
                .
        orderByDesc(PropertyRefundDetailDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default List<PropertyRefundDetailDO> selectListByRefundId(Long refundId) {
        return selectList(PropertyRefundDetailDO::getRefundId, refundId);
    }

    default List<PropertyRefundDetailDO> selectListByBillDetailId(Long billDetailId) {
        return selectList(PropertyRefundDetailDO::getBillDetailId, billDetailId);
    }

    default List<PropertyRefundDetailDO> selectListByRefundStatus(Integer refundStatus) {
        return selectList(PropertyRefundDetailDO::getRefundStatus, refundStatus);
    }

    default List<PropertyRefundDetailDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyRefundDetailDO>()
                .orderByDesc(PropertyRefundDetailDO::getId));
    }
}
package cn.iocoder.yudao.module.property.dal.mysql.refund;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.refund.PropertyRefundRecordDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyRefundRecordMapper extends BaseMapperX<PropertyRefundRecordDO> {

    default PageResult<PropertyRefundRecordDO> selectPage(Long projectId, String refundNo, Long cashierRecordId, Long billId, Long houseId, Integer refundType, Integer refundStatus, Integer approvalStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyRefundRecordDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyRefundRecordDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyRefundRecordDO>()
                .eqIfPresent(PropertyRefundRecordDO::getProjectId, projectId)
                .eqIfPresent(PropertyRefundRecordDO::getRefundNo, refundNo)
                .eqIfPresent(PropertyRefundRecordDO::getCashierRecordId, cashierRecordId)
                .eqIfPresent(PropertyRefundRecordDO::getBillId, billId)
                .eqIfPresent(PropertyRefundRecordDO::getHouseId, houseId)
                .eqIfPresent(PropertyRefundRecordDO::getRefundType, refundType)
                .eqIfPresent(PropertyRefundRecordDO::getRefundStatus, refundStatus)
                .eqIfPresent(PropertyRefundRecordDO::getApprovalStatus, approvalStatus)
                .
        orderByDesc(PropertyRefundRecordDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyRefundRecordDO selectByRefundNo(String refundNo) {
        return selectOne(PropertyRefundRecordDO::getRefundNo, refundNo);
    }

    default List<PropertyRefundRecordDO> selectListByCashierRecordId(Long cashierRecordId) {
        return selectList(PropertyRefundRecordDO::getCashierRecordId, cashierRecordId);
    }

    default List<PropertyRefundRecordDO> selectListByBillId(Long billId) {
        return selectList(PropertyRefundRecordDO::getBillId, billId);
    }

    default List<PropertyRefundRecordDO> selectListByHouseId(Long houseId) {
        return selectList(PropertyRefundRecordDO::getHouseId, houseId);
    }

    default List<PropertyRefundRecordDO> selectListByRefundStatus(Integer refundStatus) {
        return selectList(PropertyRefundRecordDO::getRefundStatus, refundStatus);
    }

    default List<PropertyRefundRecordDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyRefundRecordDO>()
                .orderByDesc(PropertyRefundRecordDO::getId));
    }
}

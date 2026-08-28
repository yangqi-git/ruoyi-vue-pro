package cn.iocoder.yudao.module.property.dal.mysql.cashier;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.cashier.PropertyCashierRecordDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyCashierRecordMapper extends BaseMapperX<PropertyCashierRecordDO> {

    default PageResult<PropertyCashierRecordDO> selectPage(Long projectId, String recordNo, Long billId, Long houseId, Long communityId, Integer payType, Integer payStatus, Integer recordStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyCashierRecordDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyCashierRecordDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyCashierRecordDO>()
                .eqIfPresent(PropertyCashierRecordDO::getProjectId, projectId)
                .eqIfPresent(PropertyCashierRecordDO::getRecordNo, recordNo)
                .eqIfPresent(PropertyCashierRecordDO::getBillId, billId)
                .eqIfPresent(PropertyCashierRecordDO::getHouseId, houseId)
                .eqIfPresent(PropertyCashierRecordDO::getCommunityId, communityId)
                .eqIfPresent(PropertyCashierRecordDO::getPayType, payType)
                .eqIfPresent(PropertyCashierRecordDO::getPayStatus, payStatus)
                .eqIfPresent(PropertyCashierRecordDO::getRecordStatus, recordStatus)
                .
        orderByDesc(PropertyCashierRecordDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyCashierRecordDO selectByRecordNo(String recordNo) {
        return selectOne(PropertyCashierRecordDO::getRecordNo, recordNo);
    }

    default List<PropertyCashierRecordDO> selectListByBillId(Long billId) {
        return selectList(PropertyCashierRecordDO::getBillId, billId);
    }

    default List<PropertyCashierRecordDO> selectListByHouseId(Long houseId) {
        return selectList(PropertyCashierRecordDO::getHouseId, houseId);
    }

    default List<PropertyCashierRecordDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyCashierRecordDO::getCommunityId, communityId);
    }

    default List<PropertyCashierRecordDO> selectListByPayStatus(Integer payStatus) {
        return selectList(PropertyCashierRecordDO::getPayStatus, payStatus);
    }

    default List<PropertyCashierRecordDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyCashierRecordDO>()
                .orderByDesc(PropertyCashierRecordDO::getId));
    }
}

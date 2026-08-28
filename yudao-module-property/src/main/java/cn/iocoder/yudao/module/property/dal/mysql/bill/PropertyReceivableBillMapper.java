package cn.iocoder.yudao.module.property.dal.mysql.bill;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.bill.PropertyReceivableBillDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyReceivableBillMapper extends BaseMapperX<PropertyReceivableBillDO> {

    default PageResult<PropertyReceivableBillDO> selectPage(String billNo, Long houseId, Long communityId, Long projectId, Integer billType, Integer payStatus, Integer billStatus, Integer pageNo, Integer pageSize) {
        Page<PropertyReceivableBillDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyReceivableBillDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyReceivableBillDO>()
                .eqIfPresent(PropertyReceivableBillDO::getBillNo, billNo)
                .eqIfPresent(PropertyReceivableBillDO::getHouseId, houseId)
                .eqIfPresent(PropertyReceivableBillDO::getCommunityId, communityId)
                .eqIfPresent(PropertyReceivableBillDO::getProjectId, projectId)
                .eqIfPresent(PropertyReceivableBillDO::getBillType, billType)
                .eqIfPresent(PropertyReceivableBillDO::getPayStatus, payStatus)
                .eqIfPresent(PropertyReceivableBillDO::getBillStatus, billStatus)
                .
        orderByDesc(PropertyReceivableBillDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyReceivableBillDO selectByBillNo(String billNo) {
        return selectOne(PropertyReceivableBillDO::getBillNo, billNo);
    }

    default List<PropertyReceivableBillDO> selectListByHouseId(Long houseId) {
        return selectList(PropertyReceivableBillDO::getHouseId, houseId);
    }

    default List<PropertyReceivableBillDO> selectListByCommunityId(Long communityId) {
        return selectList(PropertyReceivableBillDO::getCommunityId, communityId);
    }

    default List<PropertyReceivableBillDO> selectListByPayStatus(Integer payStatus) {
        return selectList(PropertyReceivableBillDO::getPayStatus, payStatus);
    }

    default List<PropertyReceivableBillDO> selectListByBillStatus(Integer billStatus) {
        return selectList(PropertyReceivableBillDO::getBillStatus, billStatus);
    }

    default List<PropertyReceivableBillDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyReceivableBillDO>()
                .orderByDesc(PropertyReceivableBillDO::getId));
    }
}
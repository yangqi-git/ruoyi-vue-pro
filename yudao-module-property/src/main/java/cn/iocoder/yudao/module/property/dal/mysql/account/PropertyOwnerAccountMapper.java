package cn.iocoder.yudao.module.property.dal.mysql.account;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.account.PropertyOwnerAccountDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyOwnerAccountMapper extends BaseMapperX<PropertyOwnerAccountDO> {

    default PageResult<PropertyOwnerAccountDO> selectPage(Long ownerId, Integer status) {
        Page<PropertyOwnerAccountDO> __page = new Page<>(1, 10);
IPage<PropertyOwnerAccountDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyOwnerAccountDO>()
                .eqIfPresent(PropertyOwnerAccountDO::getOwnerId, ownerId)
                .eqIfPresent(PropertyOwnerAccountDO::getStatus, status)
                .
        orderByDesc(PropertyOwnerAccountDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyOwnerAccountDO selectByOwnerId(Long ownerId) {
        return selectOne(PropertyOwnerAccountDO::getOwnerId, ownerId);
    }
}

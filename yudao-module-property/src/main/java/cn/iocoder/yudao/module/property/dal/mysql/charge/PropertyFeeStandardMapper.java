package cn.iocoder.yudao.module.property.dal.mysql.charge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.charge.PropertyFeeStandardDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyFeeStandardMapper extends BaseMapperX<PropertyFeeStandardDO> {

    default PageResult<PropertyFeeStandardDO> selectPage(Long projectId, String houseType, Integer status) {
        Page<PropertyFeeStandardDO> __page = new Page<>(1, 10);
IPage<PropertyFeeStandardDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyFeeStandardDO>()
                .eqIfPresent(PropertyFeeStandardDO::getProjectId, projectId)
                .eqIfPresent(PropertyFeeStandardDO::getHouseType, houseType)
                .eqIfPresent(PropertyFeeStandardDO::getStatus, status)
                .
        orderByDesc(PropertyFeeStandardDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

package cn.iocoder.yudao.module.property.dal.mysql.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyIncomeDO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyIncomeMapper extends BaseMapperX<PropertyIncomeDO> {

    default PageResult<PropertyIncomeDO> selectPage(String incomeType, String payChannel, Long payerId,
                                                     LocalDate beginDate, LocalDate endDate, Integer confirmed) {
        Page<PropertyIncomeDO> __page = new Page<>(1, 10);
IPage<PropertyIncomeDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyIncomeDO>()
                .eqIfPresent(PropertyIncomeDO::getIncomeType, incomeType)
                .eqIfPresent(PropertyIncomeDO::getPayChannel, payChannel)
                .eqIfPresent(PropertyIncomeDO::getPayerId, payerId)
                .betweenIfPresent(PropertyIncomeDO::getIncomeDate, beginDate, endDate)
                .eqIfPresent(PropertyIncomeDO::getConfirmed, confirmed)
                .
        orderByDesc(PropertyIncomeDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

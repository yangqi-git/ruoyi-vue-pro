package cn.iocoder.yudao.module.property.dal.mysql.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyExpenseDO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyExpenseMapper extends BaseMapperX<PropertyExpenseDO> {

    default PageResult<PropertyExpenseDO> selectPage(String expenseType, String expenseCategory, Integer approvalStatus,
                                                      LocalDate beginDate, LocalDate endDate) {
        Page<PropertyExpenseDO> __page = new Page<>(1, 10);
IPage<PropertyExpenseDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyExpenseDO>()
                .eqIfPresent(PropertyExpenseDO::getExpenseType, expenseType)
                .eqIfPresent(PropertyExpenseDO::getExpenseCategory, expenseCategory)
                .eqIfPresent(PropertyExpenseDO::getApprovalStatus, approvalStatus)
                .betweenIfPresent(PropertyExpenseDO::getExpenseDate, beginDate, endDate)
                .
        orderByDesc(PropertyExpenseDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

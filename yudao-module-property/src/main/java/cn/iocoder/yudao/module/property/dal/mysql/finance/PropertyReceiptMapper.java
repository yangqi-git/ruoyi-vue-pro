package cn.iocoder.yudao.module.property.dal.mysql.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyReceiptDO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyReceiptMapper extends BaseMapperX<PropertyReceiptDO> {

    default PageResult<PropertyReceiptDO> selectPage(String receiptType, Long payerId,
                                                      LocalDate beginDate, LocalDate endDate) {
        Page<PropertyReceiptDO> __page = new Page<>(1, 10);
IPage<PropertyReceiptDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyReceiptDO>()
                .eqIfPresent(PropertyReceiptDO::getReceiptType, receiptType)
                .eqIfPresent(PropertyReceiptDO::getPayerId, payerId)
                .betweenIfPresent(PropertyReceiptDO::getIssueDate, beginDate, endDate)
                .
        orderByDesc(PropertyReceiptDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

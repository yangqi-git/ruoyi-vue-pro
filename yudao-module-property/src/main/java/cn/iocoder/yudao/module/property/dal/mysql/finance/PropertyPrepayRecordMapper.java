package cn.iocoder.yudao.module.property.dal.mysql.finance;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.finance.PropertyPrepayRecordDO;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyPrepayRecordMapper extends BaseMapperX<PropertyPrepayRecordDO> {

    default PageResult<PropertyPrepayRecordDO> selectPage(Long accountId, Integer recordType,
                                                           LocalDateTime beginTime, LocalDateTime endTime) {
        Page<PropertyPrepayRecordDO> __page = new Page<>(1, 10);
IPage<PropertyPrepayRecordDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyPrepayRecordDO>()
                .eqIfPresent(PropertyPrepayRecordDO::getAccountId, accountId)
                .eqIfPresent(PropertyPrepayRecordDO::getRecordType, recordType)
                .betweenIfPresent(PropertyPrepayRecordDO::getTransactionTime, beginTime, endTime)
                .
        orderByDesc(PropertyPrepayRecordDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }
}

package cn.iocoder.yudao.module.property.dal.mysql.org;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.property.dal.dataobject.org.PropertyProjectDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
public interface PropertyProjectMapper extends BaseMapperX<PropertyProjectDO> {

    default PageResult<PropertyProjectDO> selectPage(String name, String code, Integer status, Integer pageNo, Integer pageSize) {
        Page<PropertyProjectDO> __page = new Page<>(pageNo, pageSize);
IPage<PropertyProjectDO> __result = selectPage(__page, new LambdaQueryWrapperX<PropertyProjectDO>()
                .likeIfPresent(PropertyProjectDO::getName, name)
                .eqIfPresent(PropertyProjectDO::getCode, code)
                .eqIfPresent(PropertyProjectDO::getStatus, status)
                .
        orderByDesc(PropertyProjectDO::getId));
return new PageResult<>(__result.getRecords(), __result.getTotal());
    }

    default PropertyProjectDO selectByCode(String code) {
        return selectOne(PropertyProjectDO::getCode, code);
    }

    default List<PropertyProjectDO> selectListByStatus(Integer status) {
        return selectList(PropertyProjectDO::getStatus, status);
    }

    default List<PropertyProjectDO> selectList() {
        return selectList(new LambdaQueryWrapperX<PropertyProjectDO>()
                .orderByDesc(PropertyProjectDO::getId));
    }
}
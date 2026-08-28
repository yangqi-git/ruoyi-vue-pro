package cn.iocoder.yudao.module.property.service.event;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderPageReqVO;
import cn.iocoder.yudao.module.property.controller.admin.event.vo.PropertyWorkOrderRespVO;

public interface PropertyWorkOrderService {
    PageResult<PropertyWorkOrderRespVO> getWorkOrderPage(PropertyWorkOrderPageReqVO reqVO);
}

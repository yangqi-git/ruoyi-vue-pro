package cn.iocoder.yudao.module.property.enums.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyEventStatusEnum {

    PENDING_CONFIRM(0, "待确认"),
    PENDING_DISPATCH(10, "待派单"),
    PENDING_ACCEPT(20, "待接单"),
    ACCEPTED(30, "已接单"),
    ARRIVED(40, "已到场"),
    PROCESSING(50, "处理中"),
    WAITING_COLLABORATION(60, "待协作"),
    PENDING_ACCEPTANCE(70, "待验收"),
    CLOSED(80, "已关闭"),
    REOPENED(90, "已复开"),
    MERGED(98, "已合并"),
    CANCELLED(99, "已取消");

    private final Integer status;
    private final String name;
}

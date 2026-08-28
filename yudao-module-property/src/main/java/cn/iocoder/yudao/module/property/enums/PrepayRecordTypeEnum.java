package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PrepayRecordTypeEnum implements ArrayValuable<Integer> {
    TOPUP(1, "充值"),
    CONSUME(2, "消费扣款"),
    REFUND(3, "退款");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PrepayRecordTypeEnum::getType).toArray(Integer[]::new);
    private final Integer type;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

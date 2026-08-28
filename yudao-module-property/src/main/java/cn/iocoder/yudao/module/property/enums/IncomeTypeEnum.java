package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IncomeTypeEnum implements ArrayValuable<Integer> {
    PROPERTY_FEE(1, "物业费"),
    PARKING_FEE(2, "停车费"),
    UTILITY_FEE(3, "水电费"),
    RENT_FEE(4, "租金"),
    PREPAY_TOPUP(5, "预存款充值"),
    OTHER(99, "其他收入");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IncomeTypeEnum::getType).toArray(Integer[]::new);
    private final Integer type;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

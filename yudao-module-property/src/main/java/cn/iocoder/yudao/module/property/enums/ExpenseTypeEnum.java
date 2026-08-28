package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExpenseTypeEnum implements ArrayValuable<Integer> {
    MAINTENANCE(1, "维修费"),
    CLEANING(2, "保洁费"),
    SECURITY(3, "安保费"),
    ADMINISTRATIVE(4, "行政支出"),
    UTILITY(5, "公区水电"),
    EQUIPMENT(6, "设备采购"),
    OTHER(99, "其他支出");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(ExpenseTypeEnum::getType).toArray(Integer[]::new);
    private final Integer type;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

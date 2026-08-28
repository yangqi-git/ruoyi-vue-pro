package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountStatusEnum implements ArrayValuable<Integer> {
    NORMAL(1, "正常"),
    ARREARS(2, "欠费"),
    FROZEN(3, "冻结"),
    CLOSED(9, "注销");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(AccountStatusEnum::getStatus).toArray(Integer[]::new);
    private final Integer status;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

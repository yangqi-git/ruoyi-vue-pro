package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FeePayStatusEnum implements ArrayValuable<Integer> {
    UNPAID(0, "未缴"),
    PARTIAL(1, "部分缴"),
    PAID(2, "已缴"),
    FREE(3, "免缴");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(FeePayStatusEnum::getStatus).toArray(Integer[]::new);
    private final Integer status;
    private final String name;

    @Override
    public Integer[] array() { return ARRAYS; }
}

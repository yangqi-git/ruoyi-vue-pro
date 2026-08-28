package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExpenseApprovalStatusEnum implements ArrayValuable<Integer> {
    PENDING(0, "待审批"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已驳回"),
    WITHDRAWN(3, "已撤回");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(ExpenseApprovalStatusEnum::getStatus).toArray(Integer[]::new);
    private final Integer status;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

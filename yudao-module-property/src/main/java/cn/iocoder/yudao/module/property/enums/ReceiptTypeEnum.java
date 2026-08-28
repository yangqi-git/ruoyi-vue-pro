package cn.iocoder.yudao.module.property.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReceiptTypeEnum implements ArrayValuable<Integer> {
    PAYMENT_RECEIPT(1, "收款收据"),
    INVOICE(2, "发票"),
    PREPAY_RECEIPT(3, "充值凭证"),
    REFUND_RECEIPT(4, "退款凭证");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(ReceiptTypeEnum::getType).toArray(Integer[]::new);
    private final Integer type;
    private final String name;

    @Override public Integer[] array() { return ARRAYS; }
}

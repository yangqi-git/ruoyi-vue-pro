package cn.iocoder.yudao.module.property.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PayChannelEnum  {
    CASH(1, "线下现金"), BANK(2, "银行卡"),
    WECHAT(3, "微信支付"), ALIPAY(4, "支付宝"),
    PREPAY(5, "预存款");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PayChannelEnum::getChannel).toArray();
    private final Integer channel; private final String name;

}

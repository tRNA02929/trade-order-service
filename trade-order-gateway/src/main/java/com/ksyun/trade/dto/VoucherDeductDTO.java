package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
public class VoucherDeductDTO implements Serializable {

    private Integer orderId;
    private String voucherNo;
    private BigDecimal amount;
    private BigDecimal beforeDeductAmount;
    private BigDecimal afterDeductAmount;

    @Override
    public String toString() {
        String result = "orderId=" + orderId + "&" +
                "voucherNo=" + voucherNo + '&' +
                "amount=" + amount;
        if (Objects.nonNull(beforeDeductAmount) && Objects.nonNull(afterDeductAmount))
            result += '&' + "beforeDeductAmount=" + beforeDeductAmount + '&' +
                    "afterDeductAmount=" + afterDeductAmount;
        return result;
    }
}

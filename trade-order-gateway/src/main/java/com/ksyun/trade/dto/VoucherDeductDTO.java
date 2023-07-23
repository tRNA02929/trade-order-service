package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class VoucherDeductDTO implements Serializable {

    private Integer orderId;
    private String voucherNo;
    private BigDecimal amount;
    private BigDecimal beforeDeductAmount;
    private BigDecimal afterDeductAmount;

    @Override
    public String toString() {
        return "orderId=" + orderId + "&" +
                "voucherNo=" + voucherNo + '&' +
                "amount=" + amount + '&' +
                "beforeDeductAmount=" + beforeDeductAmount + '&' +
                "afterDeductAmount=" + afterDeductAmount;
    }
}

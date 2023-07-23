package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int user_id;

    private int region_id;

    private BigDecimal price_value;
}

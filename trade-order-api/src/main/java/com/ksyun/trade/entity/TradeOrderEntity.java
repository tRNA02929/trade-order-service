package com.ksyun.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int user_id;

    private int region_id;

    private BigDecimal price_value;
}

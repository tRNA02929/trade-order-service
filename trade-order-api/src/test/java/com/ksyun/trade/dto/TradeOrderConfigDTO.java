package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeOrderConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int user_id;

    private int region_id;

    private double price_value;
}

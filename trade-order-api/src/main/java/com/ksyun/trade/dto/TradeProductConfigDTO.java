package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeProductConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String item_no;

    private String item_name;

    private String unit;

    private Integer value;

}

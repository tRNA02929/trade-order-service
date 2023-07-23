package com.ksyun.trade.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {
    private String username;

    private String email;

    private String phone;

    private String address;
}

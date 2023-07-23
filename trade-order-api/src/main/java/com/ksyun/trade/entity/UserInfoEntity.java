package com.ksyun.trade.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoEntity implements Serializable {
    private String username;

    private String email;

    private String phone;

    private String address;
}

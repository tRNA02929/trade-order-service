package com.ksyun.trade.service;

import com.ksyun.trade.dto.VoucherDeductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class GatewayService {
    @Autowired
    private HttpServletResponse response;

    @Value("${actions}")
    private String[] actions;

    private boolean round = false;

    public String round() {
        String action;
        if (round) {
            round = false;
            action = actions[0];
        } else {
            round = true;
            action = actions[1];
        }
        action = "http://localhost:8089";
        return action;
    }

    public Object queryOrderInfo(Object param) throws IOException {
        // 1. 模拟路由 (负载均衡) 获取接口
        // 2. 请求转发
        response.sendRedirect(round() + "/online/trade_order/" + param);
        return null;
    }

    public Object queryRegionName(Object param) throws IOException {
        // 1. 模拟路由 (负载均衡) 获取接口
        // 2. 请求转发
        response.sendRedirect(round() + "/online/trade_order/region/" + param);
        return null;
    }

    public Object deduct(VoucherDeductDTO param) throws IOException {
        response.sendRedirect(round() + "/online/trade_order/deduct?" + param);
        return null;
    }

    public Object loadLalancing(Object param) throws IOException {
        // 1. 模拟路由 (负载均衡) 获取接口
        // 2. 请求转发
        response.sendRedirect("http://localhost:8089/online/trade_order/" + param);
        return null;
    }


}

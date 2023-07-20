package com.ksyun.trade.service;

import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ksyun.common.util.mapper.JacksonMapper.defaultMapper;

@Service
public class GatewayService {
    @Autowired
    private HttpServletResponse response;

//    @Value("${actions}")
//    private String[] actions;

    public Object loadLalancing(Object param) throws IOException {
        // 1. 模拟路由 (负载均衡) 获取接口
        // 2. 请求转发
        response.sendRedirect("http://localhost:8089" + "/online/trade_order/" + param);
        return null;
    }


}

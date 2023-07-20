package com.ksyun.trade.service;

import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ksyun.common.util.mapper.JacksonMapper.defaultMapper;

@Service
public class GatewayService {

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    public Object loadLalancing(Object param) {
        // 1. 模拟路由 (负载均衡) 获取接口
        // 2. 请求转发

        RestTemplate restTemplate = new RestTemplate();
        RestResult<HashMap<String, Object>> restResult = restTemplate.getForObject("http://localhost:8089/online/trade_order/{id}", RestResult.class, param);
        restResult.setRequestId("");
        HashMap<String, Object> map = restResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",param);
        data.put("priceValue",9406.5013);
        data.put("user",map);
        restResult.setData(data);

        return restResult;
    }


}

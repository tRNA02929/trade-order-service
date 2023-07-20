package com.ksyun.trade.service;

import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@Slf4j
public class TradeOrderService {


    @Autowired
    private HttpServletRequest request;


    public RestResult query(Integer id) {
        //TODO
        RestTemplate restTemplate = new RestTemplate();

        RestResult<HashMap<String, Object>> restResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, id);
        log.info("RequestId: {}", JacksonMapper.defaultMapper().toJson(restResult));
        restResult.setRequestId("test12345678");
        HashMap<String, Object> map = restResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("priceValue", 9406.5013);
        data.put("user", map);
        restResult.setData(data);
        log.info("RequestId: {}", JacksonMapper.defaultMapper().toJson(restResult));
        return restResult;
    }
}
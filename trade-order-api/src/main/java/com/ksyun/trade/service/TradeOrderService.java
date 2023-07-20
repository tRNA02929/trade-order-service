package com.ksyun.trade.service;

import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TradeOrderService {

    public Object query(Integer id) {
        //TODO
        RestTemplate restTemplate = new RestTemplate();

//        String str = ;
        RestResult restResult = restTemplate.getForObject("http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, id);//JacksonMapper.defaultMapper().fromJson(str, RestResult.class);


        return restResult.getData();
    }

}
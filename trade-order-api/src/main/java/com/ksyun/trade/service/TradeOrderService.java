package com.ksyun.trade.service;

import com.ksyun.trade.dto.TradeOrderDTO;
import com.ksyun.trade.dto.TradeProductConfigDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.util.TradeSelectUtil;
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
        TradeOrderDTO tocd = TradeSelectUtil.selectOrderById(id);
        int userId = tocd.getUser_id();
        TradeProductConfigDTO[] tpcd = TradeSelectUtil.selectProductById(id);

        RestTemplate restTemplate = new RestTemplate();
        RestResult<HashMap<String, Object>> restResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, userId);

        HashMap<String, Object> map = restResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("priceValue", tocd.getPrice_value());
        data.put("id", userId);
        data.put("user", map);
        data.put("configs", tpcd);
        restResult.setData(data);
        return restResult;
    }
}
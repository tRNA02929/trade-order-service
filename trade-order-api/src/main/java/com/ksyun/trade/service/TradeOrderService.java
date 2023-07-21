package com.ksyun.trade.service;

import com.google.common.collect.Maps;
import com.ksyun.trade.dto.TradeOrderDTO;
import com.ksyun.trade.dto.TradeProductConfigDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.util.TradeSelectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TradeOrderService {


    @Autowired
    private HttpServletRequest request;


    public RestResult query(Integer id) {
        //TODO
        TradeOrderDTO tocd = TradeSelectUtil.selectOrderById(id);
        TradeProductConfigDTO[] tpcd = TradeSelectUtil.selectProductById(id);
        int userId = tocd.getUser_id();
        int regionId = tocd.getRegion_id();

        RestTemplate restTemplate = new RestTemplate();
        RestResult<HashMap<String, Object>> userResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, userId);
        RestResult<ArrayList<HashMap<String, Object>>> redionResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/region/list", RestResult.class);


        HashMap<String, Object> map = userResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("priceValue", tocd.getPrice_value());
        data.put("user", map);

        for (HashMap<String, Object> region : redionResult.getData()) {
            if (regionId == (int) region.get("id")) {
                map = new HashMap<>();
                map.put("code", region.get("code"));
                map.put("name", region.get("name"));
                data.put("region", map);
                break;
            }
        }

        data.put("configs", tpcd);

        RestResult restResult = new RestResult();
        restResult.setData(data);
        return restResult;
    }
}
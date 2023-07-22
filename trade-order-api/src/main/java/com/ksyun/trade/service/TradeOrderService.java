package com.ksyun.trade.service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.ksyun.trade.dto.TradeOrderDTO;
import com.ksyun.trade.dto.TradeProductConfigDTO;
import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.util.TradeSelectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
public class TradeOrderService {

    @Autowired
    private HttpServletRequest request;

    private static HashMap<Integer, HashMap<String, Object>> regionMap = null;

    private void initialRegion() {
        RestTemplate restTemplate = new RestTemplate();
        RestResult<ArrayList<HashMap<String, Object>>> redionResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/region/list", RestResult.class);
        regionMap = new HashMap<>();

        for (HashMap<String, Object> region : redionResult.getData()) {
            Integer t = (Integer) region.get("id");
            region.remove("id");
            region.remove("status");
            regionMap.put(t, region);
        }
    }

    public RestResult queryOrderInfo(Integer id) {
        //TODO
        TradeOrderDTO tocd = TradeSelectUtil.selectOrderById(id);
        TradeProductConfigDTO[] tpcd = TradeSelectUtil.selectProductById(id);
        int userId = tocd.getUser_id();
        int regionId = tocd.getRegion_id();

        RestTemplate restTemplate = new RestTemplate();
        RestResult<HashMap<String, Object>> userResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, userId);
        if (regionMap == null) {
            initialRegion();
        }

        Map<String, Object> map = userResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("priceValue", tocd.getPrice_value());
        data.put("user", map);
        data.put("region", regionMap.get(regionId));

        data.put("configs", tpcd);

        RestResult restResult = new RestResult();
        restResult.setCode(200);
        restResult.setMsg("ok");
        restResult.setData(data);
        return restResult;
    }

    public RestResult queryRegionName(Integer id) {
        if (regionMap == null) {
            initialRegion();
        }
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMsg("ok");
        restResult.setData(regionMap.get(id).get("name"));
        return restResult;
    }

    public RestResult deduct(VoucherDeductDTO param) {
        //TODO
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMsg("ok");
        return restResult;
    }
}
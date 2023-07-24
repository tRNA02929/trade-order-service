package com.ksyun.trade.service;

import com.ksyun.trade.dto.TradeSelectDTO;
import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.dto.VoucherUpdateDTO;
import com.ksyun.trade.entity.TradeOrderEntity;
import com.ksyun.trade.entity.TradeProductConfigEntity;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.utils.UserCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class TradeOrderService {

    @Autowired
    private HttpServletRequest request;
    private static HashMap<Integer, HashMap<String, Object>> regionMap = null;

    // 初始化一次性获取所有地区信息
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

    // 获取用户信息并缓存
    private void putUserCache(int userId) {
        RestTemplate restTemplate = new RestTemplate();
        RestResult<Map<String, String>> userResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, userId);
        Map<String, String> map = userResult.getData();
        map.remove("id");
        UserCacheUtil.putUserInfo(userId, userResult.getData());
    }

    // 获取订单信息
    public RestResult queryOrderInfo(Integer id, String REFERER, String REQUEST_ID) {
        TradeOrderEntity tocd = TradeSelectDTO.selectOrderById(id);
        TradeProductConfigEntity[] tpcd = TradeSelectDTO.selectProductById(id);
        int userId = tocd.getUser_id();
        int regionId = tocd.getRegion_id();

        if (UserCacheUtil.getUserInfo(userId) == null) {
            putUserCache(userId);
        }
        if (regionMap == null) {
            initialRegion();
        }
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        String upstream = request.getHeader("REFERER");
        upstream = Objects.toString(upstream, "").split("online")[0];
        data.put("upstream", REFERER);
        data.put("id", id);
        data.put("priceValue", tocd.getPrice_value());
        data.put("user", UserCacheUtil.getUserInfo(userId));
        data.put("region", regionMap.get(regionId));
        data.put("configs", tpcd);

        RestResult restResult = new RestResult();
        restResult.setCode(200);
        restResult.setMsg("ok");
        if (!(REQUEST_ID == null || REQUEST_ID.equals("") || REQUEST_ID.equals("null"))) {
            restResult.setRequestId(REQUEST_ID);
        }
        restResult.setData(data);
        log.info("X-KSY-REQUEST-ID:{}", REQUEST_ID);
        return restResult;
    }

    // 获取地区名称
    public RestResult queryRegionName(Integer id, String REQUEST_ID) {
        if (regionMap == null) {
            initialRegion();
        }
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMsg("ok");
        restResult.setData(regionMap.get(id).get("name"));

        if (!(REQUEST_ID == null || REQUEST_ID.equals("") || REQUEST_ID.equals("null"))) {
            restResult.setRequestId(REQUEST_ID);
        }
        log.info("X-KSY-REQUEST-ID:{}", REQUEST_ID);
        return restResult;
    }

    // 扣减代金券
    public RestResult deduct(VoucherDeductDTO param, String REQUEST_ID) {
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(200);
        restResult.setMsg("ok");

        VoucherUpdateDTO.insertVoucher(param);

//        restResult.setData(param);

        if (!(REQUEST_ID == null || REQUEST_ID.equals("") || REQUEST_ID.equals("null"))) {
            restResult.setRequestId(REQUEST_ID);
        }
        log.info("X-KSY-REQUEST-ID:{}", REQUEST_ID);
        return restResult;
    }
}
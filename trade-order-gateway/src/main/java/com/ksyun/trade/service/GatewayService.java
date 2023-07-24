package com.ksyun.trade.service;

import com.ksyun.trade.dto.StringRedisUpstream;
import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class GatewayService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${actions}")
    private String[] actions;

    private boolean round = false;

    public String round() {
        // 1. 模拟路由 (负载均衡) 获取接口
        String action;
        if (round) {
            round = false;
            action = actions[0];
        } else {
            round = true;
            action = actions[1];
        }
        // 本地测试
//        action = "http://localhost:8089";
        return action;
    }

    public Object queryOrderInfo(Object param) throws IOException {
        String action = round();
        response.sendRedirect(action + "/online/trade_order/" + param +
                "?REFERER=" + action +
                "&REQUEST_ID=" + request.getHeader("X-KSY-REQUEST-ID"));
        return null;
    }

    public Object queryRegionName(Object param) throws IOException {
        String action = round();
        response.sendRedirect(action + "/online/trade_order/region/" + param +
                "?REQUEST_ID=" + request.getHeader("X-KSY-REQUEST-ID"));
        return null;
    }

    public Object deduct(VoucherDeductDTO param) throws IOException {
        response.sendRedirect(round() + "/online/trade_order/deduct?" + param
                + "&REQUEST_ID=" + request.getHeader("X-KSY-REQUEST-ID"));
        return null;
    }

    public Object listUpstreamInfo() {
        RestResult result = new RestResult<>();
        if (StringRedisUpstream.listUpstreamInfo(stringRedisTemplate)) {
            result.setCode(200);
            result.setMsg("ok");
            result.setData(actions);
        } else {
            result.setCode(429);
            result.setMsg("对不起, 系统压力过大, 请稍后再试!");
        }
        return result;
    }

}

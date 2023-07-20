package com.ksyun.trade.service;

import com.ksyun.common.util.mapper.JacksonMapper;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Service
@Slf4j
public class TradeOrderService {


    @Autowired
    private HttpServletRequest request;


    public RestResult query(Integer id) throws ClassNotFoundException, SQLException {
        //TODO
        RestTemplate restTemplate = new RestTemplate();
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ksyun?useUnicode=true&haracterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT", "root", "lb123456");
//        Connection con = DriverManager.getConnection("jdbc:mysql://campus-dev.mysql.ksyun.com:63265/test_trade?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&allowMultiQueries=true", "test", "test123");
        String sql = "select user_id from ksc_trade_order where id = " + id;
        ResultSet set = con.prepareStatement(sql).executeQuery();
        int userId = set.getInt("user_id");

        Class.forName("com.mysql.cj.jdbc.Driver");

        RestResult<HashMap<String, Object>> restResult = restTemplate.getForObject(
                "http://campus.meta.ksyun.com:8090/online/user/{id}", RestResult.class, id);
        log.info("RequestId: {}", JacksonMapper.defaultMapper().toJson(restResult));
        restResult.setRequestId("test12345678");
        HashMap<String, Object> map = restResult.getData();
        map.remove("id");
        HashMap<String, Object> data = new HashMap<>();
        data.put("priceValue", 9406.5013);
        data.put("id", userId);
        data.put("user", map);
        restResult.setData(data);
        log.info("RequestId: {}", JacksonMapper.defaultMapper().toJson(restResult));
        return restResult;
    }
}
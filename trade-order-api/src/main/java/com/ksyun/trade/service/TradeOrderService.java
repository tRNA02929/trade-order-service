package com.ksyun.trade.service;

import com.ksyun.trade.dto.TradeOrderConfigDTO;
import com.ksyun.trade.dto.TradeProductConfigDTO;
import com.ksyun.trade.mapper.TradeMapper;
import com.ksyun.trade.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;

@Service
@Slf4j
public class TradeOrderService {


    @Autowired
    private HttpServletRequest request;


    public RestResult query(Integer id) throws Exception {
        //TODO
        RestTemplate restTemplate = new RestTemplate();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TradeMapper tradeMapper = sqlSession.getMapper(TradeMapper.class);
        TradeOrderConfigDTO tocd = tradeMapper.selectUserById(id);
        int userId = tocd.getUser_id();

        TradeProductConfigDTO[] tpcd = tradeMapper.selectProductById(id);

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
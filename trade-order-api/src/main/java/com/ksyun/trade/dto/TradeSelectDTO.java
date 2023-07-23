package com.ksyun.trade.dto;

import com.ksyun.trade.mapper.TradeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public final class TradeSelectDTO {
    private static TradeMapper tradeMapper;

    static {
        String resource = "mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            tradeMapper = sqlSession.getMapper(TradeMapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TradeOrderDTO selectOrderById(int id) {
        return tradeMapper.selectOrderById(id);
    }

    public static TradeProductConfigDTO[] selectProductById(int id) {
        return tradeMapper.selectProductById(id);
    }
}

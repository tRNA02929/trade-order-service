package com.ksyun.trade;

import com.ksyun.trade.mapper.TradeOrderMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

/**
 * 基础测试类.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class BaseSpringAllTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    long s = 0;
    long e = 0;


    public static void main(String[] args) throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ksyun?useUnicode=true&haracterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT", "root", "lb123456");
////        Connection con = DriverManager.getConnection("jdbc:mysql://campus-dev.mysql.ksyun.com:63265/test_trade?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&allowMultiQueries=true", "test", "test123");
//        String sql = "select user_id from ksc_trade_order where id = 10";
//        ResultSet set = con.prepareStatement(sql).executeQuery();
//        set.next();
//        int userId = set.getInt("user_id");
//        System.out.println(userId);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TradeOrderMapper tradeOrderMapper = sqlSession.getMapper(TradeOrderMapper.class);
//        int id = userMapper.selectUserById(10);
        System.out.println(tradeOrderMapper.selectUserById(10));

        System.out.println("ok");
    }

    @Before
    public void before() {
        s = System.currentTimeMillis();
    }

    @After
    public void after() {
        e = System.currentTimeMillis();
        logger.info("耗时:{}秒", (e - s) / 1000);
    }

    @Test
    public void ping() {
        logger.info("ok");
    }
}

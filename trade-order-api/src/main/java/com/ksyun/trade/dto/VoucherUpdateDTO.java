package com.ksyun.trade.dto;

import com.ksyun.trade.entity.TradeOrderEntity;
import com.ksyun.trade.mapper.VoucherMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public final class VoucherUpdateDTO {
    private static VoucherMapper voucherMapper;

    private static SqlSession sqlSession;

    static {
        String resource = "mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            voucherMapper = sqlSession.getMapper(VoucherMapper.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static VoucherDeductDTO selectVoucherByOrderId(int id) {
        return voucherMapper.selectVoucherByOrderId(id);
    }

    public static void insertVoucher(VoucherDeductDTO voucherDeductDTO) {
        int orderId = voucherDeductDTO.getOrderId();
        VoucherDeductDTO voucher = selectVoucherByOrderId(orderId);
        BigDecimal before, after;
        if (voucher == null) {
            TradeOrderEntity tocd = TradeSelectDTO.selectOrderById(orderId);
            before = tocd.getPrice_value();
        } else {
            before = voucher.getAfterDeductAmount();
        }
        voucherDeductDTO.setBeforeDeductAmount(before);
        after = before.subtract(voucherDeductDTO.getAmount());
        if (after.compareTo(BigDecimal.ZERO) < 0) {
            after = BigDecimal.ZERO;
        }
        voucherDeductDTO.setAfterDeductAmount(after);
        voucherMapper.insertVoucher(voucherDeductDTO);
        sqlSession.commit();
    }
}

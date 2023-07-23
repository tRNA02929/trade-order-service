package com.ksyun.trade.mapper;

import com.ksyun.trade.entity.TradeOrderEntity;
import com.ksyun.trade.entity.TradeProductConfigEntity;
import com.ksyun.trade.dto.VoucherDeductDTO;
import org.apache.ibatis.annotations.Param;

public interface TradeMapper {

//    @Select("select user_id,region_id,price_value from ksc_trade_order where id = #{id}")
    public TradeOrderEntity selectOrderById(@Param("id")int id);
    public TradeProductConfigEntity[] selectProductById(@Param("id")int id);
}

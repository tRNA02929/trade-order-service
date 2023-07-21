package com.ksyun.trade.mapper;

import com.ksyun.trade.dto.TradeOrderDTO;
import com.ksyun.trade.dto.TradeProductConfigDTO;
import org.apache.ibatis.annotations.Param;

public interface TradeMapper {

//    @Select("select user_id,region_id,price_value from ksc_trade_order where id = #{id}")
    public TradeOrderDTO selectOrderById(@Param("id")int id);
    public TradeProductConfigDTO[] selectProductById(@Param("id")int id);

}

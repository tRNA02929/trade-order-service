package com.ksyun.trade.mapper;

import com.ksyun.trade.dto.TradeOrderConfigDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select user_id,region_id,price_value from ksc_trade_order where id = #{id}")
    public TradeOrderConfigDTO selectUserById(@Param("id")int id);

}

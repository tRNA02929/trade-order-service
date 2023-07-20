package com.ksyun.trade.mapper;

import com.ksyun.trade.dto.TradeProductConfigDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TradeProductMapper {
    @Select("select item_no,item_name,unit,value from ksc_trade_product_config where id = #{id}")
    public TradeProductConfigDTO[] selectProductById(@Param("id")int id);
}

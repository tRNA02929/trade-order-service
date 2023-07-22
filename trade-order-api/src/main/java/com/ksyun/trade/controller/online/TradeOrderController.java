package com.ksyun.trade.controller.online;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.rest.RestResult;
import com.ksyun.trade.service.TradeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/online/trade_order", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class TradeOrderController {
    @Autowired
    private TradeOrderService orderService;

    @RequestMapping("/{id}")
    public RestResult queryOrderInfo(@PathVariable("id") Integer id) {
        return orderService.queryOrderInfo(id);
    }

    @RequestMapping("/region/{id}")
    public RestResult queryRegionName(@PathVariable("id") Integer id) {
        return orderService.queryRegionName(id);
    }

    @RequestMapping("/deduct")
    public RestResult deduct(VoucherDeductDTO param) {
        return orderService.deduct(param);
    }
}

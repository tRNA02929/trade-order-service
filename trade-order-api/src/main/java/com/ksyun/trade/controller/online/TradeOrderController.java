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
    public RestResult queryOrderInfo(@PathVariable("id") Integer id, String REFERER, String REQUEST_ID) {
        return orderService.queryOrderInfo(id, REFERER, REQUEST_ID);
    }

    @RequestMapping("/region/{id}")
    public RestResult queryRegionName(@PathVariable("id") Integer id, String REQUEST_ID) {
        return orderService.queryRegionName(id, REQUEST_ID);
    }

    @RequestMapping("/deduct")
    public RestResult deduct(VoucherDeductDTO param, String REQUEST_ID) {
        return orderService.deduct(param, REQUEST_ID);
    }
}

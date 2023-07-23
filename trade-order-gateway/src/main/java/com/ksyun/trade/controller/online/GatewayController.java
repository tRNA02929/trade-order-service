package com.ksyun.trade.controller.online;

import com.ksyun.trade.dto.VoucherDeductDTO;
import com.ksyun.trade.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/online", produces = {MediaType.APPLICATION_JSON_VALUE})
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    /**
     * 查询订单详情 (GET)
     */
    @RequestMapping(value = "/queryOrderInfo", produces = "application/json")
    public Object queryOrderInfo(Integer id) throws IOException {
        return gatewayService.queryOrderInfo(id);
    }

    /**
     * 根据机房Id查询机房名称 (GET)
     */
    @RequestMapping(value = "/queryRegionName", produces = "application/json")
    public Object queryRegionName(Integer regionId) throws IOException {
        return gatewayService.queryRegionName(regionId);
    }

    /**
     * 订单优惠券抵扣 (POST json)
     */
    @RequestMapping(value = "/voucher/deduct", produces = "application/json")
    public Object deduct(VoucherDeductDTO param) throws IOException {
        return gatewayService.deduct(param);
    }

    /**
     * 基于Redis实现漏桶限流算法，并在API调用上体现
     */
    @RequestMapping(value = "/listUpstreamInfo", produces = "application/json")
    public Object listUpstreamInfo() {
        return null;
    }

}

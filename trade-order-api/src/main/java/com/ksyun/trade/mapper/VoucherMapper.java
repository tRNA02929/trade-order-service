package com.ksyun.trade.mapper;

import com.ksyun.trade.dto.VoucherDeductDTO;

public interface VoucherMapper {
    VoucherDeductDTO selectVoucherByOrderId(int order_id);

    void insertVoucher(VoucherDeductDTO voucherDeductDTO);

}

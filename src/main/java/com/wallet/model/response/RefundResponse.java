package com.wallet.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundResponse {

    private String refundTransactionId;

    private BigDecimal refundAmount;
    
    private String message;
}

package com.wallet.model;

import lombok.Data;

@Data
public class RefundResponse {

    private String refundTransactionId;

    private String refundAmount;
    
    private String message;
}

package com.wallet.model.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents response for refund api.
 */
@Data
public class RefundResponse {

    private String refundTransactionId;

    private BigDecimal refundAmount;
    
    private String message;
}

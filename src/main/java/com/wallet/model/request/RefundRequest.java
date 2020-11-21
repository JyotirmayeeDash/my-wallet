package com.wallet.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents the refund request.
 */
@Data
public class RefundRequest {

    private String debitTransactionId;

    @NotNull
    private BigDecimal transactionAmount;

}

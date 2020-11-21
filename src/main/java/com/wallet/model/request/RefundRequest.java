package com.wallet.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Valid
public class RefundRequest {

    private String debitTransactionId;

    @NotNull
    private BigDecimal transactionAmount;

}
package com.wallet.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Valid
public class RefundRequest {

    @NotBlank
    private String transactionId;

    @NotNull
    private BigDecimal transactionAmount;

}

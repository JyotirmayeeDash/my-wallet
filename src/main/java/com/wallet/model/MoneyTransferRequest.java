package com.wallet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {

    @NotBlank
    private String receiver;

    @NotNull
    private BigDecimal transferAmount;
}

package com.wallet.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {

    @NotBlank(message = "{error.recipient.required}")
    private String recipient;

    @NotNull
    private BigDecimal transferAmount;
}

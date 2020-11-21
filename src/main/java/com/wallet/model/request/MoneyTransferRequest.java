package com.wallet.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents the money transfer request.
 */
@Data
public class MoneyTransferRequest {

    @NotBlank(message = "{error.recipient.required}")
    private String recipient;

    @NotNull
    private BigDecimal transferAmount;
}

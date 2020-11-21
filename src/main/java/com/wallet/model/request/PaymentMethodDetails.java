package com.wallet.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Represents the payment method details.
 */
@Data
public class PaymentMethodDetails {

    private int paymentMethodId;

    private String paymentMethodType;

    private String cardHolderName;

    private String brand;

    private String bin;

    private String cvv;

    private String last4;

    private String expDate;

    @NotBlank(message = "{error.bankname.required}")
    private String bankName;

}

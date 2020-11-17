package com.wallet.model;

import lombok.Data;

@Data
public class PaymentMethodDetails {

    private int paymentMethodId;

    private String cardNumber;

    private String cvv;

    private String last4;
}

package com.wallet.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PAYMENT_METHOD_DETAILS")
public class PaymentMethod {

    @Id
    @Column(name = "PAYMENT_METHOD_DETAIL_ID")
    private int paymentMethodId;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "CVV")
    private String cvv;

    @Column(name = "LAST4")
    private String last4;
}

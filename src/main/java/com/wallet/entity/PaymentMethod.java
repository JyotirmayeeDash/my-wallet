package com.wallet.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PAYMENT_METHOD_DETAILS")
public class PaymentMethod {

    @Id
    @Column(name = "PAYMENT_METHOD_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int paymentMethodId;

    @Column(name = "PAYMENT_METHOD_TYPE")
    private String paymentMethodType;

    @Column(name = "CARD_HOLDER_NAME")
    private String cardHolderName;

    @Column(name = "CARD_BRAND")
    private String brand;

    @Column(name = "CARD_NUMBER")
    private String bin;

    @Column(name = "CVV")
    private String cvv;

    @Column(name = "LAST4")
    private String last4;

    @Column(name = "EXP_DATE")
    private String expDate;

    @Column(name = "BANK_NAME")
    private String bankName;

}

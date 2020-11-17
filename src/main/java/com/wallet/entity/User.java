package com.wallet.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "USER_DETAILS")
public class User {
    @Id
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String encodedPassword;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "ACCOUNT_BALANCE")
    private BigDecimal accountBalance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_METHOD_DETAIL_ID", referencedColumnName = "PAYMENT_METHOD_DETAIL_ID")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    @Column(name = "CREATION_TIME")
    private ZonedDateTime creationTime;
}

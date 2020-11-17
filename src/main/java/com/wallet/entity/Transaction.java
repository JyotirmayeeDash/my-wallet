package com.wallet.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "TRANSACTION_DETAILS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "transactionId",
scope = Transaction.class)
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "TRANSACTION_STATUS")
    private String transactionStatus;

    @Column(name = "TRANSACTION_AMOUNT")
    private BigDecimal transactionAmount;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "CREATION_TIME")
    private ZonedDateTime creationTime;
}

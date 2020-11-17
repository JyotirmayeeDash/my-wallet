package com.wallet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wallet.entity.Transaction;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Valid
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "transactionId",
        scope = TransactionDetails.class)
public class TransactionDetails {

    private String transactionId;

    private String transactionStatus;

    @NotNull
    private BigDecimal transactionAmount;

    private UserDetails userDetails;

    private ZonedDateTime creationTime;
}

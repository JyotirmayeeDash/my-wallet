package com.wallet.model.request;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

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

    private String transactionType;

    private UserDetails user;
    
    private ZonedDateTime creationTime;
}

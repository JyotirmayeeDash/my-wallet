package com.wallet.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Valid
public class UserDetails {

    private int userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    private String phoneNumber;

    private String accountNumber;

    private BigDecimal accountBalance;

    private PaymentMethodDetails paymentMethodDetails;

    private List<TransactionDetails> transactionDetails;

    private ZonedDateTime creationTime;
}

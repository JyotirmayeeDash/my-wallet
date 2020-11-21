package com.wallet.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class UserDetails {

    private int userId;

    @NotBlank(message = "{error.username.required}")
    private String userName;

    @NotBlank(message = "{error.password.required}")
    private String password;

    @NotBlank(message = "{error.emailid.required}")
    @Email
    private String emailId;

    @NotBlank(message = "{error.phonenumber.required}")
    private String phoneNumber;

    private BigDecimal walletBalance;

    @Valid
    private PaymentMethodDetails paymentMethod;

    private List<TransactionDetails> transactions;

    private ZonedDateTime creationTime;
}

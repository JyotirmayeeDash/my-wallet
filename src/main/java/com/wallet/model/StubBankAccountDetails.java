package com.wallet.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StubBankAccountDetails {

    private String userName;

    private String accountNumber;

    private BigDecimal accountBalance;
}

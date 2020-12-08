package com.wallet.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents request for bank api.
 */
@Data
public class StubBankAccountDetails {

    private String userName;

    private String accountNumber;

    private BigDecimal accountBalance;
}

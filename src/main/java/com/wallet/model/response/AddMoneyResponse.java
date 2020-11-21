package com.wallet.model.response;

import lombok.Data;

/**
 * Represents response for addmoney api.
 */
@Data
public class AddMoneyResponse {

    private String message;

    private String transactionId;
}

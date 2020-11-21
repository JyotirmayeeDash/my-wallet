package com.wallet.model.response;

import lombok.Data;

/**
 * Represents response for add money api.
 */
@Data
public class AddMoneyResponse {

    private String message;

    private String transactionId;
}

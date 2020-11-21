package com.wallet.model.response;

import lombok.Data;

/**
 * Represents response for status enquiry api.
 */
@Data
public class StatusEnquiryResponse {

    private String transactionId;

    private String status;

}

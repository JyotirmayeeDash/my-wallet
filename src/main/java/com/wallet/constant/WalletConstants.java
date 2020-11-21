package com.wallet.constant;

/**
 * This class holds all the constants to be used in the wallet transactions.
 */
public final class WalletConstants {
    public static final String ERROR_KEY = "errors";
    public static final String INVALID_VALUE_KEY = "invalidvalue";
    public static final String APPROVED_STATUS = "APPROVED";
    public static final String DECLINED_STATUS = "DECLINED";
    public static final double CHARGE = 0.2;
    public static final double COMMISSION = 0.05;
    public static final String ADD_MONEY_SUCCESS_MESSAGE = "Funds added successfully.";
    public static final String MONEY_TRANSFER_SUCCESS_MESSAGE = "Funds transferred successfully.";
    public static final String REFUND_SUCCESS_MESSAGE = "Money refunded successfully.";
    public static final String CREATE_USER_SUCCESS_MESSAGE = "User account created.";



    private WalletConstants () {
        //To prevent instantiation
    }


}

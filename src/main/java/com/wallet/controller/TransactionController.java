package com.wallet.controller;

import com.wallet.model.request.MoneyTransferRequest;
import com.wallet.model.request.RefundRequest;
import com.wallet.model.request.TransactionDetails;
import com.wallet.model.response.AddMoneyResponse;
import com.wallet.model.response.MoneyTransferResponse;
import com.wallet.model.response.RefundResponse;
import com.wallet.model.response.StatusEnquiryResponse;
import com.wallet.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This controller class performs all the wallet transactions.
 */
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * End point to add money to the wallet.
     *
     * @param transactionDetails transaction details
     * @param authentication authentication
     * @return add money response
     */
    @PostMapping("/money")
    public AddMoneyResponse addMoney(@Valid @RequestBody TransactionDetails transactionDetails, Authentication authentication) {

        log.info("Received request to add money to the wallet.");
        return transactionService.addMoney(transactionDetails, authentication.getName());
    }

    /**
     * End point to transfer money from current wallet to another wallet.
     *
     * @param moneyTransferRequest money transfer request
     * @param authentication authentication
     * @return money transfer response
     */
    @PostMapping("/transfer")
    public MoneyTransferResponse transferMoney(@Valid @RequestBody MoneyTransferRequest moneyTransferRequest, Authentication authentication) {

        log.info("Received request to transfer money from wallet to "+moneyTransferRequest.getRecipient());
        return transactionService.transferMoney(moneyTransferRequest, authentication.getName());
    }

    /**
     * Endpoint for status enquiry of a transaction.
     *
     * @param transactionId transaction id
     * @return status enquiry response
     */
    @GetMapping("/status/{transactionId}")
    public StatusEnquiryResponse getTransactionStatus(@Valid @PathVariable String transactionId) {

        log.info("Received request to fetch transaction status for transaction id : "+transactionId);
        return transactionService.getTransactionStatus(transactionId);
    }

    /**
     * Endpoint to get all the transaction details of a user.
     *
     * @param authentication authentication
     * @return transactionDetails list
     */
    @GetMapping("/usertransactiondetails")
    public List<TransactionDetails> getTransactionByUserName(Authentication authentication) {

        log.info("Received request to fetch all the transactions for user : "+authentication.getName());
        return transactionService.getTransactions(authentication.getName());
    }

    /**
     * Endpoint to process refund.
     *
     * @param refundRequest refund request
     * @param authentication authentication
     * @return refund response
     */
    @PostMapping("/refund")
    public RefundResponse refundTransaction(@Valid @RequestBody RefundRequest refundRequest, Authentication authentication) {

        log.info("Received request to refund the transactions having transactionId : "+refundRequest.getDebitTransactionId());
        return transactionService.refundTransaction(refundRequest, authentication.getName());
    }

}

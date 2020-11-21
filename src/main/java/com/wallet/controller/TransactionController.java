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

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/money")
    public AddMoneyResponse addMoney(@Valid @RequestBody TransactionDetails transactionDetails, Authentication authentication) {

        log.info("Received request to add money to the wallet.");
        return transactionService.addMoney(transactionDetails, authentication.getName());
    }

    @PostMapping("/transfer")
    public MoneyTransferResponse transferMoney(@Valid @RequestBody MoneyTransferRequest moneyTransferRequest, Authentication authentication) {

        log.info("Received request to transfer money from wallet to "+moneyTransferRequest.getRecipient());
        return transactionService.transferMoney(moneyTransferRequest, authentication.getName());
    }

    @GetMapping("/status/{transactionId}")
    public StatusEnquiryResponse getTransactionStatus(@Valid @PathVariable String transactionId) {

        log.info("Received request to fetch transaction status for transaction id : "+transactionId);
        return transactionService.getTransactionStatus(transactionId);
    }

    @GetMapping("/{userName}")
    public List<TransactionDetails> getTransactionByUserName(@Valid @PathVariable String userName) {

        log.info("Received request to fetch all the transactions for user : "+userName);
        return transactionService.getTransactions(userName);
    }

    @PostMapping("/refund")
    public RefundResponse refundTransaction(@Valid @RequestBody RefundRequest refundRequest, Authentication authentication) {

        log.info("Received request to refund the transactions having transactionId : "+refundRequest.getDebitTransactionId());
        return transactionService.refundTransaction(refundRequest, authentication.getName());
    }

}

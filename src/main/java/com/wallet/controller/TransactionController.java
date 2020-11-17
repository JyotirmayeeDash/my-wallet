package com.wallet.controller;

import com.wallet.entity.Transaction;
import com.wallet.model.*;
import com.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/money")
    public AddMoneyResponse addMoney(@Valid @RequestBody TransactionDetails transactionDetails, Authentication authentication) {

        return transactionService.addMoney(transactionDetails, authentication.getName());
    }

    @PostMapping("/transfer")
    public MoneyTransferResponse transferMoney(@Valid @RequestBody MoneyTransferRequest moneyTransferRequest, Authentication authentication) {

        System.out.println("User name is"+authentication.getName());
        return transactionService.transferMoney(moneyTransferRequest, authentication.getName());
    }

    @GetMapping("/status/{transactionId}")
    public StatusEnquiryResponse getTransactionStatus(@Valid @PathVariable String transactionId) {

        return transactionService.getTransactionStatus(transactionId);
    }

    @GetMapping("/{userName}")
    public List<TransactionDetails> getTransactionByUserName(@Valid @PathVariable String userName) {

        return transactionService.getTransactions(userName);
    }

    @PostMapping("/refund")
    public RefundResponse refundTransaction(@Valid @RequestBody RefundRequest refundRequest, Authentication authentication) {

        return transactionService.refundTransaction(refundRequest);
    }

}

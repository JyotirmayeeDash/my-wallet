package com.wallet.service;

import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.model.*;
import com.wallet.repository.TransactionRepository;
import com.wallet.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AddMoneyResponse addMoney(TransactionDetails transactionDetails, String name) {
        User user = userRepository.findByUserName(name);
        List<Transaction> transactionList = user.getTransactions();
        if(transactionList == null) {
            transactionList = new ArrayList<>();
        }
        user.setAccountBalance(user.getAccountBalance().add(transactionDetails.getTransactionAmount()));
        Transaction transaction = modelMapper.map(transactionDetails, Transaction.class);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setCreationTime(ZonedDateTime.now());
        transaction.setUser(user);
        transactionList.add(transaction);
        user.setTransactions(transactionList);
        userRepository.save(user);
        AddMoneyResponse response = new AddMoneyResponse();
        response.setMessage("Money added successfully.");
        return response;
    }

    public MoneyTransferResponse transferMoney(MoneyTransferRequest moneyTransferRequest, String userName) {


    return new MoneyTransferResponse();
    }

    public StatusEnquiryResponse getTransactionStatus(String transactionId) {
        return new StatusEnquiryResponse();
    }

    public List<TransactionDetails> getTransactions(String userName) {

        return new ArrayList<>();
    }

    public RefundResponse refundTransaction(RefundRequest refundRequest) {

        return new RefundResponse();
    }
}

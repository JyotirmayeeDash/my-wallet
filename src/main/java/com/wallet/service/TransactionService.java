package com.wallet.service;

import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.model.*;
import com.wallet.repository.TransactionRepository;
import com.wallet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AddMoneyResponse addMoney(TransactionDetails transactionDetails, String name) {
        User user = userRepository.findByUserName(name);
        user.setAccountBalance(user.getAccountBalance().add(transactionDetails.getTransactionAmount()));
        List<Transaction> transactionList = populateTransaction(user, transactionDetails.getTransactionAmount(), "credit");
        user.setTransactions(transactionList);

        userRepository.save(user);
        AddMoneyResponse response = new AddMoneyResponse();

        response.setMessage("Money added successfully.");
        return response;
    }

    public MoneyTransferResponse transferMoney(MoneyTransferRequest moneyTransferRequest, String userName) {
        User sender = userRepository.findByUserName(userName);
        sender.setAccountBalance(sender.getAccountBalance().subtract(moneyTransferRequest.getTransferAmount()));
        List<Transaction> senderTransactionList = populateTransaction(sender, moneyTransferRequest.getTransferAmount(), "debit");
        sender.setTransactions(senderTransactionList);

        User receiver = userRepository.findByUserName(moneyTransferRequest.getReceiver());
        receiver.setAccountBalance(receiver.getAccountBalance().add(moneyTransferRequest.getTransferAmount()));
        List<Transaction> receiverTransactionList = populateTransaction(receiver, moneyTransferRequest.getTransferAmount(), "credit");
        receiver.setTransactions(receiverTransactionList);

        userRepository.save(sender);
        userRepository.save(receiver);

        MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();
        moneyTransferResponse.setMessage("Money transferred successfully.");
    return moneyTransferResponse;
    }

    public StatusEnquiryResponse getTransactionStatus(String transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        StatusEnquiryResponse statusEnquiryResponse = new StatusEnquiryResponse();
        statusEnquiryResponse.setStatus(transaction.get().getTransactionStatus());
        statusEnquiryResponse.setTransactionId(transactionId);
        return statusEnquiryResponse;
    }

    public List<TransactionDetails> getTransactions(String userName) {
    List<Transaction> transactionList = transactionRepository.findByUserName(userName);
    List<TransactionDetails> transactionDetailsList = new ArrayList<>();
    TransactionDetails transactionDetails = null;
    for(Transaction transaction : transactionList){

        transactionDetails = modelMapper.map(transaction, TransactionDetails.class);
        transactionDetailsList.add(transactionDetails);

    }
        return transactionDetailsList;
    }

    public RefundResponse refundTransaction(RefundRequest refundRequest) {

        return new RefundResponse();
    }

    private List<Transaction> populateTransaction(User user, BigDecimal transactionAmount, String transactionType) {
        List<Transaction> transactionList = user.getTransactions();
        if(transactionList == null) {
            transactionList = new ArrayList<>();
        }
        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setCreationTime(ZonedDateTime.now());
        transaction.setTransactionStatus("Approved");
        transaction.setTransactionType(transactionType);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setUser(user);
        transactionList.add(transaction);
        return transactionList;

    }
}

package com.wallet.service;

import com.wallet.constant.ErrorType;
import com.wallet.constant.WalletConstants;
import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.model.StubBankAccountDetails;
import com.wallet.model.StubBankResponse;
import com.wallet.model.exception.CustomException;
import com.wallet.model.request.MoneyTransferRequest;
import com.wallet.model.request.RefundRequest;
import com.wallet.model.request.TransactionDetails;
import com.wallet.model.request.UserDetails;
import com.wallet.model.response.AddMoneyResponse;
import com.wallet.model.response.MoneyTransferResponse;
import com.wallet.model.response.RefundResponse;
import com.wallet.model.response.StatusEnquiryResponse;
import com.wallet.repository.TransactionRepository;
import com.wallet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wallet.constant.WalletConstants.*;

/**
 * Service class to perform all the wallet transactions.
 */
@Service
@Slf4j
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Processes the add money request and saves the details to the database.
     *
     * @param transactionDetails transaction details
     * @param name user name
     * @return add money response
     */
    public AddMoneyResponse addMoney(TransactionDetails transactionDetails, String name) {
        User user = userRepository.findByUserName(name);
        StubBankAccountDetails bankAccountDetails = new StubBankAccountDetails();
        bankAccountDetails.setUserName(name);
        StubBankResponse bankResponse = callBankApi(bankAccountDetails);
        if(!(APPROVED_STATUS).equals(bankResponse.getStatus())) {
            log.error("Received error response from bank server.");
            throw new CustomException(ErrorType.BANK_SERVER_ERROR);
        }
        BigDecimal charge = calculateCharge(transactionDetails.getTransactionAmount());
        BigDecimal commission = calculateCommission(transactionDetails.getTransactionAmount());
        BigDecimal finalAmount = transactionDetails.getTransactionAmount().subtract(charge).subtract(commission);
        user.setWalletBalance(user.getWalletBalance().add(finalAmount));
        List<Transaction> transactionList = user.getTransactions();
        if(transactionList == null) {
            transactionList = new ArrayList<>();
        }
        Transaction transaction = populateTransaction(user, transactionDetails.getTransactionAmount(), "credit");
        transactionList.add(transaction);
        user.setTransactions(transactionList);

        userRepository.save(user);
        AddMoneyResponse response = new AddMoneyResponse();

        response.setMessage(WalletConstants.ADD_MONEY_SUCCESS_MESSAGE);
        response.setTransactionId(transaction.getTransactionId());
        return response;
    }

    /**
     * Processes the add money transfer and saves the details to the database.
     *
     * @param moneyTransferRequest money transfer request
     * @param userName user name
     * @return money transfer response
     */
    public MoneyTransferResponse transferMoney(MoneyTransferRequest moneyTransferRequest, String userName) {

        User sender = processSenderTransaction(userName, moneyTransferRequest);
        User receiver = processReceiverTransaction(moneyTransferRequest);

        userRepository.save(sender);
        userRepository.save(receiver);

        MoneyTransferResponse moneyTransferResponse = new MoneyTransferResponse();
        moneyTransferResponse.setMessage(MONEY_TRANSFER_SUCCESS_MESSAGE);
    return moneyTransferResponse;
    }

    /**
     * Creates the transaction for the sender in case of money transfer.
     *
     * @param userName user name
     * @param moneyTransferRequest money transfer request
     * @return user
     */
    private User processSenderTransaction(String userName, MoneyTransferRequest moneyTransferRequest) {
        User sender = userRepository.findByUserName(userName);
        if(sender.getWalletBalance().compareTo(moneyTransferRequest.getTransferAmount()) < 0) {
            log.error("Insufficient funds to transfer.");
            throw new CustomException(ErrorType.INSUFFICIENT_WALLET_BALANCE);
        }
        StubBankAccountDetails bankAccountDetails = new StubBankAccountDetails();
        bankAccountDetails.setUserName(userName);
        StubBankResponse bankResponse = callBankApi(bankAccountDetails);
        if(!(APPROVED_STATUS).equals(bankResponse.getStatus())) {
            log.error("Received error response from bank server.");
            throw new CustomException(ErrorType.BANK_SERVER_ERROR);
        }

        BigDecimal charge = calculateCharge(moneyTransferRequest.getTransferAmount());
        BigDecimal commission = calculateCommission(moneyTransferRequest.getTransferAmount());
        BigDecimal finalAmount = moneyTransferRequest.getTransferAmount().add(charge).add(commission);
        sender.setWalletBalance(sender.getWalletBalance().subtract(finalAmount));
        List<Transaction> senderTransactionList = sender.getTransactions();
        if(senderTransactionList == null) {
            senderTransactionList = new ArrayList<>();
        }
        Transaction senderTransaction = populateTransaction(sender, moneyTransferRequest.getTransferAmount(), "debit");
        senderTransactionList.add(senderTransaction);
        sender.setTransactions(senderTransactionList);
        return sender;
    }

    /**
     * Creates the transaction for the recipient in case of money transfer.
     *
     * @param moneyTransferRequest money transfer request
     * @return user
     */
    private User processReceiverTransaction(MoneyTransferRequest moneyTransferRequest) {
        User receiver = userRepository.findByUserName(moneyTransferRequest.getRecipient());
        StubBankAccountDetails recipientBankAccountDetails = new StubBankAccountDetails();
        recipientBankAccountDetails.setUserName(moneyTransferRequest.getRecipient());
        StubBankResponse recipientBankResponse = callBankApi(recipientBankAccountDetails);
        if(!(APPROVED_STATUS).equals(recipientBankResponse.getStatus())) {
            log.error("Received error response from bank server.");
            throw new CustomException(ErrorType.BANK_SERVER_ERROR);
        }
        receiver.setWalletBalance(receiver.getWalletBalance().add(moneyTransferRequest.getTransferAmount()));
        List<Transaction> receiverTransactionList = receiver.getTransactions();
        if(receiverTransactionList == null) {
            receiverTransactionList = new ArrayList<>();
        }
        Transaction receiverTransaction = populateTransaction(receiver, moneyTransferRequest.getTransferAmount(), "credit");
        receiverTransactionList.add(receiverTransaction);
        receiver.setTransactions(receiverTransactionList);
        return receiver;
    }

    /**
     * Fetches the status of the transaction from the database.
     *
     * @param transactionId transaction id
     * @return status enquiry response
     */
    public StatusEnquiryResponse getTransactionStatus(String transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if(!transaction.isPresent()) {
            log.error("No transaction found in the database with the given transaction id."+transactionId);
            throw new CustomException(ErrorType.TRANSACTION_ID_NOT_FOUND);
        }
        StatusEnquiryResponse statusEnquiryResponse = new StatusEnquiryResponse();
        statusEnquiryResponse.setStatus(transaction.get().getTransactionStatus());
        statusEnquiryResponse.setTransactionId(transactionId);
        return statusEnquiryResponse;
    }


    /**
     * Fetches all the transaction details of a user from the database.
     *
     * @param userName user name
     * @return transaction details list
     */
    public List<TransactionDetails> getTransactions(String userName) {
    List<Transaction> transactionList = transactionRepository.findByUserName(userName);

    List<TransactionDetails> transactionDetailsList = new ArrayList<>();
    TransactionDetails transactionDetails = null;
    for(Transaction transaction : transactionList){

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<User, UserDetails>() {

            @Override
            protected void configure() {
                skip().setTransactions(null);
            }
        });

        transactionDetails = mapper.map(transaction, TransactionDetails.class);
        transactionDetailsList.add(transactionDetails);

    }
        return transactionDetailsList;
    }

    /**
     * Process refund request and saves the details to the database.
     * @param refundRequest refund request
     * @param userName user name
     * @return refundResponse
     */
    public RefundResponse refundTransaction(RefundRequest refundRequest, String userName) {
        User user = userRepository.findByUserName(userName);
        StubBankAccountDetails bankAccountDetails = new StubBankAccountDetails();
        bankAccountDetails.setUserName(userName);
        StubBankResponse bankResponse = callBankApi(bankAccountDetails);
        if(!(APPROVED_STATUS).equals(bankResponse.getStatus())) {
            log.error("Received error response from bank server.");
            throw new CustomException(ErrorType.BANK_SERVER_ERROR);
        }
        user.setWalletBalance(user.getWalletBalance().add(refundRequest.getTransactionAmount()));
        RefundResponse response = new RefundResponse();
        List<Transaction> transactionList = user.getTransactions();
        if(transactionList == null) {
            transactionList = new ArrayList<>();
        }

        Transaction transaction = populateTransaction(user, refundRequest.getTransactionAmount(), "refund");
        transactionList.add(transaction);
        user.setTransactions(transactionList);

        userRepository.save(user);

        response.setMessage(REFUND_SUCCESS_MESSAGE);
        response.setRefundAmount(refundRequest.getTransactionAmount());
        response.setRefundTransactionId(transaction.getTransactionId());

        return response;
    }

    /**
     * Populates a transaction based on the parameters provided.
     *
     * @param user user
     * @param transactionAmount transaction amount
     * @param transactionType transaction type
     * @return transaction
     */
    private Transaction populateTransaction(User user, BigDecimal transactionAmount, String transactionType) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setCreationTime(ZonedDateTime.now());
        transaction.setTransactionStatus(APPROVED_STATUS);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setUser(user);

        return transaction;

    }


    /**
     * Calls the bank api.
     *
     * @param stubBankAccountDetails stubBankAccountDetails
     * @return StubBankResponse
     */
    public StubBankResponse callBankApi(StubBankAccountDetails stubBankAccountDetails) {
        final String uri = "http://localhost:8080/bank/stub";
        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling bank server");
        StubBankResponse result = restTemplate.postForObject( uri, stubBankAccountDetails, StubBankResponse.class);
        log.info("Received " +result.getStatus() + " status from bank");
        return result;

    }

    /**
     * Computes the charge amount for a transaction.
     *
     * @param amount transaction amount
     * @return chargeAmount
     */
    private BigDecimal calculateCharge(BigDecimal amount) {
        return (amount.multiply(new BigDecimal(CHARGE/100)));
    }

    /**
     * Computes commission amount for a transaction.
     *
     * @param amount transaction amount
     * @return commissionAmount
     */
    private BigDecimal calculateCommission(BigDecimal amount) {
        return (amount.multiply(new BigDecimal(COMMISSION /100)));
    }
}

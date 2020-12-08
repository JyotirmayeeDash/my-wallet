package com.wallet.controller;

import com.wallet.model.StubBankAccountDetails;
import com.wallet.model.StubBankResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to provide mocked response for all the bank operations.
 */
@RestController
@RequestMapping("/bank")
@Slf4j
public class BankStubController {

    @PostMapping("/stub")
    public StubBankResponse callBank(@RequestBody StubBankAccountDetails bankAccountDetails) {
        StubBankResponse stubBankResponse = new StubBankResponse();
        if(bankAccountDetails.getUserName().equals("jyodash") || bankAccountDetails.getUserName().equals("sou")) {
            stubBankResponse.setStatus("APPROVED");

        }
        else {
           log.error("User does not exist.");
            stubBankResponse.setStatus("DECLINED");
        }
      return stubBankResponse;
    }
}

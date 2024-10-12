package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        Account account = accountService.findAccount(accountNumber);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest request) {

        try {
            DepositTransaction depositTransaction = new DepositTransaction(request.getAmount());
            TransactionStatus status = accountService.processTransaction(accountNumber, depositTransaction);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransactionStatus("FAIL", null, e.getMessage()));
        }
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest request) {

        try {
            WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(request.getAmount());
            TransactionStatus status = accountService.processTransaction(accountNumber, withdrawalTransaction);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TransactionStatus("FAIL", null, e.getMessage()));
        }
    }
}
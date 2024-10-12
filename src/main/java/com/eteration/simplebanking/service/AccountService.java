package com.eteration.simplebanking.service;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public TransactionStatus processTransaction(String accountNumber, Transaction transaction)
        throws InsufficientBalanceException {

            Account account = findAccount(accountNumber);
            transaction.execute(account);
            account.getTransactions().add(transaction);
            accountRepository.save(account);

            return new TransactionStatus("OK", transaction.getApprovalCode());

    }
}

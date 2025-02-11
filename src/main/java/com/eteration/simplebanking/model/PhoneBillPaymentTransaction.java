package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;

import javax.persistence.Entity;

@Entity
public class PhoneBillPaymentTransaction extends Transaction{

    private String operator;
    private String phoneNumber;

    public PhoneBillPaymentTransaction(){
        super();
    }

    public PhoneBillPaymentTransaction(double amount, String operator, String phoneNumber) {
        super(amount);
        this.operator = operator;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void execute(Account account) throws InsufficientBalanceException {
        account.debit(amount);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

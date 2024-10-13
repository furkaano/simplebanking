package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Date date;
    protected double amount;
    protected String approvalCode;

    public Transaction(){
        this.date = new Date();
        this.approvalCode = UUID.randomUUID().toString();
    }

    public Transaction(double amount){
        this();
        this.amount = amount;
    }

    public abstract void execute(Account account) throws InsufficientBalanceException;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    // To get transaction type
    public String getType(){
        return this.getClass().getSimpleName();
    }
}

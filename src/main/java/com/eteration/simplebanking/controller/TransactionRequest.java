package com.eteration.simplebanking.controller;

public class TransactionRequest {

    private double amount;

    public TransactionRequest() {
    }

    public TransactionRequest(double amount) {
        this.amount = amount;
    }

    // Getter and Setter

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

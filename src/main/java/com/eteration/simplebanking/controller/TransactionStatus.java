package com.eteration.simplebanking.controller;

public class TransactionStatus {

    private String status;
    private String approvalCode;
    private String message;

    public TransactionStatus() {
    }

    public TransactionStatus(String status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }

    public TransactionStatus(String status, String approvalCode, String message) {
        this.status = status;
        this.approvalCode = approvalCode;
        this.message = message;
    }

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

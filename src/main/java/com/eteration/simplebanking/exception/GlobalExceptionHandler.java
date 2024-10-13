package com.eteration.simplebanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.eteration.simplebanking.controller.TransactionStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<TransactionStatus> handleAccountNotFoundException(AccountNotFoundException ex) {
        TransactionStatus status = new TransactionStatus();
        status.setStatus("FAIL");
        status.setMessage(ex.getMessage());
        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<TransactionStatus> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        TransactionStatus status = new TransactionStatus();
        status.setStatus("FAIL");
        status.setMessage(ex.getMessage());
        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
    }
}

package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.*;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(AccountController.class)
class ControllerTests  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }
/*
    // Test 1
    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).findAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
    }
*/
    // Replacement of Test 1
    @Test
    public void shouldReturnSuccessStatusWhenCreditTransactionIsProcessed() throws Exception {
        // Given: An account number and a credit transaction request
        String accountNumber = "17892";
        TransactionRequest request = new TransactionRequest(1000.0);
        TransactionStatus expectedStatus = new TransactionStatus("OK", "approval-code-123");

        // Mock the service to return a successful transaction status
        when(accountService.processTransaction(eq(accountNumber), any(DepositTransaction.class)))
                .thenReturn(expectedStatus);

        // When: A credit transaction is submitted via the API
        ResultActions result = mockMvc.perform(post("/account/v1/credit/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then: The response should indicate success with the correct approval code
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").value("approval-code-123"));

        // Verify that the service was called correctly
        verify(accountService, times(1)).processTransaction(eq(accountNumber), any(DepositTransaction.class));
    }

/*
    // Test 2
    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(50.0));
        verify(service, times(2)).findAccount("17892");
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("OK", result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }
*/

    // Replacement of Test 2
    @Test
    public void shouldReturnSuccessStatusWhenDebitTransactionIsProcessed() throws Exception {
        // Given: An account number and a debit transaction request
        String accountNumber = "17892";
        TransactionRequest debitRequest = new TransactionRequest(50.0);
        TransactionStatus expectedStatus = new TransactionStatus("OK", "approval-code-456");

        // Mock the service to return a successful transaction status
        when(accountService.processTransaction(eq(accountNumber), any(WithdrawalTransaction.class)))
                .thenReturn(expectedStatus);

        // When: A debit transaction is submitted via the API
        ResultActions result = mockMvc.perform(post("/account/v1/debit/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)));

        // Then: The response should indicate success with the correct approval code
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").value("approval-code-456"));

        // Verify that the service was called correctly
        verify(accountService, times(1)).processTransaction(eq(accountNumber), any(WithdrawalTransaction.class));
    }
}

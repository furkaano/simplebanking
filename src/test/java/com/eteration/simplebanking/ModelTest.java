package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.eteration.simplebanking.model.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ModelTest {
	
	@Test
	public void testCreateAccountAndSetBalance0() {
		Account account = new Account("Kerem Karaca", "17892");
		assertTrue(account.getOwner().equals("Kerem Karaca"));
		assertTrue(account.getAccountNumber().equals("17892"));
		assertTrue(account.getBalance() == 0);
	}

	@Test
	public void testDepositIntoBankAccount() throws InsufficientBalanceException {
		Account account = new Account("Demet Demircan", "9834");
		account.deposit(new DepositTransaction(100));
		assertTrue(account.getBalance() == 100);
	}

	@Test
	public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
		Account account = new Account("Demet Demircan", "9834");
		account.deposit(new DepositTransaction(100));
		assertTrue(account.getBalance() == 100);
		account.deposit(new WithdrawalTransaction(50));
		assertTrue(account.getBalance() == 50);
	}

	@Test
	public void testWithdrawException() {
		InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
			Account account = new Account("Demet Demircan", "9834");
			account.deposit(new WithdrawalTransaction(500));
		});
		assertTrue(Objects.equals(exception.getMessage(), "Insufficient balance"));
	}

	@Test
	public void testTransactions() throws InsufficientBalanceException {
		// Create account
		Account account = new Account("Canan Kaya", "1234");
		assertTrue(account.getTransactions().size() == 0);

		// Deposit Transaction
		DepositTransaction depositTrx = new DepositTransaction(200);
		assertTrue(depositTrx.getDate() != null);
		account.deposit(depositTrx);
		assertTrue(account.getBalance() == 200);
		assertTrue(account.getTransactions().size() == 1);

		// Withdrawal Transaction
		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
		assertTrue(withdrawalTrx.getDate() != null);
		account.deposit(withdrawalTrx);
		assertTrue(account.getBalance() == 140);
		assertTrue(account.getTransactions().size() == 2);

		// Phone Bill Payment Transaction
		PhoneBillPaymentTransaction phoneBillTrx = new PhoneBillPaymentTransaction(96.50,"Vodafone", "5423345566");
		account.deposit(phoneBillTrx);
		assertTrue(account.getBalance() == 43.5);
		assertTrue(account.getTransactions().size() == 3);

	}
}

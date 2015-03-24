package br.com.bettercode.bank;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {

	@Test
	public void deposit_shouldDepositMoneyOnAccount() {
		Account account = new Account();
		BigDecimal depositValue = new BigDecimal("100");

		account.deposit(depositValue);

		assertEquals(depositValue, account.balance());
	}

	@Test
	public void deposit_shouldDepositMoneyOnAccountMultipleTimes() {
		Account account = new Account();
		BigDecimal depositValue = new BigDecimal("100");

		account.deposit(depositValue);
		account.deposit(depositValue);
		account.deposit(depositValue);

		assertEquals(new BigDecimal("300"), account.balance());
	}

	@Test
	public void withdraw_shouldWithdrawMoneyFromAccount() {
		Account account = new Account();
		account.deposit(new BigDecimal("100"));

		account.withdraw(new BigDecimal("50"));

		assertEquals(new BigDecimal("50"), account.balance());
	}

	@Test
	public void withdraw_shouldWithdrawMoneyFromAccountMultipleTimes() {
		Account account = new Account();
		account.deposit(new BigDecimal("100"));

		account.withdraw(new BigDecimal("50"));
		account.withdraw(new BigDecimal("20"));

		assertEquals(new BigDecimal("30"), account.balance());
	}

	@Test(expected = AccountLimitException.class)
	public void withdraw_shouldNotWithdrawBeyondTheDefaultLimit(){
		Account account = new Account();
		account.deposit(new BigDecimal("100"));

		account.withdraw(new BigDecimal("101"));
	}

	@Test
	public void withdraw_shouldWithdrawMoneyFromAccountLimit(){
		Account account = new Account(BigDecimal.TEN);
		account.deposit(new BigDecimal("100"));

		account.withdraw(new BigDecimal("101"));
		account.withdraw(new BigDecimal("5"));

		assertEquals(new BigDecimal("-6"), account.balance());
	}

	@Test(expected = AccountLimitException.class)
	public void withdraw_shouldNotWithdrawBeyondTheLimit(){
		Account account = new Account(BigDecimal.TEN);
		account.deposit(new BigDecimal("100"));

		account.withdraw(new BigDecimal("111"));
	}

	@Test
	public void getStatement_shouldReturnEmptyStatement(){
		Account account = new Account();

		List statement = account.getStatement();

		assertTrue(statement.isEmpty());
	}

	@Test
	public void getStatement_shouldReturnLastWeekStatement() {
		Account account = new Account();
		account.deposit(new BigDecimal("100"));
		account.withdraw(BigDecimal.TEN);

		List statement = account.getStatement();

		assertEquals(2, statement.size());
	}

}

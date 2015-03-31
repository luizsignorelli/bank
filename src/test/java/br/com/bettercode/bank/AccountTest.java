package br.com.bettercode.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import br.com.bettercode.bank.time.BankTime;

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

		Statement statement = account.getStatement();

		assertTrue(statement.isEmpty());
	}

	@Test
	public void getStatement_shouldReturnLastWeekStatement() {
		Account account = new Account();
		account.deposit(new BigDecimal("100"));
		account.withdraw(BigDecimal.TEN);

		Statement statement = account.getStatement();

		assertEquals(2, statement.size());
	}
	
	@Test
	public void getStatement_shouldNotReturnEntriesBeforeLastWeek() {
		LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);

		Account account = new Account();

		BankTime.useFixedClockAt(twoWeeksAgo);
		account.deposit(new BigDecimal("100"));
		
		BankTime.useSystemDefaultZoneClock();
		account.withdraw(BigDecimal.TEN);

		Statement statement = account.getStatement();

		assertEquals(1, statement.size());
	}

}

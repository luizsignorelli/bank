package br.com.bettercode.bank;

import java.math.BigDecimal;

public class Account {

	private BigDecimal limit;
	private BigDecimal balance;
	private Statement statement;

	public Account() {
		balance = BigDecimal.ZERO;
		limit = BigDecimal.ZERO;
		statement = new Statement();
	}

	public Account(BigDecimal limit) {
		this();
		this.limit = limit;
	}

	public void deposit(BigDecimal depositValue) {
		statement.add(new AccountEntry());
		balance = balance.add(depositValue);
	}

	public BigDecimal balance() {
		return balance;
	}

	public void withdraw(BigDecimal withdrawValue) {
		if(balance.add(limit).compareTo(withdrawValue) < 0) {
			throw new AccountLimitException();
		}
		statement.add(new AccountEntry());
		balance = balance.subtract(withdrawValue);
	}

	public Statement getStatement() {
		return statement;
	}
}

package br.com.bettercode.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

	private BigDecimal limit;
	private BigDecimal balance;
	private List statement;

	public Account() {
		balance = BigDecimal.ZERO;
		limit = BigDecimal.ZERO;
		statement = new ArrayList();
	}

	public Account(BigDecimal limit) {
		this();
		this.limit = limit;
	}

	public void deposit(BigDecimal depositValue) {
		balance = balance.add(depositValue);
	}

	public BigDecimal balance() {
		return balance;
	}

	public void withdraw(BigDecimal withdrawValue) {
		if(balance.add(limit).compareTo(withdrawValue) < 0) {
			throw new AccountLimitException();
		}

		balance = balance.subtract(withdrawValue);
	}

	public List getStatement() {
		return statement;
	}
}

package br.com.bettercode.bank;

import java.util.ArrayList;
import java.util.List;

public class Statement {

	private List<AccountEntry> entries;

	public Statement() {
		entries = new ArrayList<>();
	}

	public void add(AccountEntry accountEntry) {
		entries.add(accountEntry);
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	public Object size() {
		return entries.size();
	}

}

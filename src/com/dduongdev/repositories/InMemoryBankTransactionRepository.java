package com.dduongdev.repositories;

import java.util.ArrayList;
import java.util.List;

import com.dduongdev.entities.BankTransaction;

public class InMemoryBankTransactionRepository implements IBankTransactionRepository {
	private static InMemoryBankTransactionRepository instance = null;
	private List<BankTransaction> transactions;
	
	public static InMemoryBankTransactionRepository getInstance() {
		if (instance == null) {
			instance = new InMemoryBankTransactionRepository();
		}
		return instance;
	}
	
	private InMemoryBankTransactionRepository() {
		transactions = new ArrayList<BankTransaction>();
	}
	
	public List<BankTransaction> getAll() {
		return transactions;
	}
	
	public void add(BankTransaction transaction) {
		this.transactions.add(transaction);
	}
	
	public void addBatch(List<BankTransaction> transactions) {
		this.transactions.addAll(transactions);
	}
}

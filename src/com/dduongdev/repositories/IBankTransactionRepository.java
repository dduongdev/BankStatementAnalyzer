package com.dduongdev.repositories;

import java.util.List;

import com.dduongdev.entities.BankTransaction;

public interface IBankTransactionRepository {
	public List<BankTransaction> getAll();
	public void add(BankTransaction transaction);
	public void addBatch(List<BankTransaction> transactions);
}

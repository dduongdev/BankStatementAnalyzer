package com.dduongdev.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;
import com.dduongdev.repositories.IBankTransactionRepository;
import com.dduongdev.utils.BankTransactionSortTypes;

public class BankStatementAnalysisService {
	private IBankTransactionRepository bankTransactionRepository;
	
	public BankStatementAnalysisService(IBankTransactionRepository bankTransactionRepository) {
		this.bankTransactionRepository = bankTransactionRepository;
	}
	
	public double calculateTotalAmountOfTransactionType(BankTransactionTypes transactionType) {
		double totalAmount = 0;
		for (var transaction : bankTransactionRepository.getAll()) {
			if (transaction.getTransactionType() == transactionType) {
				totalAmount += transaction.getTransactionAmount();
			}
		}
		return totalAmount;
	}
	
	public int countBankTransactionsInMonth(Month month) {
		int count = 0;
		for (var transaction : bankTransactionRepository.getAll()) {
			if (transaction.getTransactionDate().getMonth() == month) {
				count += 1;
			}
		}
		return count;
	}
	
	public List<BankTransaction> getTopBankTransactions(Comparator<BankTransaction> sortCriterion, 
			BankTransactionSortTypes bankTransactionSortType, int limit) {
		List<BankTransaction> result = new ArrayList<BankTransaction>(bankTransactionRepository.getAll());
		
		if (bankTransactionSortType == BankTransactionSortTypes.MIN) {
			result.sort(sortCriterion);
		}
		else {
			result.sort(sortCriterion.reversed());
		}
		
		return result.stream().limit(limit).toList();
	}
	
	public String getMostSpentGroup() {
		Map<String, Double> groupSpentTotals = new HashMap<String, Double>();
		for (var transaction : bankTransactionRepository.getAll()) {
			if (transaction.getTransactionType() != BankTransactionTypes.SEND) {
				continue;
			}
			
			String bankTransactionGroup = transaction.getTransactionGroup();
			if (groupSpentTotals.containsKey(bankTransactionGroup)) {
				groupSpentTotals.put(bankTransactionGroup, groupSpentTotals.get(bankTransactionGroup) + transaction.getTransactionAmount());
			}
			else {
				groupSpentTotals.put(bankTransactionGroup, transaction.getTransactionAmount());
			}
		}
		
		return groupSpentTotals.entrySet()
								.stream()
								.max(Map.Entry.comparingByValue())
								.map(Map.Entry::getKey)
								.orElse(null);
	}
}


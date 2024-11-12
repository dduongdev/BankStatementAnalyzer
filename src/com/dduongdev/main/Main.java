package com.dduongdev.main;

import java.io.IOException;
import java.time.Month;
import java.util.Comparator;
import java.util.List;

import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;
import com.dduongdev.entities.strategy.FileDataFetchStrategy;
import com.dduongdev.entities.strategy.IDataFetchStrategy;
import com.dduongdev.repositories.IBankTransactionRepository;
import com.dduongdev.repositories.InMemoryBankTransactionRepository;
import com.dduongdev.services.BankStatementAnalysisService;
import com.dduongdev.services.BankTransactionService;
import com.dduongdev.utils.BankTransactionSortTypes;

public class Main {
	public static void main(String[] args) throws IOException {
		IDataFetchStrategy dataFetchStrategy = new FileDataFetchStrategy();
		IBankTransactionRepository bankTransactionRepository = InMemoryBankTransactionRepository.getInstance();
		BankTransactionService bankTransactionService = new BankTransactionService();
		
		
		List<String> transactionsRawData = (List<String>) dataFetchStrategy.fetchData();
		
		for (var transaction : transactionsRawData) {
			bankTransactionRepository.add(bankTransactionService.mapTransactionRecordToObject(transaction));
		}
		
		BankStatementAnalysisService bankStatementAnalysisService = new BankStatementAnalysisService(bankTransactionRepository);
		
		double totalProfit = bankStatementAnalysisService.calculateTotalAmountOfTransactionType(BankTransactionTypes.RECEIVE);
		double totalLoss = bankStatementAnalysisService.calculateTotalAmountOfTransactionType(BankTransactionTypes.SEND);
		System.out.println("Total profit: " + totalProfit + ", Total loss: " + totalLoss + ", and it is " + (totalProfit - totalLoss > 0 ? "positive" : "negative"));
		System.out.println();
		
		Month statisticMonth = Month.of(2);
		int bankTransactionsCount = bankStatementAnalysisService.countBankTransactionsInMonth(statisticMonth);
		System.out.println("The number of transactions in " + statisticMonth + " is: " + bankTransactionsCount);
		System.out.println();
		
		List<BankTransaction> topExpenseTransactions = bankStatementAnalysisService.getTopBankTransactions(Comparator.comparing(BankTransaction::getTransactionAmount), BankTransactionSortTypes.MAX, 10);
		System.out.println("Top 10 expenses: ");
		for (var transaction : topExpenseTransactions) {
			System.out.println("Date: " + transaction.getTransactionDate() + ", Amount: " + transaction.getTransactionAmount() + ", Type: " + transaction.getTransactionType() + ", Group: " + transaction.getTransactionGroup());
		}
		System.out.println();
		
		System.out.println("The category he spends most of his money on is " + bankStatementAnalysisService.getMostSpentGroup());
	}
}

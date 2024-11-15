package com.dduongdev.main;

import java.io.IOException;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;
import com.dduongdev.entities.datafetcher.DataFetchOptions;
import com.dduongdev.entities.datafetcher.DataFetcherFactory;
import com.dduongdev.entities.datafetcher.IDataFetcher;
import com.dduongdev.repositories.IBankTransactionRepository;
import com.dduongdev.repositories.InMemoryBankTransactionRepository;
import com.dduongdev.services.BankStatementAnalysisService;
import com.dduongdev.services.BankTransactionService;
import com.dduongdev.utils.BankTransactionTopTypes;

public class Main {
	public static void main(String[] args) throws IOException, IllegalArgumentException  {
		List<String> transactionsRawData = null;
		
		Properties properties = new Properties();
		try {
			properties.load(Main.class.getResourceAsStream("/datafetchsettings.properties"));
			String option = properties.getProperty("option");
			String path = properties.getProperty("path");
			
			DataFetchOptions dataFetchOption = DataFetchOptions.valueOf(option);
			IDataFetcher dataFetcher = DataFetcherFactory.factory(dataFetchOption);
			
			transactionsRawData = (List<String>) dataFetcher.fetchData(path);
		}
		finally {}
		
		IBankTransactionRepository bankTransactionRepository = InMemoryBankTransactionRepository.getInstance();
		BankTransactionService bankTransactionService = new BankTransactionService();
		
		
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
		
		List<BankTransaction> topExpenseTransactions = bankStatementAnalysisService.getTopBankTransactions(Comparator.comparing(BankTransaction::getTransactionAmount), BankTransactionTopTypes.MIN, BankTransactionTypes.SEND, 10);
		System.out.println("Top 10 expenses: ");
		for (var transaction : topExpenseTransactions) {
			System.out.println("Date: " + transaction.getTransactionDate() + ", Amount: " + transaction.getTransactionAmount() + ", Type: " + transaction.getTransactionType() + ", Group: " + transaction.getTransactionGroup());
		}
		System.out.println();
		
		System.out.println("The category he spends most of his money on is " + bankStatementAnalysisService.getMostAmountGroup(BankTransactionTypes.SEND));
	}
}

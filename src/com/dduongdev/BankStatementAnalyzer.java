package com.dduongdev;

import java.io.IOException;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import com.dduongdev.dao.datafetcher.DataFetchOptions;
import com.dduongdev.dao.datafetcher.DataFetcherFactory;
import com.dduongdev.dao.datafetcher.IDataFetcher;
import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;
import com.dduongdev.repositories.IBankTransactionRepository;
import com.dduongdev.repositories.InMemoryBankTransactionRepository;
import com.dduongdev.services.BankStatementAnalysisService;
import com.dduongdev.services.BankTransactionTopTypes;

public class BankStatementAnalyzer {
	public static void main(String[] args) throws IOException, IllegalArgumentException  {
		List<BankTransaction> bankTransactions = null;
		
		Properties properties = new Properties();
		try {
			properties.load(BankStatementAnalyzer.class.getResourceAsStream("/datafetchsettings.properties"));
			String option = properties.getProperty("option");
			String path = properties.getProperty("path");
			
			DataFetchOptions dataFetchOption = DataFetchOptions.valueOf(option);
			IDataFetcher dataFetcher = DataFetcherFactory.factory(dataFetchOption);
			
			bankTransactions = (List<BankTransaction>) dataFetcher.fetchData(path);
		}
		finally {}
		
		IBankTransactionRepository bankTransactionRepository = InMemoryBankTransactionRepository.getInstance();
		bankTransactionRepository.addBatch(bankTransactions);
		
		BankStatementAnalysisService bankStatementAnalysisService = new BankStatementAnalysisService(bankTransactionRepository);
		
		double totalProfit = bankStatementAnalysisService.calculateTotalAmountOfTransactionType(BankTransactionTypes.RECEIVE);
		double totalLoss = bankStatementAnalysisService.calculateTotalAmountOfTransactionType(BankTransactionTypes.SEND);
		System.out.println("Total profit: " + totalProfit + ", Total loss: " + totalLoss + ", and it is " + (totalProfit - totalLoss > 0 ? "positive" : "negative"));
		System.out.println();
		
		Month statisticMonth = Month.of(2);
		int bankTransactionsCount = bankStatementAnalysisService.countBankTransactionsInMonth(statisticMonth);
		System.out.println("The number of transactions in " + statisticMonth + " is: " + bankTransactionsCount);
		System.out.println();
		
		List<BankTransaction> topExpenseTransactions = bankStatementAnalysisService.getTopBankTransactions(Comparator.comparing(BankTransaction::getTransactionAmount), BankTransactionTopTypes.MAX, BankTransactionTypes.SEND, 10);
		System.out.println("Top 10 expenses: ");
		for (var transaction : topExpenseTransactions) {
			System.out.println("Date: " + transaction.getTransactionDate() + ", Amount: " + transaction.getTransactionAmount() + ", Type: " + transaction.getTransactionType() + ", Group: " + transaction.getTransactionGroup());
		}
		System.out.println();
		
		System.out.println("The category he spends most of his money on is " + bankStatementAnalysisService.getMostAmountGroup(BankTransactionTypes.SEND));
	}
}

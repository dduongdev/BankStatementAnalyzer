package com.dduongdev.dao.datafetcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;

public class CSVDataFetcher implements IDataFetcher {
	public List<BankTransaction> fetchData(String path) throws IOException {
		List<BankTransaction> bankTransactions = new ArrayList<BankTransaction>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                bankTransactions.add(mapTransactionRecordToObject(line));
            }
        }
        return bankTransactions;
	}
	
	private BankTransaction mapTransactionRecordToObject(String transactionRecord) {
		String[] splittedData = transactionRecord.split(",");
		String transactionDateRawData = splittedData[0];
		String transactionAmountRawData = splittedData[1];
		String transactionGroup = splittedData[2];
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate transactionDate = LocalDate.parse(transactionDateRawData, formatter);
		
		BankTransactionTypes transactionType = (transactionAmountRawData.contains("-")) ? BankTransactionTypes.SEND : BankTransactionTypes.RECEIVE;
		
		double transactionAmount = Double.parseDouble(transactionAmountRawData.replace("-", ""));
		
		return new BankTransaction(transactionDate, transactionAmount, transactionGroup, transactionType);
	}
}

package com.dduongdev.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.dduongdev.entities.BankTransaction;
import com.dduongdev.entities.BankTransactionTypes;

public class BankTransactionService {
	public BankTransaction mapTransactionRecordToObject(String transactionRecord) {
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

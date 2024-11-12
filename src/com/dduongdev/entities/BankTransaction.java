package com.dduongdev.entities;

import java.time.LocalDate;

public class BankTransaction {
	private LocalDate transactionDate;
	private double transactionAmount;
	private String transactionGroup;
	private BankTransactionTypes transactionType;
	
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionGroup() {
		return transactionGroup;
	}

	public void setTransactionGroup(String transactionGroup) {
		this.transactionGroup = transactionGroup;
	}
	
	public BankTransactionTypes getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(BankTransactionTypes bankTransactionType) {
		this.transactionType = bankTransactionType;
	}
	
	public BankTransaction() {}

	public BankTransaction(LocalDate transactionDate, double transactionAmount, 
					String transactionGroup, BankTransactionTypes transactionType) {
		this.transactionDate = transactionDate;
		this.transactionAmount = transactionAmount;
		this.transactionGroup = transactionGroup;
		this.transactionType = transactionType;
	}
	
	@Override
	public String toString() {
		return "BankTransaction [transactionDate=" + transactionDate + ", transactionAmount=" + transactionAmount
				+ ", transactionGroup=" + transactionGroup + ", transactionType=" + transactionType + "]";
	}
}

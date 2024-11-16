package com.dduongdev.dao.datafetcher;

public class DataFetcherFactory {
	public static IDataFetcher factory(DataFetchOptions option) {
		switch (option) {
		case DataFetchOptions.CSV:
			return new CSVDataFetcher();
		default:
			throw new IllegalArgumentException("No data fetch option found!");
		}
	}
}

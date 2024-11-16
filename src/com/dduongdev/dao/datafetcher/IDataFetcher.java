package com.dduongdev.dao.datafetcher;

import java.io.IOException;
import java.util.List;

import com.dduongdev.entities.BankTransaction;

public interface IDataFetcher {
	public List<BankTransaction> fetchData(String path) throws IOException;
}

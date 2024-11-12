package com.dduongdev.entities.strategy;

import java.io.IOException;
import java.util.List;

public interface IDataFetchStrategy {
	public List<String> fetchData() throws IOException;
}

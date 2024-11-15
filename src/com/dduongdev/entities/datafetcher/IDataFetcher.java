package com.dduongdev.entities.datafetcher;

import java.io.IOException;
import java.util.List;

public interface IDataFetcher {
	public List<String> fetchData(String path) throws IOException;
}

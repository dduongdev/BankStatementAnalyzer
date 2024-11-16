package com.dduongdev.dao.datafetcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataFetcher implements IDataFetcher {
	public List<String> fetchData(String path) throws IOException {
		List<String> data = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
	}
}

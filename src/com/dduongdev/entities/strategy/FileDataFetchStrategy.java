package com.dduongdev.entities.strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dduongdev.utils.Constants;

public class FileDataFetchStrategy implements IDataFetchStrategy {
	public List<String> fetchData() throws IOException {
		List<String> data = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
	}
}

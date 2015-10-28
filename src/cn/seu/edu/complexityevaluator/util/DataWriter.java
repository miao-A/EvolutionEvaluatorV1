﻿package cn.seu.edu.complexityevaluator.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.csvreader.CsvWriter;

public class DataWriter {
	private String csvFilePath;
	private CsvWriter wr;
	
	public DataWriter(String csvFilePath) {
		this.csvFilePath = csvFilePath;
		this.wr = new CsvWriter(csvFilePath, ',', Charset.forName("SJIS"));
	}
	
	public void writeToCsv(String[] result) {
		try {
			wr.writeRecord(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		wr.close();
	}
}

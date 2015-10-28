package cn.seu.edu.complexityevaluator.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class DataReader {
	private String csvFilePath;
	private String[] header;
	private List<String[]> listOfValueWithoutHeader;
	
	public DataReader(String csvFilePath){
		this.csvFilePath = csvFilePath;
		this.listOfValueWithoutHeader = new ArrayList<String[]>();
		this.readDataFromCsv();
	}
	
	public String[] getHeader(){
		return header;
	}
	
	public List<String[]> getContentListWithoutHeader(){
		return listOfValueWithoutHeader;
	}
	
	private void readDataFromCsv() {
		CsvReader reader = null;
		try {
			reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
			if(reader.readHeaders()){
				header = reader.getHeaders();
			}else{
				header = new String[0];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while (reader.readRecord()) {
				listOfValueWithoutHeader.add(reader.getValues());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*String[][] datas = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			datas[i] = list.get(i);
		}
		
		for (int i = 0; i < header.length; i++) {
			System.out.print(header[i] + "\t");
		}
		System.out.println("");

		for (int i = 0; i < datas.length; i++) {
			Object[] data = datas[i]; // 閸欐牕锸稉锟界矋閺佺増宓?
			for (int j = 0; j < data.length; j++) {
				Object cell = data[j];
				System.out.print(cell + "\t");
			}
			System.out.println("");
		}*/
		reader.close();
	}
}

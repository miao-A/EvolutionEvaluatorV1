package cn.seu.edu.integrabilityevaluator.parser;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;

import cn.seu.edu.integrabilityevaluator.astvisitor.ChangeabilityOfClassRequestor;
import cn.seu.edu.integrabilityevaluator.dbconnect.ChangeabilityInfoConnector;


public class Substitutability {

	private String projectName;
	private String version;	
	
	public Substitutability(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
//		ChangeabilityRequestor changeabilityRequestor = new ChangeabilityRequestor(projectNameString,versionString);
		ChangeabilityOfClassRequestor changeabilityRequestor = new ChangeabilityOfClassRequestor(projectNameString,versionString);
		ReadFile readFile = new ReadFile(pathOfProject);
		List<String> filelist = readFile.readJavaFiles();
		
		int listLength = filelist.size();
		for (int i = 0; i < listLength; i += 100) {
			int toIndex = 0;
			if (i+100<listLength) {
				toIndex = i+100;
			}else {
				toIndex = listLength;
			}
			
			String[] sourceFilePaths = filelist.subList(i, toIndex).toArray(new String[toIndex - i]);
			parser.createASTs(sourceFilePaths,  null, new String[0], changeabilityRequestor, null);
			
		}
		
		
		ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// 添加三行数据  		        
	    for (String string : packageNameList) {
	    	dbConnector.setChangeabilityInfo(string);
		}
	}
	
	
	
	

	
	
}

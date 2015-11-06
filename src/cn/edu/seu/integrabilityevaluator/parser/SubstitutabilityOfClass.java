package cn.edu.seu.integrabilityevaluator.parser;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTParser;

import cn.edu.seu.integrabilityevaluator.astvisitor.SubstitutabilityRequestor;
import cn.edu.seu.integrabilityevaluator.dbconnect.SubstitutabilityConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.SubstitutabilityInfoConnector;


public class SubstitutabilityOfClass {

	private String projectName;
	private String version;	
	
	public SubstitutabilityOfClass(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
//		ChangeabilityRequestor changeabilityRequestor = new ChangeabilityRequestor(projectNameString,versionString);
		SubstitutabilityRequestor substitutabilityRequestor = new SubstitutabilityRequestor(projectNameString,versionString);
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
			parser.createASTs(sourceFilePaths,  null, new String[0], substitutabilityRequestor, null);
			
		}
		
		SubstitutabilityInfoConnector dbConnector = new SubstitutabilityInfoConnector(projectNameString, versionString);
		//ChangeabilityInfoConnector dbConnector = new ChangeabilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// 添加三行数据  		        
	    for (String string : packageNameList) {
	    	dbConnector.setSubtitutabilityInfo(string);
		}
	}
	
	
	
	

	
	
}

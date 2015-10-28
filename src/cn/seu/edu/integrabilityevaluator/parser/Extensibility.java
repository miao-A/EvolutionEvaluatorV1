package cn.seu.edu.integrabilityevaluator.parser;


import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTParser;


import cn.seu.edu.integrabilityevaluator.astvisitor.ExtensibilityRequestor;
import cn.seu.edu.integrabilityevaluator.dbconnect.ExtensibilityInfoConnector;

public class Extensibility {
	private int numOfInterface;
	private int numOfAbstract;
	private int numOfClass;
	private double ratioOfInterface;
	
	private String projectNameString;
	private String versionString;


	
	public Extensibility(ASTParser parser, String pathOfProject,String projectNameString,String versionString){
		
		ExtensibilityRequestor extensibilityRequestor = new ExtensibilityRequestor(projectNameString,versionString);

		this.projectNameString = projectNameString;
		this.versionString = versionString;
		
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
			
			parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);
			
		}
		
		/*String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		
		parser.createASTs(sourceFilePaths,  null, new String[0], extensibilityRequestor, null);
		*/
		
		ExtensibilityInfoConnector dbConnector = new ExtensibilityInfoConnector(projectNameString,versionString);
		ArrayList<String> packageNameList= dbConnector.getpackageName();
			// 添加三行数据  		        
	    for (String string : packageNameList) {
	    	dbConnector.setExtensibilityInfo(string);
		}

	}	



}

package cn.seu.edu.integrabilityevaluator.parser;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import cn.seu.edu.integrabilityevaluator.astvisitor.InnerCompatibilityRequestor;
import cn.seu.edu.integrabilityevaluator.astvisitor.JavaVersionCompatibilityRequestor;
import cn.seu.edu.integrabilityevaluator.astvisitor.OuterCompatibilityRequestor;
import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.JarClassModel;
import cn.seu.edu.integrabilityevaluator.model.UnCompatibilityMIModel;


public class OuterCompatibility {

	private String projectName;
	private String version;	
	private List<String> uncompatibilityfileList = new ArrayList<>();
	private List<JarClassModel> uncompatibilityClassModels = new ArrayList<>();
	
	public OuterCompatibility( String pathOfProject,String version){

		this.projectName = pathOfProject;
		this.version = version;
		
	}
	
	public boolean jdkCompatibility(String Version){
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);
		
		String javaCoreVersion = null;
		switch (Version) {
		case "1.1":
			javaCoreVersion = JavaCore.VERSION_1_4;
			break;
		case "1.2":
			javaCoreVersion = JavaCore.VERSION_1_4;
			break;
		case "1.3":
			javaCoreVersion = JavaCore.VERSION_1_4;
			break;
		case "1.4":
			javaCoreVersion = JavaCore.VERSION_1_4;
			break;
		case "1.5":
			javaCoreVersion = JavaCore.VERSION_1_5;
			break;
		case "1.6":
			javaCoreVersion = JavaCore.VERSION_1_6;
			break;
		case "1.7":
			javaCoreVersion = JavaCore.VERSION_1_7;
			break;
		default:
			javaCoreVersion = JavaCore.VERSION_1_7;
			break;
		}
		
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, javaCoreVersion);
		parser.setCompilerOptions(complierOptions);
				
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
					
						
		JavaVersionCompatibilityRequestor requertor = new JavaVersionCompatibilityRequestor();
		
		ReadFile readFile = new ReadFile(projectName);
		List<String> filelist = readFile.readJavaFiles();
				
		/*		ReadFile readTestedFile = new ReadFile(pathOfTestComponet);
				filelist.addAll(readTestedFile.readJavaFiles());*/
						
		//////////////////////
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
		/////////////////////
						
						
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
						
		//parser.setEnvironment(null, null, null, true);
		parser.setEnvironment(jarpathEntries, null, null, true);
						
		parser.createASTs(sourceFilePaths,  null, new String[0], requertor, null);
		if (requertor.getuncompatibilityFiles().size()==0) {
			return true;
		}
		uncompatibilityfileList.addAll(requertor.getuncompatibilityFiles());
		
		if (uncompatibilityfileList.size() == 0) {
			return true;
		}
		return false;
	}
	
	public boolean jarCompatibility(String jarPath, String dependPath){
		
		List<JarClassModel> list = null;
    	try {

			list = AnalysisJarFile.getJarMethod(jarPath,dependPath);
			//list = AnalysisJarFile.getJarMethod("D:\\eclipse\\dropins\\lib\\mysql-connector-java-5.1.35-bin.jar");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
		
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);		
	
		
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
				
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
					
						
		OuterCompatibilityRequestor requertor = new OuterCompatibilityRequestor(projectName, null,list);
		
		ReadFile readFile = new ReadFile(projectName);
		List<String> filelist = readFile.readJavaFiles();
				
		/*		ReadFile readTestedFile = new ReadFile(pathOfTestComponet);
				filelist.addAll(readTestedFile.readJavaFiles());*/
						
		//////////////////////
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
		/////////////////////						
						
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
						
		//parser.setEnvironment(null, null, null, true);
		parser.setEnvironment(jarpathEntries, null, null, true);
						
		parser.createASTs(sourceFilePaths,  null, new String[0], requertor, null);
		
		uncompatibilityClassModels.addAll(requertor.getunCompatibilityList());
		
		if (uncompatibilityClassModels.size()==0) {
			return true;
		}
		
		return false;
	}

	public List<String> getuncompatibilityfileList() {
		return uncompatibilityfileList;
	}

	public List<JarClassModel> getUncompatibilityClassModels() {
		return uncompatibilityClassModels;
	}

	public void setUncompatibilityClassModels(List<JarClassModel> uncompatibilityClassModels) {
		this.uncompatibilityClassModels = uncompatibilityClassModels;
	}
	
}

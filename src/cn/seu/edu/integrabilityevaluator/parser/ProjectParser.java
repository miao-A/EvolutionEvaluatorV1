package cn.seu.edu.integrabilityevaluator.parser;



import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import cn.seu.edu.integrabilityevaluator.model.JarClassModel;

public class ProjectParser {

	private ASTParser parser;
	private String pathOfProject;
	private String projectName;
	private String version;
	
	
	public  ProjectParser(String pathOfProject,String projectName,String version) {
		// TODO Auto-generated constructor stub
		this.pathOfProject = pathOfProject;
		this.projectName = projectName;
		this.version = version;	
		
	}	
	
	public void parser()  {
		// create a AST parser
		parser = ASTParser.newParser(AST.JLS4);
	
		Map<String,String> compilerOptions= JavaCore.getDefaultOptions();
//		compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(compilerOptions);
		// set the environment for the AST parsers
		//String libPath = pathOfLib;
		ReadFile readFile = new ReadFile(pathOfProject);
		
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
//		String[] jarpathEntries = {pathOfProject};
		
		List<String> javafilelist = readFile.readJavaFiles();		
//		String[] sourcepathEntries = javafilelist.toArray(new String[javafilelist.size()]);
		String[] sourcepathEntries = {pathOfProject};

		parser.setEnvironment(jarpathEntries, sourcepathEntries, null, true);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		
	}	
	
	public void runDectors(){
		parser();
		runExtensiblityDectector();
		parser(); 
		//runChangeabilityDector();	
		runSubstitutabilityOfClassDector();
	}
	
	private void runExtensiblityDectector(){
		Extensibility extensibility = new Extensibility(parser, pathOfProject,projectName,version);		
	}
	
	public  void runChangeabilityDector(){
		Substitutability changeability = new Substitutability(parser, pathOfProject,projectName,version);
	}
	
	public void runSubstitutabilityOfClassDector(){
		SubstitutabilityOfClass substitutabilityOfClass = new SubstitutabilityOfClass(parser, pathOfProject, projectName, version);
	}
	
	public void runOuterCompatibilityDectector(){
		OuterCompatibility outerCompatibility = new OuterCompatibility(pathOfProject,version);
		
		String jarPath = "D:\\test\\jfreechart-1.0.19.jar";
		String dependPath = "D:\\test\\TestJar";
		outerCompatibility.jarCompatibility(jarPath, dependPath);
		List<JarClassModel> lists = outerCompatibility.getUncompatibilityClassModels();
		for (JarClassModel jarClassModel : lists) {
			System.out.println(jarClassModel.getClassName());
			System.out.println(jarClassModel.getmethod().get(0).getMethodName());
		}
		
	}
	
	/*public void runInnerCompatibilityDectector(String projectPath,String pathOne,String version){
		InnerCompatibility InnerCompatibility = new InnerCompatibility(projectPath,pathOne);
	}*/

	public void getInfoOfProject() {
		System.out.println("InfoOfProject"+pathOfProject);				
	}
	
	public void getExtensibilityInfo(){
		
		Extensibility extensibility = new Extensibility(parser, pathOfProject,projectName,version);
	
	}
	
	
	
	public void getChangeabilityInfo(){		
	
		Substitutability changeability = new Substitutability(parser, pathOfProject,projectName,version);
	}	
}






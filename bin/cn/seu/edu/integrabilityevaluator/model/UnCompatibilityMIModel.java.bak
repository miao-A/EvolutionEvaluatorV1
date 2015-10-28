package cn.seu.edu.integrabilityevaluator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.compiler.IProblem;

public class UnCompatibilityMIModel {
	private String fromPackageName;
	private String fromClassName;
	private String methodName;
	private String defaultArguments;
	private String realArguments;
/*	private List<String> defaultArgumentList = new ArrayList<>();
	private List<String> realArgumentList = new ArrayList<>();*/
	
	private String inPackageName;
	private String pathOfFile;
	
	public UnCompatibilityMIModel(String fromPackageName,String methodName,String defaultArgument,String realArgumentList,String inPackageName,String pathOfFile){

		this.setFromPackageName(fromPackageName);
		this.setMethodName(methodName);
		this.setDefaultArguments(defaultArgument);
		this.setRealArguments(realArgumentList);
		this.setInPackageName(inPackageName);
		this.setPathOfFile(pathOfFile);	
		this.setFromClassName(extractNameFromJavaFilePath(pathOfFile));
	}

	public String getFromPackageName() {
		return fromPackageName;
	}

	public void setFromPackageName(String fromPackageName) {
		this.fromPackageName = fromPackageName;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public String getDefaultMethodName() {
		return methodName+"("+defaultArguments+")";
	}
	
	public String getRealMethodName() {
		return methodName+"("+realArguments+")";
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDefaultArguments() {
		return defaultArguments;
	}

	public void setDefaultArguments(String defaultArguments) {
		this.defaultArguments = defaultArguments;
	}

	public String getRealArguments() {
		return realArguments;
	}

	public void setRealArguments(String realArguments) {
		this.realArguments = realArguments;
	}

	public String getInPackageName() {
		return inPackageName;
	}

	public void setInPackageName(String inPackageName) {
		this.inPackageName = inPackageName;
	}

	public String getPathOfFile() {
		return pathOfFile;
	}

	public void setPathOfFile(String pathOfFile) {
		this.pathOfFile = pathOfFile;
	}
	
	public void getMessage(){
		System.out.println("-------------------------------------------------");
		System.out.println(fromPackageName);
		System.out.println(methodName+"("+defaultArguments+")");
		System.out.println(methodName+"("+realArguments+")");
		System.out.println(inPackageName);
		System.out.println(pathOfFile);
		System.out.println("-------------------------------------------------");
	}
	
	public String extractNameFromJavaFilePath(String pathOfSourceFile) {
		// input: F:\jEditor0.2\editor\Animal.java
		// output: Animal
		String javaFileName = "";
		String temp[] = pathOfSourceFile.trim().split("\\\\");
		if (temp.length > 0) {
			String fullName = temp[temp.length - 1].trim();
			if (fullName.length() > ".java".length()) {
				int lastIndexOf = fullName.lastIndexOf(".java");
				if (lastIndexOf > 0) {
					javaFileName = fullName.substring(0, lastIndexOf);
				}
			}
		}
		return javaFileName;
	}

	public String getFromClassName() {
		return fromClassName;
	}

	public void setFromClassName(String fromClassName) {
		this.fromClassName = fromClassName;
	}

	
	
	
}

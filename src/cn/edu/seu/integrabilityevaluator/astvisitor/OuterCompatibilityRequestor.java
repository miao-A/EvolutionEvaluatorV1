package cn.edu.seu.integrabilityevaluator.astvisitor;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import cn.edu.seu.integrabilityevaluator.model.JarClassModel;



public class OuterCompatibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	private List<JarClassModel> jarList = new ArrayList<>();
	private List<JarClassModel> unCompatibilityJarList = new ArrayList<>();
	
	public OuterCompatibilityRequestor(String projectName,String version, List<JarClassModel> list){
		this.projectName = projectName;
		this.version = version;
		this.jarList = list;
	}	
	
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		OuterCompatibilityVisitor visitor = new OuterCompatibilityVisitor(jarList);
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
		this.unCompatibilityJarList.addAll(visitor.getunCompatibilityList());		
	}

	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub		
	}
	
	public List<JarClassModel> getunCompatibilityList() {
		return unCompatibilityJarList;
	}
	
	

}

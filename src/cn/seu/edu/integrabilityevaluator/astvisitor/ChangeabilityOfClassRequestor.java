package cn.seu.edu.integrabilityevaluator.astvisitor;

import java.text.DecimalFormat;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;



public class ChangeabilityOfClassRequestor extends FileASTRequestor {
	
	private String projectName;
	private String version;	
	
	public ChangeabilityOfClassRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	
	
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		//ChangeabilityVisitor visitor = new ChangeabilityVisitor(projectName, version);
		ChangeabilityOfClassVisitor visitor = new ChangeabilityOfClassVisitor(projectName, version);
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}			

	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}	

}

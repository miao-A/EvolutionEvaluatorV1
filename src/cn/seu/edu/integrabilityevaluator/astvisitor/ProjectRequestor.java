package cn.seu.edu.integrabilityevaluator.astvisitor;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;



public class ProjectRequestor extends FileASTRequestor{
	
	private String projectString;
	private String versionString;
	
	

	public ProjectRequestor(){
		super();
	}
		@Override
		public void acceptAST(String sourceFilePath, CompilationUnit ast) {
			//this.sourceFilePath = sourceFilePath;	
			ProjectVisitor visitor = new ProjectVisitor(projectString,versionString);
			ast.accept(visitor);
			
			super.acceptAST(sourceFilePath, ast);
		}
}

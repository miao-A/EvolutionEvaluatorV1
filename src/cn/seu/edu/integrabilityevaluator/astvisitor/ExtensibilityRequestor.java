package cn.seu.edu.integrabilityevaluator.astvisitor;


import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

public class ExtensibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	
	public ExtensibilityRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		ExtensibilityVisitor visitor = new ExtensibilityVisitor(projectName,version);
/*		CompilationUnit compilationUnit = AstUnit.getCompilationUnit(sourceFilePath);
		compilationUnit.accept(visitor);*/
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
	}

}

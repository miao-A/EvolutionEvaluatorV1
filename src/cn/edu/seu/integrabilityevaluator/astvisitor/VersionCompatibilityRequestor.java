package cn.edu.seu.integrabilityevaluator.astvisitor;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import cn.edu.seu.integrabilityevaluator.model.AbstractClassModel;


public class VersionCompatibilityRequestor extends FileASTRequestor {


	private List<AbstractClassModel> typeModels;
	
	public VersionCompatibilityRequestor(){
		typeModels = new LinkedList<>();
	}	

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		VersionCompatibilityVisitor visitor = new VersionCompatibilityVisitor();
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
		if (visitor.getTypeModel()!=null) {
			typeModels.add(visitor.getTypeModel());
		}
		
	}
	
	public List<AbstractClassModel> getTypeModels(){
		return typeModels;
	}	
	
}

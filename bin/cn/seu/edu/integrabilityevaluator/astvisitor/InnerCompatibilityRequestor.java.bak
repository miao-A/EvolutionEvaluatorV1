package cn.seu.edu.integrabilityevaluator.astvisitor;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.UnCompatibilityMIModel;



public class InnerCompatibilityRequestor extends FileASTRequestor {

	private String projectName;
	private String version;
	private List<String> packageList=null;
	List<UnCompatibilityMIModel> unCompatibilityMIModels = new ArrayList<>();
	
	/*public InnerCompatibilityRequestor(String projectName,String version){
		this.projectName = projectName;
		this.version = version;
	}	*/
	
	public InnerCompatibilityRequestor(List<String> packageList) {
		// TODO Auto-generated constructor stub
		this.packageList = packageList;
	}

	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		InnerCompatibilityVisitor visitor = new InnerCompatibilityVisitor(packageList);
		ast.accept(visitor);
		super.acceptAST(sourceFilePath, ast);
		unCompatibilityMIModels.addAll(visitor.getUncompatibilityMiModels());
	}

	
	public List<UnCompatibilityMIModel> getuncompatibilityMIModels(){
		return unCompatibilityMIModels;
	}
	
	public void ShowInfoOfChangeability() {
		// TODO Auto-generated method stub
		
	}
	
	

}

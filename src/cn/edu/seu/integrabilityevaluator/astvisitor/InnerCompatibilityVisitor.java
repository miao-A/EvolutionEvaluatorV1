package cn.edu.seu.integrabilityevaluator.astvisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.edu.seu.integrabilityevaluator.model.AbstractClassModel;
import cn.edu.seu.integrabilityevaluator.model.MethodModel;
import cn.edu.seu.integrabilityevaluator.model.SingleVariableModel;
import cn.edu.seu.integrabilityevaluator.model.UnCompatibilityMIModel;

public class InnerCompatibilityVisitor extends ASTVisitor{
	
	private List<String> packageList = null;
	private List<UnCompatibilityMIModel> unCompatibilityMIModels = new ArrayList<>();

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	public InnerCompatibilityVisitor(List<String> packageList) {
		// TODO Auto-generated constructor stub
		this.packageList = packageList;
	}	
	
		
	public boolean visit(CompilationUnit node){
//		System.out.println(node.getLength());
/*		Message[] messages = node.getMessages();
		for (Message message : messages) {
			System.out.println(message.getMessage());
		}*/
		
		IProblem[] iProblems = node.getProblems();
		for (IProblem iProblem : iProblems) {
			if (iProblem.isError()) {
				if(iProblem.getID()==IProblem.ParameterMismatch){					
					//System.out.println("##"+node.getPackage());
					if (iProblem.getArguments()[0].startsWith(node.getPackage().getName().toString())) {
						continue;
					}
					
					for (String string :packageList) {
						if (iProblem.getArguments()[0].startsWith(string)) {
							UnCompatibilityMIModel unCompatibilityMIModel = new UnCompatibilityMIModel(iProblem.getArguments()[0], 
									iProblem.getArguments()[1], iProblem.getArguments()[2], iProblem.getArguments()[3],
									node.getPackage().getName().toString(),String.valueOf(iProblem.getOriginatingFileName()));
							unCompatibilityMIModels.add(unCompatibilityMIModel);

						}
					}
					
					/*if (packageList.contains(node.getPackage().getName().toString())) {
						
											
						UnCompatibilityMIModel unCompatibilityMIModel = new UnCompatibilityMIModel(iProblem.getArguments()[0], 
								iProblem.getArguments()[1], iProblem.getArguments()[2], iProblem.getArguments()[3],
								node.getPackage().getName().toString(),String.valueOf(iProblem.getOriginatingFileName()));
						unCompatibilityMIModels.add(unCompatibilityMIModel);
					}	*/				
				}			
			}
	
		}
		return true;
	}
	
	
	public List<UnCompatibilityMIModel> getUncompatibilityMiModels(){
		return unCompatibilityMIModels;
	}
	
}

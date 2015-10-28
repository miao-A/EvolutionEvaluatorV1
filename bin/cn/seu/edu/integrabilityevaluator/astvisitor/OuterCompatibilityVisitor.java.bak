package cn.seu.edu.integrabilityevaluator.astvisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.seu.edu.integrabilityevaluator.model.JarClassModel;
import cn.seu.edu.integrabilityevaluator.model.JarMethodModel;

public class OuterCompatibilityVisitor extends ASTVisitor{

	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
    String NORMAL_METHOD= "waitequalsnotifynotifyAlltoStringhashCodegetClass"; 
    private List<JarClassModel> jarList = new ArrayList<>();
    private List<JarClassModel> unCompatibilityJarList = new ArrayList<>();
    private String packageName;
    private String className;

	public OuterCompatibilityVisitor(List<JarClassModel> jarList) {
		this.jarList = jarList;
		// TODO Auto-generated constructor stub
	}

	public boolean visit(CompilationUnit node){
		packageName = node.getPackage().getName().getFullyQualifiedName();
		
		/*IProblem[] iProblems = node.getProblems();
		for (IProblem iProblem : iProblems) {
			if (iProblem.isError()) {
				System.out.println(iProblem.getOriginatingFileName());
				System.out.println(iProblem.getMessage());
				String[] strings = iProblem.getArguments();
				for (String string : strings) {
					System.out.println(string);
				}
				System.out.println();
				UnCompatibilityMIModel unCompatibilityMIModel = new UnCompatibilityMIModel(iProblem.getArguments()[0], 
						iProblem.getArguments()[1], iProblem.getArguments()[2], iProblem.getArguments()[3],
						node.getPackage().getName().toString(), String.valueOf(iProblem.getOriginatingFileName()));
				if (unCompatibilityJarList2.indexOf(String.valueOf(iProblem.getOriginatingFileName()))==-1) {
					unCompatibilityJarList2.add(String.valueOf(iProblem.getOriginatingFileName()));
				}
				
			}
		}*/
		return true;

	}
	
	public boolean visit(TypeDeclaration node){
		
		className = node.getName().getFullyQualifiedName();
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		
		IMethodBinding binding = node.resolveMethodBinding();
		if (binding == null) {
//				System.out.println("MethodInvocation binding is null in class:" +classString );				
				return true;
		}	
	
//		System.out.println("---------------------------------------------------------------");
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
		IMethodBinding iMethodBinding = binding.getMethodDeclaration();
		/*System.out.println("packagename:" + importpackageName);
		System.out.println("classname:"+binding.getDeclaringClass().getName());*/
		
			
		String methodname = iMethodBinding.getName();
/*		System.out.println("methodname:" + methodname );
*/		
		
		if (NORMAL_METHOD.indexOf(methodname) <0)
        {
			JarClassModel jarClassModel = new JarClassModel(importpackageName, importpackageName+"."+binding.getDeclaringClass().getName());
           JarMethodModel jarMethodModel = new JarMethodModel(iMethodBinding.getName());
           List<String> paraList = new ArrayList<>();
           List<Expression> list = node.arguments();
           for (Expression expression : list) {
        	   ITypeBinding iTypeBinding = expression.resolveTypeBinding();
/*        	   System.out.println(iTypeBinding.getQualifiedName());
*/        	   paraList.add(iTypeBinding.getQualifiedName());
           }
			
			jarMethodModel.setParameters(paraList);
			jarClassModel.addmethod(jarMethodModel);
			jarClassModel.setFromClass(packageName +"."+className);
			
			if (!compatibiliyInJar(jarClassModel)) {
				unCompatibilityJarList.add(jarClassModel);			
			}
        }	
		
//		System.out.println("---------------------------------------------------------------");
				
		return true;
		
	}
	
	public boolean compatibiliyInJar(JarClassModel jarClassModel) {
		if (jarList.contains(jarClassModel)) {
			JarMethodModel miMethodModel = jarClassModel.getmethod().get(0);
 			List<JarMethodModel> methodList = jarList.get(jarList.indexOf(jarClassModel)).getmethod();
			if (methodList.contains(miMethodModel)) {
				return true;
			}else {
				return false;
			}			
		}		
		return true;
	}
	
	public List<JarClassModel> getunCompatibilityList() {
		return unCompatibilityJarList;
	}
	
}

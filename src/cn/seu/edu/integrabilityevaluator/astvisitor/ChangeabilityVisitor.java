/*package cn.seu.edu.integrabilityevaluator.astvisitor;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.seu.edu.integrabilityevaluator.dbconnect.ChangeabilityConnector;



public class ChangeabilityVisitor extends ASTVisitor{
	

	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;
	private ArrayList<String> importDeclarationList;
	private HashSet<String> importPackageStrings;
	private static int count=0;
	private String projectName;
	private String version;
	
	
	public ChangeabilityVisitor(String projectName, String version) {
		// TODO Auto-generated constructor stub
		importDeclarationList = new ArrayList<String>();
		importPackageStrings = new HashSet<String>();
		this.projectName = projectName;
		this.version = version;

	}
	
	public boolean visit(PackageDeclaration node) {		
		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}

	
	public boolean visit(TypeDeclaration node){
		
		classString = node.getName().toString();
		
//		System.out.println("class Declaration: "+ classString);		
		return true;
	}

	public boolean visit(SimpleType node){

		ITypeBinding binding = node.resolveBinding();

		if (binding == null) {
			//System.out.println("simpleType binding is null in class:" +classString );
			return true;
		}
		String importpackageName = binding.getPackage().getName();
		if (!importpackageName.equals(packageString)) {
			importPackageStrings.add(importpackageName);
		}
		
		return true;
	}
	

	public boolean visit(MethodInvocation  node){
		IMethodBinding binding = node.resolveMethodBinding();
		if (binding == null) {
				System.out.println("MethodInvocation binding is null in class:" +classString );
				System.out.println(count++ );
				return true;
		}
	
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
			if (!importpackageName.equals(packageString)) {
				importPackageStrings.add(importpackageName);
		}
				
		return true;
	}

	
	
	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );
		ChangeabilityConnector connector = new ChangeabilityConnector(projectName,version);
		for (String string : importPackageStrings) {
			connector.importNameUpatedate(packageString, string);
			System.out.println("package "+ packageString + " have package "+string);
			System.out.println("Class "+ classString + " import package "+string);
		}		
		System.out.println("----------------------------------------------------------");
		
		importPackageStrings.clear();
	}
}
*/
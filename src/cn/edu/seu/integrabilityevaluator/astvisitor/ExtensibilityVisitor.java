package cn.edu.seu.integrabilityevaluator.astvisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.edu.seu.integrabilityevaluator.dbconnect.DBConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.ExtensibilityConnector;

public class ExtensibilityVisitor extends ASTVisitor {

	private String packageString;
	private String classString;
	private String classType;
	private ArrayList<String> classNameList;
	private ArrayList<String> classTypeList;
	
	private String projectName;
	private String version;
	
	private static int numofmethod=0;
	
	
	
	public ExtensibilityVisitor(String projectName,String version){

		classNameList = new ArrayList<String>();
		classTypeList = new ArrayList<String>();
		this.projectName = projectName;
		this.version = version;
		
	}
	

	
	
	public boolean visit(PackageDeclaration node) {		
//		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}

	
	
	public boolean visit(TypeDeclaration node){

		classString = node.getName().toString();

		
		if (node.modifiers().toString().contains("abstract")) {			
//			System.out.println("abstract class"+ node.getName());
			classType = "abstract";

		} else if (node.isInterface()) {
//			System.out.println("interface class:"+ node.getName());
			classType = "interface";
		}else {	
			System.out.println("concrete class:" + node.getName());
			classType = "concrete";
		}
		
		classNameList.add(classString);		
		classTypeList.add(classType);		

		return true;
	}
	

	
	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除	
		if (classNameList.size()==0) {
			return;
		}
		
		System.out.println("package "+ packageString + " have class "+classNameList.get(0));
		ExtensibilityConnector connector = new ExtensibilityConnector(projectName, version);
		connector.extendsibilityUpdateStatement(packageString, classNameList.get(0), classTypeList.get(0));
		
		for (int i=1; i<classNameList.size(); i++ ) {
			classNameList.get(i);
			classTypeList.get(i);
			System.out.println("package "+ packageString + " have class "+classNameList.get(i));
			connector.extendsibilityUpdateStatement(packageString, classNameList.get(0)+"$"+classNameList.get(i), classTypeList.get(i));
		}		
		
		classNameList.clear();
		classTypeList.clear();
	}
}






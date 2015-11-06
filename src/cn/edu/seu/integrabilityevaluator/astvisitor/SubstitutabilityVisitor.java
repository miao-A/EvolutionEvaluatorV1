package cn.edu.seu.integrabilityevaluator.astvisitor;



import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.edu.seu.integrabilityevaluator.dbconnect.SubstitutabilityConnector;


public class SubstitutabilityVisitor extends ASTVisitor{
	

	private String packageString;
	private String classString;
	private String methodDeclaString;
	private String methodInvoString;
	private String projectName;
	private String version;

	private HashSet<String> importStrings;
	
	
	public SubstitutabilityVisitor(String projectName, String version) {
		// TODO Auto-generated constructor stub
		importStrings = new HashSet<String>();
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

		ITypeBinding binding = (ITypeBinding) node.getName().resolveBinding();
////类级别耦合性检测
/*		binding.getPackage();
		binding.getQualifiedName();*/
		if (binding == null||binding.getPackage()==null) {
			return true;
		}
		String fullString = binding.getQualifiedName();		
		String importpackageName = binding.getPackage().getName();
		
		
		if (fullString.contains(importpackageName)) {
			String importClassName = fullString.substring(importpackageName.length()+1);
			if (importClassName.contains(".")) {
				importClassName = importClassName.substring(0, importClassName.indexOf('.'));
			}
			
			importStrings.add(importpackageName+"$"+importClassName);
		}
		
		
		return true;
	}
	
	public boolean visit(MethodInvocation node){
		IMethodBinding binding =  (IMethodBinding) node.getName().resolveBinding();
		if (binding == null) {
			return true;
		}
		String fullString = binding.getDeclaringClass().getQualifiedName();
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
		if(fullString.length()==0){
			return true;
		}
		String importClassName = fullString.substring(importpackageName.length()+1);
		if (importClassName.contains(".")) {
			importClassName = importClassName.substring(0, importClassName.indexOf('.'));
		}
		
		importStrings.add(importpackageName+"$"+importClassName);
		return true;
	}

	public void endVisit(CompilationUnit node){
		//可用于数据库插入，数据库建成后上述get方法可删除
	
/*		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageString );
*/
		SubstitutabilityConnector connector = new SubstitutabilityConnector(projectName,version);
		for (String string : importStrings) {
			
			int index = string.indexOf('$');
			String ipn = string.substring(0, index);
			String icn = string.substring(index+1);
			if (classString == null) {
				continue;
			}

			if (packageString.equals(ipn)) {
				continue;
			}
			if (icn.contains("<")) {
				icn = icn.split("<")[0];
			}
			
			connector.importNameUpatedate(packageString, classString, ipn, icn);
		}
		
/*		System.out.println("----------------------------------------------------------");		
*/		importStrings.clear();
	}
}

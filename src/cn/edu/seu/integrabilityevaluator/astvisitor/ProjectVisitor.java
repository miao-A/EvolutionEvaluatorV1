package cn.edu.seu.integrabilityevaluator.astvisitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
* @author   Yam

*@version   1.1	

*@see     项目访问器

*/
public class ProjectVisitor extends ASTVisitor {
	
	private String packageString;
	private String classString;
	private String projectString;
	private String versionString;
	
	public ProjectVisitor(String projectString,String versionString){
		this.projectString = projectString;
		this.versionString = versionString;
		
	}
		
	public boolean visit(PackageDeclaration node) {		
//		System.out.println("PackageName:" + node.getName());
		packageString = node.getName().toString();
		return true;		
	}
	
	public boolean visit(TypeDeclaration node) {
		classString = node.getName().toString();
		if (node.modifiers().toString().contains("abstract")) {			
			System.out.println("abstract class"+ node.getName());			
		} else if (node.isInterface()) {
			System.out.println("interface class:"+ node.getName());
		}else {
			System.out.println("normal class:" + node.getName());			
		}
		
		return true;
	}
	/**
	* @author   Yam

	*@version   1.1

	*@see     结束访问后输出包与类信息
	*
	*@param    编译单元

	*@return   空

	*/
	
	public void endVisit(CompilationUnit node){
		System.out.println("package "+ packageString + " have class "+classString);
	}
}

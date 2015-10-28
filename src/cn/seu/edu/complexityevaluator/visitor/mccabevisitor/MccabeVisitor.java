/* 作者 何磊
 * 时间 2015/05/20
 * 说明 主要是计算方法层次的圈复杂度  
 *    目前检测的圈复杂度的操作符为 if while switch case for catch throw return 三目运算符  || &&
 * 版本 2.0  加入方法的初始化圈复杂度值
 * */
package cn.seu.edu.complexityevaluator.visitor.mccabevisitor;

import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;

public class MccabeVisitor extends AbstractVisitor {
    //初始化为0
	private   int  mccabeNum=0;
	public int getMccabeNum() {
		return mccabeNum;
	}
	private Bag<String> mccabeOperators = new HashBag<String>();
	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}

	private void operator(ASTNode node, Object name) {
		addOperator(typeName(node) + "\t" + name.toString());
	}
	private void addOperator(String id) {
                mccabeNum++;
			mccabeOperators.add(id);
		}

	private String typeName(ASTNode node) {

		return node.getClass().toString()
				.replace("class org.eclipse.jdt.core.dom.", "");
	}

	@Override
	public void endVisit(CompilationUnit node) {
		super.endVisit(node);
	}
	//开始扫描
	@Override
	public boolean visit(CompilationUnit node) {
		// System.out.println(pacakgeFullyQuallifedName);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		// System.out.println(activeType);
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		//增加方法初始化的圈复杂度1
		operator(node);
		return true;
	}
	// 获取方法的相关数据
	@Override
	public boolean visit(InfixExpression node) {
		if(node.getOperator()==InfixExpression.Operator.CONDITIONAL_AND||node.getOperator()==InfixExpression.Operator.CONDITIONAL_OR){
		operator(node, node.getOperator());
		}
		return true;
	}
	@Override
	public boolean visit(CatchClause node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		operator(node);
		return true;
	}
	@Override
	public boolean visit(ForStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(IfStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(SwitchCase node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		operator(node);
		return true;
	}
	@Override
	public boolean visit(DoStatement node) {
		operator(node);
		return true;
	}
}


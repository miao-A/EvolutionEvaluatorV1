/* 
 * 日期 2015-03-29
 * 描述：主要是计算系统层的Halstead复杂度
 *      其实也看以看成是语句级别的复杂度
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.visitor.halsteadvisitor;
import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;

public class HalsteadVisitor extends AbstractVisitor{

	private   Bag<String> operators = new HashBag<String>();
	public Bag<String> getOperators() {
		return operators;
	}

	public void setOperators(Bag<String> operators) {
		this.operators = operators;
	}

	public Bag<String> getOperands() {
		return operands;
	}

	public void setOperands(Bag<String> operands) {
		this.operands = operands;
	}

	private  Bag<String> operands = new HashBag<String>();
	
	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}
	
	private void operator(ASTNode node, Object name){
		addOperator(typeName(node) + "\t" + name.toString());
	}

	private void addOperator(String id) {
		operators.add(id);
		//System.out.println("operator: " + id);
	}
	
	private void operand(ASTNode node, Object name){
		addOperand(typeName(node) + "\t" + name.toString());
	}

	private void addOperand(String id) {
		operands.add(id);
		//System.out.println("operand:  " + id);
	}

	private String typeName(ASTNode node) {
		
		return node.getClass().toString().
				replace("class org.eclipse.jdt.core.dom.", "");
	}

	@Override
	public void endVisit(CompilationUnit node) {
		super.endVisit(node);
	}
    /*
	 * Interesting operators...
	 */
	@Override
	public boolean visit(PackageDeclaration node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		operator(node, node.getName());
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(InfixExpression node) {
		operator(node, node.getOperator());
		return true;
	}

	@Override
	public boolean visit(PostfixExpression node) {
		operator(node, node.getOperator());
		return true;
	}

	@Override
	public boolean visit(PrefixExpression node) {
		operator(node, node.getOperator());
		return true;
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		operator(node, node.booleanValue());
		return true;
	}

	// a cast will get counted twice
	@Override
	public boolean visit(CastExpression node) {
		operator(node, node.getType());
		return true;
	}

	@Override
	public boolean visit(ConstructorInvocation node) {
		operator(node, node.arguments().size());
		return true;
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		operator(node, node.arguments().size());
		return true;
	}

	/*
	 * Skipped
	 */

	@Override
	public boolean visit(Block node) {
		// operator(node);
		return true;
	}

	@Override
	public boolean visit(EmptyStatement node) {
		operator(node);
		return true;
	}

	// //simpletypes contain simplenames
	// @Override
	public boolean visit(SimpleType node) {
		operator(node, node.getName());
		return true;
	}

	//
	// //typically just a SimpleName and maybe a comma if theres more than one
	// 变量申明片段
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		operator(node);
		return true;
	}

	//
	//
	/*
	 * Operands
	 */

	@Override
	public boolean visit(CharacterLiteral node) {
		operand(node, node.charValue());
		return true;
	}

	@Override
	// 原先是操作数处理，现在改为操作符
	public boolean visit(Modifier node) {
		operator(node, node.getKeyword());
		return true;
	}

	@Override
	public boolean visit(NumberLiteral node) {
		operand(node, node.getToken());
		return true;
	}

	@Override
	// 基类9种
	public boolean visit(PrimitiveType node) {
		operator(node, node.getPrimitiveTypeCode());
		return true;
	}

	// count the qualified name, but don't look any further
	// 限定类型 更改为操作符处理
	@Override
	public boolean visit(QualifiedName node) {
		operator(node, node.getFullyQualifiedName());
		return false;
	}

	@Override
	public boolean visit(SimpleName node) {
		operand(node, node.getIdentifier());
		return true;
	}

	@Override
	public boolean visit(StringLiteral node) {
		operand(node, node.getLiteralValue());
		return true;
	}

	/*
	 * Uninteresting operators... 修改了一些，去除了注解这些不关注的类型
	 */

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ArrayAccess node) {
		operator(node);
		return true;
	}

	//
	@Override
	public boolean visit(ArrayCreation node) {
		operator(node);
		return true;
	}

	@Override
	// 没有做统一处理，以后可能需要修改
	public boolean visit(ArrayInitializer node) {
		operator(node, node.expressions());
		return true;
	}

	@Override
	public boolean visit(ArrayType node) {
		operator(node);
		return true;
	}

	@Override
	// 赋值表达式
	public boolean visit(Assignment node) {
		operator(node, node.getOperator());
		return true;
	}

	@Override
	public boolean visit(BreakStatement node) {
		operator(node);
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
	public boolean visit(ContinueStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(DoStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(EnumDeclaration node) {
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
	public boolean visit(Initializer node) {
		operator(node, node.getBody());
		return true;
	}

	//
	@Override
	public boolean visit(InstanceofExpression node) {
		operator(node);
		return true;
	}

	//
	@Override
	public boolean visit(LabeledStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(NullLiteral node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		operator(node);
		return true;
	}

	//

	@Override
	public boolean visit(SuperMethodInvocation node) {
		operator(node);
		return true;
	}

	//
	@Override
	public boolean visit(SwitchCase node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(SwitchStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ThisExpression node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(ThrowStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(TryStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		operator(node);
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		operator(node);
		return true;
	}
}

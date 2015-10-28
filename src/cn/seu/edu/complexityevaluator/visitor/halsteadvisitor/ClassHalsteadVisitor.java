/* 
 * 日期 2015/05/20
 * 版本 4.0
 * 描述 主要是类级别的halstead的信息收集
 * 3.0 修改问题：主要是内部类的bug修复,修复内部类遍历完后，再遍历其他的方法是所存储的类名还是内部类的问题，这个里面没有考虑匿名内部类的问题
 * 4.0 修复问题：主要修复匿名内部类的处理问题
 * */
package cn.seu.edu.complexityevaluator.visitor.halsteadvisitor;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
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

public class ClassHalsteadVisitor extends AbstractVisitor {
	// 用来存贮内部类的类名，解决内部类的遍历越界问题
	private Stack<SimpleName> pool = new Stack<SimpleName>();
	// 用来存贮类的操作符
	private Bag<String> typeOperators = new HashBag<String>();

	public Bag<String> getTypeOperators() {
		return typeOperators;
	}

	public Bag<String> getTypeOperands() {
		return typeOperands;
	}

	// 用来存贮类的操作数
	private Bag<String> typeOperands = new HashBag<String>();
	// 用来存贮halstead的4个基本测度值
	private Map<String, String> typeHalsteadInfo = new HashMap<String, String>();

	public Map<String, String> getTypeHalsteadInfo() {
		return typeHalsteadInfo;
	}

	public void setTypeHalsteadInfo(Map<String, String> typeHalsteadInfo) {
		this.typeHalsteadInfo = typeHalsteadInfo;
	}

	// 包全名
	private String pacakgeFullyQuallifedName;
	// 当前类名
	private SimpleName activeType;


	// 添加操作符，这个是只添加他的结构，主要是针对那么结构相同的节点，还有关键字
	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}

	// 添加操作符，这里不管是添加操作符的节点类型，还要把他的具体内容添加进来
	private void operator(ASTNode node, Object name) {
		addOperator(typeName(node) + "\t" + name.toString());
	}

	// 真真的添加操作符的操作
	private void addOperator(String id) {
		// 添加类的扫描结果
		if (activeType != null) {
			String typepath = pacakgeFullyQuallifedName + ":" + activeType;
			if (typeHalsteadInfo.containsKey(typepath)) {
				// 唯一操作符数
				String operatorNum1 = typeHalsteadInfo.get(typepath).split(":")[0];
				// 唯一操作数数
				String operandNum1 = typeHalsteadInfo.get(typepath).split(":")[1];
				// 总操作符数
				String operatorNum2 = typeHalsteadInfo.get(typepath).split(":")[2];
				// 总操作数数
				String operandNum2 = typeHalsteadInfo.get(typepath).split(":")[3];
				if (typeOperators.contains(typepath + "\t" + id)) {
					int updateOperatorNum2 = Integer.parseInt(operatorNum2) + 1;
					String updateNum = operatorNum1 + ":" + operandNum1 + ":"
							+ updateOperatorNum2 + ":" + operandNum2;
					typeHalsteadInfo.put(typepath, updateNum);
				} else {
					int updateOperatorNum1 = Integer.parseInt(operatorNum1) + 1;
					int updateOperatorNum2 = Integer.parseInt(operatorNum2) + 1;
					String updateNum = updateOperatorNum1 + ":" + operandNum1
							+ ":" + updateOperatorNum2 + ":" + operandNum2;
					typeHalsteadInfo.put(typepath, updateNum);
				}
			} else {
				String setNum = 1 + ":" + 0 + ":" + 1 + ":" + 0;
				typeHalsteadInfo.put(typepath, setNum);
			}
			typeOperators.add(typepath + "\t" + id);
		}
	}

	private void operand(ASTNode node, Object name) {
		addOperand(typeName(node) + "\t" + name.toString());
	}

	private void addOperand(String id) {
		// operands.add(id);
		// System.out.println("operand:  " + id);
		// 类的扫描结果的存贮
		if (activeType != null) {
			String typepath = pacakgeFullyQuallifedName + ":" + activeType;
  			//测试用
			//System.out.println(typepath+"   "+id);
			if (typeHalsteadInfo.containsKey(typepath)) {
				// 唯一操作符数
				String operatorNum1 = typeHalsteadInfo.get(typepath).split(":")[0];
				// 唯一操作数数
				String operandNum1 = typeHalsteadInfo.get(typepath).split(":")[1];
				// 总操作符数
				String operatorNum2 = typeHalsteadInfo.get(typepath).split(":")[2];
				// 总操作数数
				String operandNum2 = typeHalsteadInfo.get(typepath).split(":")[3];
				if (typeOperands.contains(typepath + "\t" + id)) {
					int updateOperandNum2 = Integer.parseInt(operandNum2) + 1;
					String updateNum = operatorNum1 + ":" + operandNum1 + ":"
							+ operatorNum2 + ":" + updateOperandNum2;
					typeHalsteadInfo.put(typepath, updateNum);
				} else {
					int updateOperandNum1 = Integer.parseInt(operandNum1) + 1;
					int updateOperandNum2 = Integer.parseInt(operandNum2) + 1;
					String updateNum = operatorNum1 + ":" + updateOperandNum1
							+ ":" + operatorNum2 + ":" + updateOperandNum2;
					typeHalsteadInfo.put(typepath, updateNum);
				}
			} else {
				String setNum = 0 + ":" + 1 + ":" + 0 + ":" + 1;
				typeHalsteadInfo.put(typepath, setNum);
			}
			typeOperands.add(typepath + "\t" + id);
		}
	}

	private String typeName(ASTNode node) {

		return node.getClass().toString()
				.replace("class org.eclipse.jdt.core.dom.", "");
	}

	/*
	 * Interesting operators...
	 */

	@Override
	public boolean visit(PackageDeclaration node) {
		this.pacakgeFullyQuallifedName = node.getName().getFullyQualifiedName();
		// System.out.println(pacakgeFullyQuallifedName);
		operator(node);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		this.activeType = node.getName();
		pool.push(activeType);
		// System.out.println(activeType);
		operator(node, node.getName());
		return true;
	}

	// 这里主要考虑要内部类的信息覆盖问题
	@Override
	public void endVisit(TypeDeclaration node) {
		// SimpleName type=node.getName();
		// 有内部类的情况
		if (pool.size() >= 2) {
			pool.pop();
			this.activeType = pool.get(pool.size() - 1);
		}else{
		// 如果只有一个主类的情况
			pool.clear();
			}
		super.endVisit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		// System.out.println(activeMethod);
		operator(node);
		return true;
	}
    
	@Override
	public boolean visit(ClassInstanceCreation node) {
		operator(node);
		return super.visit(node);
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
	// 这个就是标识符的节点
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

/* 作者 何磊 
 * 日期 2013-5-20
 * 描述 计算类层级的halstead数据
 * 问题：首先是数据结果的验证问题，其次是这个结果的阈值如何设定，以及与类层级怎么结合起来
 * 版本 3.0
 *  2.0  解决部分内部类的问题，以及方法层数据不对称问题（没处理匿名内部类）
 *  3.0  修复内部类中匿名内部类导致的方法名称不对问题，
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

import cn.seu.edu.complexityevaluator.util.MethodPathUtil;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;


public class MethodHalsteadVisitor extends AbstractVisitor {
	//主要是解决除匿名内部类外，类名问题
    private Stack<String> pool=new Stack<String>();
    //同样是解决匿名内部类的方法跳出到外部方法的问题
    private Stack<String> methodpool=new Stack<String>();
    //这个是存储匿名内部类的计数器
    private HashMap<String, Integer> counter = new HashMap<String, Integer>();
    //相当于一个list,以全路径名+具体的操作符或者操作数
	private Bag<String> methodOperators = new HashBag<String>();
	private Bag<String> methodOperands = new HashBag<String>();
	//key 全路径    map是 4个基本度量值 
	private Map<String, String> methodHalsteadInfo = new HashMap<String, String>();

	public Map<String, String> getMethodHalsteadInfo() {
		return methodHalsteadInfo;
	}

	public void setMethodHalsteadInfo(Map<String, String> methodHalsteadInfo) {
		this.methodHalsteadInfo = methodHalsteadInfo;
	}

	private String pacakgeFullyQuallifedName;//包全名
	private String activeType;//当前类名
	private String activeMethodPath;//当前方法全路径
	private String activeMethodName;//当前方法名
	private String activeMethodParameters;//当前方法参数
    private MethodPathUtil pathUtil=new MethodPathUtil();

	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}

	private void operator(ASTNode node, Object name) {
		addOperator(typeName(node) + "\t" + name.toString());
	}

	private void addOperator(String id) {
		// 添加方法的扫描结果
		if (activeType != null && activeMethodPath != null) {
			String methodpath = pacakgeFullyQuallifedName + ":" + activeType
					+ ":" + activeMethodPath;
			if (methodHalsteadInfo.containsKey(methodpath)) {
				// 唯一操作符数
				String operatorNum1 = methodHalsteadInfo.get(methodpath).split(
						":")[0];
				// 唯一操作数数
				String operandNum1 = methodHalsteadInfo.get(methodpath).split(
						":")[1];
				// 总操作符数
				String operatorNum2 = methodHalsteadInfo.get(methodpath).split(
						":")[2];
				// 总操作数数
				String operandNum2 = methodHalsteadInfo.get(methodpath).split(
						":")[3];
				if (methodOperators.contains(methodpath + "\t" + id)) {
					int updateOperatorNum2 = Integer.parseInt(operatorNum2) + 1;
					String updateNum = operatorNum1 + ":" + operandNum1 + ":"
							+ updateOperatorNum2 + ":" + operandNum2;
					methodHalsteadInfo.put(methodpath, updateNum);
				} else {
					int updateOperatorNum1 = Integer.parseInt(operatorNum1) + 1;
					int updateOperatorNum2 = Integer.parseInt(operatorNum2) + 1;
					String updateNum = updateOperatorNum1 + ":" + operandNum1
							+ ":" + updateOperatorNum2 + ":" + operandNum2;
					methodHalsteadInfo.put(methodpath, updateNum);
				}
			} else {
				String setNum = 1 + ":" + 0 + ":" + 1 + ":" + 0;
				methodHalsteadInfo.put(methodpath, setNum);
			}
			methodOperators.add(methodpath + "\t" + id);
		}
		// operators.add(id);
		// System.out.println("operator: " + id);
	}

	private void operand(ASTNode node, Object name) {
		addOperand(typeName(node) + "\t" + name.toString());
	}

	private void addOperand(String id) {
		// 方法的扫描结果存贮
		if (activeType != null && activeMethodPath != null) {
			String methodpath = pacakgeFullyQuallifedName + ":" + activeType
					+ ":" + activeMethodPath;
			//这个是用于数据调试所做的输出
			//System.out.println(methodpath+id);
			if (methodHalsteadInfo.containsKey(methodpath)) {
				// 唯一操作符数
				String operatorNum1 = methodHalsteadInfo.get(methodpath).split(
						":")[0];
				// 唯一操作数数
				String operandNum1 = methodHalsteadInfo.get(methodpath).split(
						":")[1];
				// 总操作符数
				String operatorNum2 = methodHalsteadInfo.get(methodpath).split(
						":")[2];
				// 总操作数数
				String operandNum2 = methodHalsteadInfo.get(methodpath).split(
						":")[3];
				if (methodOperands.contains(methodpath + "\t" + id)) {
					int updateoperandNum2 = Integer.parseInt(operandNum2) + 1;
					String updateNum = operatorNum1 + ":" + operandNum1 + ":"
							+ operatorNum2 + ":" + updateoperandNum2;
					methodHalsteadInfo.put(methodpath, updateNum);
				} else {
					int updateoperandNum1 = Integer.parseInt(operandNum1) + 1;
					int updateoperandNum2 = Integer.parseInt(operandNum2) + 1;
					String updateNum = operatorNum1 + ":" + updateoperandNum1
							+ ":" + operatorNum2 + ":" + updateoperandNum2;
					methodHalsteadInfo.put(methodpath, updateNum);
				}
			} else {
				String setNum = 0 + ":" + 1 + ":" + 0 + ":" + 1;
				methodHalsteadInfo.put(methodpath, setNum);
			}
			methodOperands.add(methodpath + "\t" + id);
		}
		// operands.add(id);
		// System.out.println("operand:  " + id);
		// 类的扫描结果的存贮
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
		operator(node);
		return true;
	}
//这里是不是应该保存的是operator（node）
	@Override
	public boolean visit(TypeDeclaration node) {
		this.activeType = node.getName().toString();
		pool.push(activeType);
		// System.out.println(activeType);
		operator(node);
		return true;
	}
	
	@Override
	public void endVisit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
		if(pool.size()>=2){
			pool.pop();
			activeType=pool.get(pool.size()-1);
		}else{
			pool.clear();
			}
		
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		this.activeMethodName = node.getName().toString();
		this.activeMethodParameters = pathUtil.getParameter(node);
		this.activeMethodPath=activeMethodName+activeMethodParameters;
		methodpool.push(activeMethodPath);
		// System.out.println(activeMethod);
		operator(node);
		return true;
	}
   

	@Override
	public void endVisit(MethodDeclaration node) {
		//说明出现了匿名内部类
		if(methodpool.size()>=2){
			methodpool.pop();
			activeMethodPath=methodpool.get(methodpool.size()-1);
		}else{
		methodpool.clear();
		this.activeMethodPath=null;
		}
		//this.activeMethod=null;
		super.endVisit(node);
	}
//判断匿名内部类
	public boolean visit(ClassInstanceCreation node) {
		if(node.getAnonymousClassDeclaration()!=null){
			if (counter.containsKey(activeType)) {
				int oldValue = counter.get(activeType);
				int newValue=oldValue+1;
				counter.put(activeType, newValue);
				activeType=activeType.split("\\$")[0]+"$"+newValue;
				this.activeMethodPath=null;
			} else {
				counter.put(activeType, 1);
				activeType=activeType.split("\\$")[0]+"$"+1;
				this.activeMethodPath=null;
			}
			
		}
		operator(node);
		return super.visit(node);
	}
//主要是解决匿名内部类遍历结束，还原最外层类状态
	@Override
	public void endVisit(ClassInstanceCreation node) {
		super.endVisit(node);
		if(activeType.contains("$")){
			String	temp[]=activeType.split("\\$");
			activeType=temp[0];
		}
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

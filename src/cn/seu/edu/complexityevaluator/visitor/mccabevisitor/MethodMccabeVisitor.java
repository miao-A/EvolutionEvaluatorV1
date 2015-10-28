/* 作者 何磊
 * 时间 2015/05/20
 * 说明 主要是计算方法层次的圈复杂度  
 *    目前检测的圈复杂度的操作符为 if while switch case for catch throw return 三目运算符  || &&
 * 版本 3.0
 *     2.0  修复内部类的扫描数据问题
 *     3.0  修改activeMethod=activeMethodName+activeMethodParameters；修复方法栈问题
 * */
package cn.seu.edu.complexityevaluator.visitor.mccabevisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.seu.edu.complexityevaluator.util.MethodPathUtil;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;

public class MethodMccabeVisitor extends AbstractVisitor {

	private Map<String, Integer> methodMccabeInfo = new HashMap<String, Integer>();
    
	public Map<String, Integer> getMethodMccabeInfo() {
		return methodMccabeInfo;
	}

	public void setMethodMccabeInfo(Map<String, Integer> methodMccabeInfo) {
		this.methodMccabeInfo = methodMccabeInfo;
	}
	//方法路径处理类
	private MethodPathUtil pathUtil=new MethodPathUtil();
	//匿名内部类计数器
	private HashMap<String, Integer> counter = new HashMap<String, Integer>();
	//解决匿名内部类转换问题
    private Stack<String> pool=new Stack<String>();
  //同样是解决匿名内部类的方法跳出到外部方法的问题
    private Stack<String> methodpool=new Stack<String>();
	private String pacakgeFullyQuallifedName;
	private String activeType;
	private String activeMethod;
	private String activeMethodName;
	private String activeMethodParameters;
	// 存贮mccabe相关操作符，以便后面数据验证
//	private Bag<String> mccabeOperators = new HashBag<String>();
	private Map<String,List<String>> mccabeOperators = new HashMap<String,List<String>>();//改变存储结构
	public Map<String, List<String>> getMccabeOperators() {
		return mccabeOperators;
	}

	private List<String> list=new ArrayList<String>();//用来存贮条件选择操作符
	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}

	private void operator(ASTNode node, Object name) {
		addOperator(typeName(node) + "\t" + name.toString());
	}

	private void addOperator(String id) {

		// 添加方法的扫描结果
		/*
		 * 逻辑：先判断这个方法路径是不是已经在MethodMccabeInfo里面。如果有了先拿到原来的复杂度数据，进行加1更新；如果路径不存在，
		 * 那么进行初始化存贮，因为每个方法的初始
		 * 复杂度是1，这个时候在检测到的话，那么存贮的值应该是2了；不管怎么样，检测到的操作符都会存到operator表中，以便后面的数据核对
		 * 问题：这样圈复杂为1的方法就没有统计了，应该在方法扫描的时候就进行初始化
		 */
		if (activeType != null && activeMethod != null) {
			String methodpath = pacakgeFullyQuallifedName + "|" + activeType
					+ "|" + activeMethod;
			//更新数值
			if (methodMccabeInfo.containsKey(methodpath)) {
				// 拿到当前方法的圈复杂度
				int mccabeNum = methodMccabeInfo.get(methodpath);
				int updateNum = mccabeNum + 1;
				methodMccabeInfo.put(methodpath, updateNum);
			}
			//保存对应的条件选择操作符
			if(mccabeOperators.containsKey(methodpath)){
				List<String> tempList=mccabeOperators.get(methodpath);
				tempList.add(id);
				mccabeOperators.put(methodpath, tempList);
			}else{
				List<String> tempList=new ArrayList<String>();
				tempList.add(id);
				mccabeOperators.put(methodpath, tempList);
			}
		}
	}

	private String typeName(ASTNode node) {

		return node.getClass().toString()
				.replace("class org.eclipse.jdt.core.dom.", "");
	}

	@Override
	public void endVisit(CompilationUnit node) {
		super.endVisit(node);
	}

	// 开始扫描
	@Override
	public boolean visit(PackageDeclaration node) {
		this.pacakgeFullyQuallifedName = node.getName()
				.getFullyQualifiedName();
		// System.out.println(pacakgeFullyQuallifedName);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		this.activeType = node.getName().toString();
		pool.push(activeType);
		// System.out.println(activeType);
		return true;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		super.endVisit(node);
		if(pool.size()>=2){
			pool.pop();
			this.activeType=pool.get(pool.size()-1);
		}else{
			pool.clear();
			}
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		this.activeMethodName = node.getName().toString();
		this.activeMethodParameters =pathUtil.getParameter(node);
		this.activeMethod=activeMethodName+activeMethodParameters;
		//初始化数据
		if (activeType != null && activeMethod != null) {
			String allmethodpath = pacakgeFullyQuallifedName + "|" + activeType
					+ "|" + activeMethod;
			methodMccabeInfo.put(allmethodpath, 1);
		}
		methodpool.push(activeMethod);
		// System.out.println(activeMethod);
		return true;
	}
	@Override
	public void endVisit(MethodDeclaration node) {
		//说明出现了匿名内部类
		if(methodpool.size()>=2){
			methodpool.pop();
			activeMethod=methodpool.get(methodpool.size()-1);
		}else{
		methodpool.clear();
		this.activeMethod=null;
		}
		//this.activeMethod=null;
		super.endVisit(node);
	}
	public boolean visit(ClassInstanceCreation node) {
		if(node.getAnonymousClassDeclaration()!=null){
			if (counter.containsKey(activeType)) {
				int oldValue = counter.get(activeType);
				int newValue=oldValue+1;
				counter.put(activeType, newValue);
				activeType=activeType.split("\\$")[0]+"$"+newValue;
				this.activeMethod=null;
			} else {
				counter.put(activeType, 1);
				activeType=activeType.split("\\$")[0]+"$"+1;
				this.activeMethod=null;
			}
			
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(ClassInstanceCreation node) {
		super.endVisit(node);
		if(activeType.contains("$")){
			String	temp[]=activeType.split("\\$");
			activeType=temp[0];
		}
	}

	// 获取方法的相关数据
	@Override
	public boolean visit(InfixExpression node) {
		if (node.getOperator() == InfixExpression.Operator.CONDITIONAL_AND
				|| node.getOperator() == InfixExpression.Operator.CONDITIONAL_OR) {
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

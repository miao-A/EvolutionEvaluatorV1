﻿/* 作者 何磊
 * 时间 2015/05/20
 * 说明 主要是计算方法层次的圈复杂度  
 *    目前检测的圈复杂度的操作符为 if while switch case for catch throw return 三目运算符  || &&
 * 版本 2.0
 * 1.0存在问题：类层级的初始化的cc值不应该都是1，而是方法的个数，下个版本要修复;同时类级别的圈复杂度的计算应该是类中所有方法的圈复杂度值和。
 * 2.0已经修复上述问题
 * */
package cn.seu.edu.complexityevaluator.visitor.mccabevisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;

public class ClassMccabeVisitor extends AbstractVisitor {

	private Map<String, Integer> classMccabeInfo = new HashMap<String, Integer>();

	public Map<String, Integer> getClassMccabeInfo() {
		return classMccabeInfo;
	}

	public void setClassMccabeInfo(Map<String, Integer> classMccabeInfo) {
		this.classMccabeInfo = classMccabeInfo;
	}
    private Stack<SimpleName> pool=new Stack<SimpleName>();
	private String pacakgeFullyQuallifedName;
	private SimpleName activeType;
	// 存贮mccabe相关操作符，以便后面数据验证
	private Bag<String> mccabeOperators = new HashBag<String>();

	private void operator(ASTNode node) {
		addOperator(typeName(node));
	}

	private void operator(ASTNode node, Object name) {
		addOperator(typeName(node) + "\t" + name.toString());
	}
	private void addOperator(String id) {
		
		// 添加方法的扫描结果
		/*逻辑：先判断这个方法路径是不是已经在MethodMccabeInfo里面。如果有了先拿到原来的复杂度数据，进行加1更新；如果路径不存在，那么进行初始化存贮，这里初始化是在TypeDeclartion里面。因为每个方法的初始
		复杂度是1，每个类的圈复杂度是每个方法圈复杂之和，所以这里的类的圈复杂度初始化是0，而不是1；不管怎么样，检测到的操作符都会存到operator表中，以便后面的数据核对*/
		if (activeType != null) {
			String classpath = pacakgeFullyQuallifedName + ":" + activeType;
			if (classMccabeInfo.containsKey(classpath)) {
				// 拿到当前方法的圈复杂度
				int mccabeNum = classMccabeInfo.get(classpath);
				int updateNum=mccabeNum+1;
				classMccabeInfo.put(classpath, updateNum);
			}
			mccabeOperators.add(classpath+"\t"+id);
		}
	}

	private String typeName(ASTNode node) {

		return node.getClass().toString()
				.replace("class org.eclipse.jdt.core.dom.", "");
	}

	//开始扫描
	@Override
	public boolean visit(PackageDeclaration node) {
		this.pacakgeFullyQuallifedName = node.getName()
				.getFullyQualifiedName();
		// System.out.println(pacakgeFullyQuallifedName);
		return true;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		this.activeType = node.getName();
		pool.push(activeType);
		if (activeType != null) {
			String classpath = pacakgeFullyQuallifedName + ":" + activeType;
		    classMccabeInfo.put(classpath, 0);
		}
		// System.out.println(activeType);
		return true;
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		super.endVisit(node);
		if(pool.size()>=2){
			pool.pop();
			this.activeType=pool.get(pool.size()-1);
		}
		pool.clear();
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		// System.out.println(activeMethod);
		//没扫描到一个方法进行加1，这是因为，按照WMC的计算方法来说其实每个方法的初始化圈复杂度是1，但是在这里面进行初始化的时候无法对类中的方法个数不好进行确定，所以
		//在这边处理的时候进行了，碰到方法的申明就进行+1操作来处理
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


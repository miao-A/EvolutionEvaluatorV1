﻿/* 作者 何磊
 * 日期 2015、03、31
 * 描述  中间调用 并存贮数据 后期简化
 * 版本 2.0  修复了初始化问题
 * */
package cn.seu.edu.complexityevaluator.creatast;

import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import cn.seu.edu.complexityevaluator.dao.SaveClassHstd;
import cn.seu.edu.complexityevaluator.dao.SaveClassMcc;
import cn.seu.edu.complexityevaluator.dao.SaveMethodHstd;
import cn.seu.edu.complexityevaluator.dao.SaveMethodMcc;
import cn.seu.edu.complexityevaluator.mainui.CalculateMetric;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.HalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MccabeVisitor;

public class Requestor extends FileASTRequestor {
	private AbstractVisitor abstractVisitor=new AbstractVisitor();
	private String[] visitors = { "classMccabeVisitor", "classHalsteadVisitor","halsteadVisitor","mccabeVisitor","methodMccabeVisitor","methodHalsteadVisitor"};
	 //这两个静态变量用来存贮所有文件的操作符符合操作数
	public static int allMccabeNum=0;
	public static Bag<String> allOperators = new HashBag<String>();
	public static Bag<String> allOperands = new HashBag<String>();
	private SaveMethodHstd saveMethodHstd=new SaveMethodHstd();
	private SaveMethodMcc saveMethodMcc=new SaveMethodMcc();
	private SaveClassMcc saveClassMcc=new SaveClassMcc();
	private SaveClassHstd saveClassHstd=new SaveClassHstd();
	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit ast) {
		String projName=CalculateMetric.getParser().getProjName();
		String projVersion=CalculateMetric.getParser().getProjVersion();
		for (int i = 0; i < visitors.length; i++) {
			AbstractVisitor	visitor = abstractVisitor.getVisitor(visitors[i]);
			ast.accept(visitor);
			//收集系统集数据
			if (visitor instanceof HalsteadVisitor){
				HalsteadVisitor halsteadVisitor = (HalsteadVisitor) visitor;
				Bag<String> operators = halsteadVisitor.getOperators();
				Bag<String> operands = halsteadVisitor.getOperands();
				allOperators.addAll(operators);
				allOperands.addAll(operands);
			}else if(visitor instanceof MccabeVisitor){
				MccabeVisitor mccabeVisitor = (MccabeVisitor) visitor;
				int mccabeNum=mccabeVisitor.getMccabeNum();
				allMccabeNum=allMccabeNum+mccabeNum;
			}
			//存储方法层和类层数据
			saveMethodMcc.saveMethodMcc(visitor, sourceFilePath, projName, projVersion);
			saveMethodHstd.saveStatistics(visitor,sourceFilePath,projName,projVersion);
			saveClassMcc.saveStatistics(visitor,sourceFilePath,projName,projVersion);
			saveClassHstd.saveStatistics(visitor,sourceFilePath,projName,projVersion);
		}
	}
}
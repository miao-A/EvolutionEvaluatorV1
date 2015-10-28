/* 作者 何磊
 * 时间 2015-3-31
 * 描述 Visitor的抽象类，相当于控制器
 * 版本 1.0
 * */

package cn.seu.edu.complexityevaluator.visitor;
import org.eclipse.jdt.core.dom.ASTVisitor;

import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.ClassHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.HalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.MethodHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.ClassMccabeVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MccabeVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MethodMccabeVisitor;

public  class AbstractVisitor extends ASTVisitor
{	
	public  AbstractVisitor getVisitor(String visitorName)
	{
		visitorName = visitorName.toLowerCase();
		//全局扫描
		if (visitorName.equals("halsteadvisitor"))
		{
		
			return new HalsteadVisitor();
		}
		if (visitorName.equals("mccabevisitor"))
		{
			return new MccabeVisitor();
		}
		//方法层扫描
		if (visitorName.equals("methodhalsteadvisitor"))
		{
			return new MethodHalsteadVisitor();
		}
		if (visitorName.equals("methodmccabevisitor"))
		{
			return new MethodMccabeVisitor();
		}
		//类层面扫描
		if (visitorName.equals("classhalsteadvisitor"))
		{
			return new ClassHalsteadVisitor();
		}
		if (visitorName.equals("classmccabevisitor"))
		{
			return new ClassMccabeVisitor();
		}

		throw new RuntimeException("No visitor named " + visitorName);
	}
	
}

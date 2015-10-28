/*
 * 日期 2015-8-12
 * 描述 用来对ast中分析得到的方法名的形式进行变更，只显示参数类型，没有参数名.
 * 特别注意：有一种特殊的情况，就是数组的【】，可以写在前面，也可以写在后面，要进行一个维度的判断，再做处理
 * 
 * */
package cn.seu.edu.complexityevaluator.util;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class MethodPathUtil {
	public String getParameter(final MethodDeclaration methodDec) {
		StringBuffer parameter = new StringBuffer();
		parameter.append("(");
		List<SingleVariableDeclaration> paraList = methodDec.parameters();
		for (SingleVariableDeclaration par : paraList) {
			Type type = par.getType();
			String str = type.toString();
			int i = par.getExtraDimensions();
			for (int j = 0; j < i; j++){
				str = str + "[]";
			}
			parameter.append(str + ",");

		}
		if (paraList.size() > 0) {
			parameter.deleteCharAt(parameter.length() - 1);
		}
		parameter.append(")");
		return parameter.toString();
	}
}

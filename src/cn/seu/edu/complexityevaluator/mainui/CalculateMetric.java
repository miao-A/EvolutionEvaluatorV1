/*
 * 版本 2.0
 * 日期  2015-8-1
 * 描述 对程序进行解析的入口
 * 
 * */
package cn.seu.edu.complexityevaluator.mainui;
import org.apache.commons.collections15.bag.HashBag;

import cn.seu.edu.complexityevaluator.creatast.ProjectParser;
import cn.seu.edu.complexityevaluator.creatast.Requestor;
public class CalculateMetric {  
	
	private static ProjectParser parser;
	public static ProjectParser getParser() {
		return parser;
	}
	public static void setParser(ProjectParser parser) {
		CalculateMetric.parser = parser;
	}
	public void caculateMetric(String projectPath,String projName,String projVersion){
		String libPath = "C:/lib2013";
		parser = new ProjectParser(libPath, projectPath,projName,projVersion);
		parser.parser();
		//重置数据
		Requestor.allOperands=new HashBag<String>();
		Requestor.allOperators=new HashBag<String>();
		Requestor.allMccabeNum=0;
   }
}
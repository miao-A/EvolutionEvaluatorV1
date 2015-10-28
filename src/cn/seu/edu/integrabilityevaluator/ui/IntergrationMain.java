package cn.seu.edu.integrabilityevaluator.ui;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;

import cn.seu.edu.integrabilityevaluator.dbconnect.DBConnector;
import cn.seu.edu.integrabilityevaluator.dbconnect.ExtensibilityConnector;
import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectInfoConnector;
import cn.seu.edu.integrabilityevaluator.model.JarClassModel;
import cn.seu.edu.integrabilityevaluator.model.JarMethodModel;
import cn.seu.edu.integrabilityevaluator.parser.AnalysisJarFile;
import cn.seu.edu.integrabilityevaluator.parser.Compatibility;
import cn.seu.edu.integrabilityevaluator.parser.OuterCompatibility;
import cn.seu.edu.integrabilityevaluator.parser.ProjectParser;
import cn.seu.edu.integrabilityevaluator.parser.ReadFile;


//测试使用
public class IntergrationMain {
		
	public static void main(String args[]) {
			
		
		String projectPath = "D:\\ProjectOfHW\\junit\\junit3.6";
		String projectName = "junit";
		String version = "3.6";
				
		ProjectParser projectParser = new ProjectParser(projectPath, projectName, version);
		projectParser.parser();

		projectParser.runSubstitutabilityOfClassDector();
		/*String projectPath = "D:\\ProjectOfHW\\jEditor\\jEditor0.2";
		String pathOfOne = "D:\\ProjectOfHW\\jEditor\\jEditor0.2\\src\\org\\jeditor\\app";*/
//		String pathOfTwo = "D:\\ProjectOfHW\\jEditor\\jEditor0.2\\src\\org\\jeditor\\diff";
		
/*		ProjectParser projectParser = new ProjectParser(projectPath,projectName,version);
		projectParser.parser();
		projectParser.runOuterCompatibilityDectector();*/
		/*OuterCompatibility outerCompatibility = new OuterCompatibility(projectPath,version);
		if (outerCompatibility.jdkCompatibility("1.4")) {			
			System.out.println("兼容");
		}else{
			for (String string : outerCompatibility.getuncompatibilityfileList()) {
				System.out.println(string);
			}
			System.out.println("不兼容");
		}
		*/
    
    	
    	/*List<JarClassModel> list = null;
    	try {

			list = AnalysisJarFile.getJarMethod("D:\\test\\jfreechart-1.0.19.jar","D:\\test\\TestJar");
			//list = AnalysisJarFile.getJarMethod("D:\\eclipse\\dropins\\lib\\mysql-connector-java-5.1.35-bin.jar");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
    	
    	/*for (JarClassModel jarClassModel : list) {
    		System.out.println("----------------------------------------------");
			System.out.println("classname:"+jarClassModel.getClassName());
			for (JarMethodModel jarMethodModel : jarClassModel.getmethod()) {
				System.out.println("methodname:"+jarMethodModel.getMethodName());
			}
			System.out.println("----------------------------------------------");
		}*/
    	
    	System.out.println("END");
    	
    }  
}
/* 
 * 日期 2015-3-31
 * 描述 解析文件路径
 * */
package cn.seu.edu.complexityevaluator.util;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class FilePathUtil {
	private static void readFiles(File file, String suffix,ArrayList<String> fileList) {
		if (file != null) {
			if (file.isDirectory()) {
				File f[] = file.listFiles();
				if (f != null) {
					for (int i = 0; i < f.length; i++) {
						readFiles(f[i], suffix, fileList);
					}
				}
			} else if(file.getName().endsWith(suffix)){
				fileList.add(file.toString());
			}
		}
	}
	
	public static String[] getAllFiles(String projectPath, String suffix) {
		ArrayList<String> fileList = new ArrayList<String>();                                  
		File file = new File(projectPath);
		if (file.isDirectory() == true) {
			FilePathUtil.readFiles(file, suffix, fileList);
		}
		return fileList.toArray(new String[0]);
	}
}

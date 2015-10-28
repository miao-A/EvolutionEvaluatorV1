/* 作者 何磊
 * 日期 2013-3-31
 * 描述 对文件夹中的每个文件进行语法树生成
 * 版本 2.0
 * 修改说明：这个版本将系统级别的数据存贮放到了这里。
 * 存在问题 文件夹路径不存在 没有提示错误。后期修改
 * */

package cn.seu.edu.complexityevaluator.creatast;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import cn.seu.edu.complexityevaluator.dao.SaveSysHstd;
import cn.seu.edu.complexityevaluator.dao.SaveSysMcc;
import cn.seu.edu.complexityevaluator.util.FilePathUtil;


public class ProjectParser {
	private  String pathOfProject;
	private  String pathOfLib;
	private  String projName;
	public String getProjName() {
		return projName;
	}


	public void setProjName(String projName) {
		this.projName = projName;
	}


	public String getProjVersion() {
		return projVersion;
	}


	public void setProjVersion(String projVersion) {
		this.projVersion = projVersion;
	}


	private  String projVersion;
	private  SaveSysHstd saveSysHstd=new SaveSysHstd();
	private  SaveSysMcc saveSysMcc=new SaveSysMcc();
	public ProjectParser(String pathOfLib, String pathOfProject,String projName,String projVersion){	
		this.pathOfLib = pathOfLib;
		this.pathOfProject = pathOfProject;
		this.projName=projName;
		this.projVersion=projVersion;
	}

		
	public void parser()  {
		// create a AST parser
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		// obtain classpathEntries
		String libPath = pathOfLib;
		String[] classpathEntries = FilePathUtil.getAllFiles(libPath, ".jar");
		// obtain sourcepathEntries
		String[] sourcepathEntries = {pathOfProject};	
		// set the environment for the AST parsers
		parser.setEnvironment(classpathEntries, sourcepathEntries, null, true);
		// enable binding
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		// set the compiler option
		Map complierOptions= JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, complierOptions);
		parser.setCompilerOptions(complierOptions);
		// obtain sourceFilePaths
	    Requestor requestor=new Requestor();
		String[] sourceFilePaths = FilePathUtil.getAllFiles(pathOfProject, ".java");
		parser.createASTs(sourceFilePaths, null, new String[0], requestor, null);
		saveSysHstd.saveSystemHalsteadInfo(projName,projVersion,pathOfProject);
		saveSysMcc.saveMccabeInfo(projName,projVersion,pathOfProject);
	}
}

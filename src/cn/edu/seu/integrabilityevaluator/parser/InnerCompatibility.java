package cn.edu.seu.integrabilityevaluator.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import cn.edu.seu.integrabilityevaluator.astvisitor.InnerCompatibilityRequestor;
import cn.edu.seu.integrabilityevaluator.astvisitor.VersionCompatibilityRequestor;
import cn.edu.seu.integrabilityevaluator.model.AbstractClassModel;
import cn.edu.seu.integrabilityevaluator.model.UnCompatibilityMIModel;
import cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder.ClassCompatibilityRecoder;

public class InnerCompatibility {
	
	private String pathOfComponetFirst;
	private String pathOfComponetSecond;
/*	private List<AbstractClassModel> changeRecoder;
	
	private List<AbstractClassModel> removedType = new LinkedList<>();
	private List<AbstractClassModel> newType = new LinkedList<>();*/
/*	private List<ClassCompatibilityRecoder> compatibilityRecoders = new LinkedList<>();
	private List<ClassCompatibilityRecoder> unCompatibilityRecoders = new LinkedList<>();*/
	private List<UnCompatibilityMIModel> unCompatibilityMIModels = new LinkedList<>();
//	private List<AbstractClassModel> unchangedClassModels = new LinkedList<>();
	
	
	private List<ClassCompatibilityRecoder>  typeRecoders = new LinkedList<>();
	
	public InnerCompatibility(String pathOfProject,String pathOfComponet) {
		// TODO Auto-generated constructor stub
		this.pathOfComponetFirst = pathOfProject;
		this.pathOfComponetSecond = pathOfComponet;
		
		unCompatibilityMIModels = innerCompatibilityParser(pathOfProject,pathOfComponet);
		for (UnCompatibilityMIModel unCompatibilityMIModel : unCompatibilityMIModels) {
			unCompatibilityMIModel.getMessage();
		}		
	}
	
	
	public List<UnCompatibilityMIModel> innerCompatibilityParser(String ProjectOfPath, String pathOfTestComponet){
		
		List<AbstractClassModel> models = parserComponet(pathOfTestComponet);
		List<String> packageList = new ArrayList<>();
		for (AbstractClassModel abstractClassModel : models) {
			if (!packageList.contains(abstractClassModel.getPackage())) {
				packageList.add(abstractClassModel.getPackage());
			}			
		}
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);
			
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
			
				
		InnerCompatibilityRequestor componentRequertor = new InnerCompatibilityRequestor(packageList);
		ReadFile readFile = new ReadFile(ProjectOfPath);
		List<String> filelist = readFile.readJavaFiles();
		
/*		ReadFile readTestedFile = new ReadFile(pathOfTestComponet);
		filelist.addAll(readTestedFile.readJavaFiles());*/
				
		//////////////////////
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);

				/////////////////////
				
				
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
				
		//parser.setEnvironment(null, null, null, true);
		parser.setEnvironment(jarpathEntries, null, null, true);
				
		parser.createASTs(sourceFilePaths,  null, new String[0], componentRequertor, null);
		System.out.println("comparing... ...");
		//		return componentRequertor.getTypeModels();
		return componentRequertor.getuncompatibilityMIModels();
		
		
	}	
	
	public List<AbstractClassModel> parserComponet(String pathOfComponet)  {
		// create a AST parser
		ASTParser parser;
		parser = ASTParser.newParser(AST.JLS4);
	
		Map<String,String> complierOptions= JavaCore.getDefaultOptions();
		complierOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
		parser.setCompilerOptions(complierOptions);
		
		
		// enable binding	
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		
		
		VersionCompatibilityRequestor componentRequertor = new VersionCompatibilityRequestor();
		ReadFile readFile = new ReadFile(pathOfComponet);		
		List<String> filelist = readFile.readJavaFiles();
		
		//////////////////////
		List<String> jarfilelist = readFile.readJarFiles();		
		String[] jarpathEntries = jarfilelist.toArray(new String[jarfilelist.size()]);
//		String[] jarpathEntries = {pathOfProject};		
		List<String> javafilelist =  readFile.readJavaFiles();
		/////////////////////
		
		
		String[] sourceFilePaths = filelist.toArray(new String[filelist.size()]);
		System.out.println("fileread over!");
		
		//parser.setEnvironment(null, null, null, true);
		parser.setEnvironment(jarpathEntries, null, null, true);
		
		parser.createASTs(sourceFilePaths,  null, new String[0], componentRequertor, null);
		//System.out.println("comparing... ...");
		return componentRequertor.getTypeModels();
	}		
	
	public List<UnCompatibilityMIModel> getunCompatibilityMIModels(){
		return unCompatibilityMIModels;
	}
	
	/*public List<ClassCompatibilityRecoder> getTypeChangeRecoders(){
		return unCompatibilityRecoders;
	}
	
	
	public List<AbstractClassModel> getNewTypeModels(){
		return newType;
	}
	
	public List<AbstractClassModel> getRemovedTypeModels(){
		return removedType;
	}
	
	public List<ClassCompatibilityRecoder> getCompatibilityRecoders(){
		return compatibilityRecoders;
	}
	
	public List<ClassCompatibilityRecoder> getUncompatibilityRecoders(){
		return unCompatibilityRecoders;
	}
	
	public void getinfo(){
		
		for(AbstractClassModel atm : newType){
			System.out.println("newType:"+ atm.getPackage()+" " +atm.getClassName());
			
		}
		
		for(AbstractClassModel atm : removedType){
			System.out.println("removedType:"+ atm.getPackage()+" " +atm.getClassName());
			List<MethodModel> list = atm.getMethodModels();
			int count = 0;
			for (MethodModel methodModel : list) {
				if (methodModel.getModifier().isPUBLIC()) {
					++count;
				}
			}
			System.out.println(count);
		}
		
		for(ClassCompatibilityRecoder atm : compatibilityRecoders){
			System.out.println("compatibilityType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
			
			ConstructorMethodRecoder cmr = atm.getConstructorMethodRecoder();
			//int count = 0;
			Map<ConstructorMethodModel, ConstructorMethodModel> cmap = cmr.getCompatibilityConstructorMethodMap();			
			//System.out.println(count);			
			for (ConstructorMethodModel methodModel : cmap.keySet()) {
				System.out.println("old:"+methodModel.getFullName());
				System.out.println("new:"+cmap.get(methodModel).getFullName());
				
			}
			
			
			MethodRecoder mr = atm.getMethodRecoder();
			int count = 0;
			List<MethodModel> list = mr.getUnchangedMethodModels();			
			System.out.println(count);
			Map<MethodModel, MethodModel> map = atm.getMethodRecoder().getCompatibilityMethodMap();
			for (MethodModel methodModel : map.keySet()) {
				System.out.println("old:"+methodModel.getFullName());
				System.out.println("new:"+map.get(methodModel).getFullName());				
			}
		}
		
		for(ClassCompatibilityRecoder atm : unCompatibilityRecoders){
			System.out.println("unCompatibilityRecoders:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName());
			MethodRecoder mr = atm.getMethodRecoder();
			
			if (mr.getNewAddMethodModels().size()!=0) {
				System.out.println("NewAddMethod:");
			}
			

			for (MethodModel methodModel : mr.getNewAddMethodModels()) {
				System.out.println(methodModel.getFullName());
										
			}
	
			if (mr.getRemovedMethodModels().size()!=0) {
				System.out.println("RemovedMethod:");
			}
			
			for (MethodModel methodModel : mr.getRemovedMethodModels()) {
				System.out.println(methodModel.getFullName());						
			}
			
			ConstructorMethodRecoder cmr = atm.getConstructorMethodRecoder();
			if (cmr.getCompatibilityConstructorMethodMap().size()!=0) {
				System.out.println("Compatibility Method:");
				Map<ConstructorMethodModel, ConstructorMethodModel> map = cmr.getCompatibilityConstructorMethodMap();
				for (ConstructorMethodModel methodModel : map.keySet()) {
					System.out.println("old:"+methodModel.getFullName());
					System.out.println("new:"+map.get(methodModel).getFullName());
				}			
			}
			
			if (cmr.getRemovedMethodModels().size()!=0) {
				System.out.println("RemovedConstructorMethod:");
			}
			
			for (ConstructorMethodModel methodModel : cmr.getRemovedMethodModels()) {
				System.out.println(methodModel.getFullName());						
			}
			
			if (cmr.getNewAddMethodModels().size()!=0) {
				System.out.println("newConstructorMethod:");
			}
			
			for (ConstructorMethodModel methodModel : cmr.getNewAddMethodModels()) {
				System.out.println(methodModel.getFullName());						
			}
			
			
			
			if (mr.getCompatibilityMethodMap().size()!=0) {
				System.out.println("Compatibility Method:");
				Map<MethodModel, MethodModel> map = mr.getCompatibilityMethodMap();
				for (MethodModel methodModel : map.keySet()) {
					System.out.println("old:"+methodModel.getFullName());
					System.out.println("new:"+map.get(methodModel).getFullName());
				}			
			}		
						
		}
	}*/
}

/*package cn.seu.edu.integrabilityevaluator.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import cn.seu.edu.integrabilityevaluator.astvisitor.VersionCompatibilityRequestor;
import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.ClassModel;
import cn.seu.edu.integrabilityevaluator.model.ConstructorMethodModel;
import cn.seu.edu.integrabilityevaluator.model.EnumModel;
import cn.seu.edu.integrabilityevaluator.model.MethodModel;
import cn.seu.edu.integrabilityevaluator.changecomparator.ChangeStatus;
import cn.seu.edu.integrabilityevaluator.changecomparator.ClassChangeRecoder;
import cn.seu.edu.integrabilityevaluator.changecomparator.ConstructorMethodRecoder;
import cn.seu.edu.integrabilityevaluator.changecomparator.MethodRecoder;


public class ChangeComparator {
	private String oldPathOfProject;
	private String newPathOfProject;
	private List<AbstractClassModel> changeRecoder;
	
	private List<AbstractClassModel> removedType = new LinkedList<>();
	private List<AbstractClassModel> newType = new LinkedList<>();
	private List<ClassChangeRecoder> unchangeRecoders = new LinkedList<>();
	private List<ClassChangeRecoder> modifiedRecoders = new LinkedList<>();
	
	public ChangeComparator(String oldPathOfProject,String newPathOfProject) {
		// TODO Auto-generated constructor stub
		this.oldPathOfProject = oldPathOfProject;
		this.newPathOfProject = newPathOfProject;
		changeRecoder = new LinkedList<>();		
		changeParser(this.parserComponet(oldPathOfProject),this.parserComponet(newPathOfProject));		
	}	
	
	
	public void changeParser(List<AbstractClassModel> oldModels,List<AbstractClassModel> newModels){

		for (AbstractClassModel oldTypeModel : oldModels) {
			if (!newModels.contains(oldTypeModel)) {
				removedType.add(oldTypeModel);
			}			
		}
		
		for (AbstractClassModel newTypeModel : newModels) {
			if (!oldModels.contains(newTypeModel)) {
				newType.add(newTypeModel);
			}
		}
		
		for(AbstractClassModel newTypeModel : newModels) {
			if(oldModels.contains(newTypeModel)){
				int index = oldModels.indexOf(newTypeModel);
				
				ClassChangeRecoder changeRecoder = new ClassChangeRecoder(oldModels.get(index),newTypeModel); 
				if (changeRecoder.getChangeStatus().equals(ChangeStatus.UNCHANGED)) {
					unchangeRecoders.add(changeRecoder);
				}else if (changeRecoder.getChangeStatus().equals(ChangeStatus.MODIFIED)) {
					modifiedRecoders.add(changeRecoder);
				}
			}
		}		
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
		System.out.println("comparing... ...");
		return componentRequertor.getTypeModels();
	}		
	
	public List<ClassChangeRecoder> getTypeChangeRecoders(){
		return modifiedRecoders;
	}
	
	
	public List<AbstractClassModel> getNewTypeModels(){
		return newType;
	}
	
	public List<AbstractClassModel> getRemovedTypeModels(){
		return removedType;
	}
	
	public List<ClassChangeRecoder> getUnchangeRecoders(){
		return unchangeRecoders;
	}		
	
	public void getinfo(){

		for(AbstractClassModel atm : newType){
			
			System.out.print("newType:"+ atm.getPackage()+" " +atm.getClassName()+" ");
			if (atm instanceof EnumModel) {
				System.out.println("Enum");
			}else if (atm instanceof ClassModel) {
				if (((ClassModel) atm).isINTERFACE()) {
					System.out.println("Interface");
				}else {
					System.out.println("Class");
				}
			}
			System.out.println("ImportPackages num:"+atm.getImportPackages().size());			
		}
		
		
		for(AbstractClassModel atm : removedType){
			System.out.print("removedType:"+ atm.getPackage()+" " +atm.getClassName()+" ");
			if (atm instanceof EnumModel) {
				System.out.println("Enum");
			}else if (atm instanceof ClassModel) {
				if (((ClassModel) atm).isINTERFACE()) {
					System.out.println("Interface");
				}else {
					System.out.println("Class");
				}
			}
			System.out.println("ImportPackages num:"+atm.getImportPackages().size());
			
			List<MethodModel> list = atm.getMethodModels();
			int count = 0;
			for (MethodModel methodModel : list) {
				if (methodModel.getModifier().isPUBLIC()) {
					++count;
				}
			}
//			System.out.println(count);
		}
		
		
		for(ClassChangeRecoder atm : unchangeRecoders){
			System.out.print("unchangeType:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName()
					+" ");
			if (atm.getNewTypeModel() instanceof EnumModel) {
				System.out.println("Enum");
			}else if (atm.getNewTypeModel() instanceof ClassModel) {
				if (((ClassModel) atm.getNewTypeModel()).isINTERFACE()) {
					System.out.println("Interface");
				}else {
					System.out.println("Class");
				}
			}
			
			System.out.println("ImportPackages num:"+atm.getOldTypeModel().getImportPackages().size());
			System.out.println("ImportPackages num:"+atm.getNewTypeModel().getImportPackages().size());
			
			ConstructorMethodRecoder cmr = atm.getConstructorMethodRecoder();
			//int count = 0;
			Map<ConstructorMethodModel, ConstructorMethodModel> cmap = cmr.getModifiedMethodMap();			
			//System.out.println(count);			
			for (ConstructorMethodModel methodModel : cmap.keySet()) {
				System.out.println("old:"+methodModel.getFullName());
				System.out.println("new:"+cmap.get(methodModel).getFullName());				
			}
			
			
			MethodRecoder mr = atm.getMethodRecoder();
			int count = 0;
			List<MethodModel> list = mr.getUnchangedMethodModels();			
//			System.out.println(count);
			Map<MethodModel, MethodModel> map = atm.getMethodRecoder().getModifiedMethodMap();
			for (MethodModel methodModel : map.keySet()) {
				System.out.println("old:"+methodModel.getFullName());
				System.out.println("new:"+map.get(methodModel).getFullName());				
			}
		}
		System.out.println("begin");
		for(ClassChangeRecoder atm : modifiedRecoders){
			
			System.out.print("changeRecoders old:"+ atm.getOldTypeModel().getPackage()+" " +atm.getOldTypeModel().getClassName()+" ");
			if (atm.getOldTypeModel() instanceof EnumModel) {
				System.out.println("Enum");
			}else if (atm.getOldTypeModel() instanceof ClassModel) {
				if (((ClassModel) atm.getOldTypeModel()).isINTERFACE()) {
					System.out.println("Interface");
				}else {
					System.out.println("Class");
				}
			}
			
			System.out.println("ImportPackages num:"+atm.getOldTypeModel().getImportPackages().size());
			System.out.println("ImportPackages num:"+atm.getNewTypeModel().getImportPackages().size());
			
			System.out.print("changeRecoders new:"+ atm.getNewTypeModel().getPackage()+" " +atm.getNewTypeModel().getClassName()+" ");
			if (atm.getNewTypeModel() instanceof EnumModel) {
				System.out.println("Enum");
			}else if (atm.getNewTypeModel() instanceof ClassModel) {
				if (((ClassModel) atm.getNewTypeModel()).isINTERFACE()) {
					System.out.println("Interface");
				}else {
					System.out.println("Class");
				}
			}
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
			
			if (mr.getModifiedMethodMap().size()!=0) {
				System.out.println("modified Method:");
				Map<MethodModel, MethodModel> map = mr.getModifiedMethodMap();
				for (MethodModel methodModel : map.keySet()) {
					System.out.println("old:"+methodModel.getFullName());
					System.out.println("new:"+map.get(methodModel).getFullName());
				}			
			}
			
			ConstructorMethodRecoder cmr = atm.getConstructorMethodRecoder();
			if (cmr.getModifiedMethodMap().size()!=0) {
				System.out.println("modified Method:");
				Map<ConstructorMethodModel, ConstructorMethodModel> map = cmr.getModifiedMethodMap();
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
						
		}
	}
}
*/
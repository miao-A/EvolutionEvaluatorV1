package cn.edu.seu.integrabilityevaluator.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractClassModel {
	

	
	public abstract void setPackage(String name);
	
	public abstract String getPackage();
	
	public abstract void setClassName(String name);
	
	public abstract String getClassName();
	
	public abstract void addInnerClass(ClassModel typeModel);
		
	public abstract void setModifier(JModifier jModifier);
		
	public abstract JModifier getModifier();
	
	public abstract List<String> getSuperInterfaceTypes();
	
	public abstract void setSuperInterfaceType(String superInterfaceType);
	
	public abstract List<FieldModel> getFieldModels();
	
	public abstract void setFieldModels(List<FieldModel> fieldModels);
	
	public abstract List<MethodModel> getMethodModels();
	
	public abstract int getPublicMethodNum();

	public abstract void setMethodModels(List<MethodModel> methodModels);
	
	public abstract List<AbstractClassModel> getInnerClassModels();
	
	public abstract void setInnerClassModels(List<AbstractClassModel> innerClassModels);
	
	public abstract void addInnerClassModel(AbstractClassModel innerClassModel);	
	
	public abstract boolean equals(Object obj);
	
	public abstract List<ConstructorMethodModel> getConstructorModel();
	
	public abstract void setImportPackages(HashSet<String> importPackages);
	public abstract HashSet<String> getImportPackages();
	

	
}

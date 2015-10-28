package cn.seu.edu.integrabilityevaluator.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;


/*public class ClassModel{	
	private String packageName;//源文件中包名
	private String className;//源文件中类名	
	private JModifier modifier;//源文件中类修饰符	
	private List<ConstructorMethodModel> constructorMethodModels;//	源文件中默认构造方法
	private List<MethodModel> methodModels ;//源文件中方法
	//......
}
*/
public class ClassModel extends AbstractClassModel {
	
	private String packageName = "";
	private String className = "";	
	private boolean INTERFACE = false;
	
	private List<String> superInterfaceTypes = new LinkedList<>();
	private String superClass;
	private JModifier modifier = new JModifier();
	
	private List<FieldModel> fieldModels = new LinkedList<>();
	private List<ConstructorMethodModel> constructorMethodModels = new LinkedList<>();	
	private List<MethodModel> methodModels = new LinkedList<>();
	
/*	private List<EnumModel> enumClassModels = new LinkedList<>();
	private List<TypeModel> innerClassModels = new LinkedList<>();*/
	
	private List<AbstractClassModel> innerClassModels = new LinkedList<>();
	private List<String> typeParameters =  new LinkedList<>();
	private boolean empty = true;

	private HashSet<String> importPackageList = new HashSet();
	

	public void setPackage(String name){
		if (name == null) {
			packageName = "";
		}else {
			packageName = name;
		}
		
	}
	
	public String getPackage(){
		return packageName;
	}
	
	public void setClassName(String name){
		className = name;
	}
	
	public String getClassName(){
		return className;
	}
	
	public void addInnerClass(ClassModel typeModel) {
		innerClassModels.add(typeModel);		
	}
	
	public void setModifier(JModifier jModifier){
		modifier = jModifier;
	}
	
	public JModifier getModifier(){
		return modifier;
	}

	
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	
	public String getSuperClass() {
		return superClass;
	}

	public List<String> getSuperInterfaceTypes() {
		return superInterfaceTypes;
	}

	public void setSuperInterfaceType(String superInterfaceType) {
		this.superInterfaceTypes.add(superInterfaceType);
	}

	public boolean isINTERFACE() {
		return INTERFACE;
	}

	public void setINTERFACE(boolean iNTERFACE) {
		INTERFACE = iNTERFACE;
	}

	public List<FieldModel> getFieldModels() {
		return fieldModels;
	}

	public void setFieldModels(List<FieldModel> fieldModels) {
		for (FieldModel fieldModel : fieldModels) {
			this.fieldModels.add(fieldModel);
		}
	}	

	public List<String> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}	
	
	public void addTypeParameter(String typeParameter){
		this.typeParameters.add(typeParameter);
	}

	public List<MethodModel> getMethodModels() {
		return methodModels;
	}

	public void setMethodModels(List<MethodModel> methodModels) {
		for (MethodModel methodModel : methodModels) {
			this.methodModels.add(methodModel);
		}
	}

	public void addMethodModel(MethodModel methodModel){
		this.methodModels.add(methodModel);
	}


	public boolean isEmpty() {
		return empty;
	}


	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	@Override
	public List<AbstractClassModel> getInnerClassModels() {
		// TODO Auto-generated method stub
		return innerClassModels;
	}

	@Override
	public void setInnerClassModels(List<AbstractClassModel> innerClassModels) {
		// TODO Auto-generated method stub
		this.innerClassModels = innerClassModels;
	}

	@Override
	public void addInnerClassModel(AbstractClassModel innerClassModel) {
		// TODO Auto-generated method stub
		this.innerClassModels.add(innerClassModel);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) {
			return true;			
		}
		if (obj instanceof ClassModel) {
			ClassModel other = (ClassModel) obj;
			if (this.getPackage().equals(other.getPackage())&&this.getClassName().equals(other.getClassName())) {
				return true;
			}
		}	
		return false;
	}

	@Override
	public int getPublicMethodNum() {
		// TODO Auto-generated method stub
		int count = 0;
		for (MethodModel methodModel :  methodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}

	public List<ConstructorMethodModel> getConstructorMethodModels() {
		return constructorMethodModels;
	}

	public void setConstructorMethodModels(List<ConstructorMethodModel> constructorMethodModels) {
		this.constructorMethodModels = constructorMethodModels;
	}

	@Override
	public List<ConstructorMethodModel> getConstructorModel() {
		// TODO Auto-generated method stub
		return constructorMethodModels;
	}

	@Override
	public void setImportPackages(HashSet<String> importPackageStrings) {
		// TODO Auto-generated method stub
		importPackageList = importPackageStrings;
		
	}

	@Override
	public HashSet<String> getImportPackages() {
		// TODO Auto-generated method stub
		return importPackageList;
	}
	
	

}

package cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder;

import java.util.LinkedList;
import java.util.List;

import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.ClassModel;
import cn.seu.edu.integrabilityevaluator.model.EnumModel;

public class ClassCompatibilityRecoder {
	private CompatibilityStatus compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	private CompatibilityStatus superClassCompatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	private CompatibilityStatus superInterfaceCompatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	private CompatibilityStatus fieldCompatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	private CompatibilityStatus methodCompatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	
	private AbstractClassModel oldTypeModel = null;
	private AbstractClassModel newTypeModel = null;
	
	private ModifierRecoder modifierRecoder = new ModifierRecoder();
	private SuperClassRecoder superClassRecoder = new SuperClassRecoder();
	private TypeParameterRecoder typeParameterRecoder = new TypeParameterRecoder();
	private SuperInterfaceClassRecoder superInterfaceClassRecoder = new SuperInterfaceClassRecoder();
	private FieldRecoder fieldRecoder = new FieldRecoder();
	private MethodRecoder methodRecoder = new MethodRecoder();
	private ConstructorMethodRecoder constructorMethodRecoder = new ConstructorMethodRecoder();
	private EnumConstantRecoder enumConstantRecoder = new EnumConstantRecoder();
	
	private List<AbstractClassModel> newInnerTypeModels = new LinkedList<>();
	private List<AbstractClassModel> removedInnerTypeModels = new LinkedList<>();
	private List<AbstractClassModel> uncompatibilityInnerTypeModels = new LinkedList<>();
	private List<AbstractClassModel> compatibilityInnerTypeModels = new LinkedList<>();	
	
	public ClassCompatibilityRecoder(AbstractClassModel oldModel,AbstractClassModel newModel){
		this.oldTypeModel = oldModel;
		this.newTypeModel = newModel;
		compatibilityStatus = compareRun();
	}	
	
	public CompatibilityStatus compareRun(){
		if ((oldTypeModel instanceof ClassModel)&&(newTypeModel instanceof ClassModel)) {
			superClassRecoder =new SuperClassRecoder(((ClassModel)oldTypeModel).getSuperClass(),((ClassModel)newTypeModel).getSuperClass());
		}
		
		if ((oldTypeModel instanceof EnumModel)&&(newTypeModel instanceof EnumModel)) {
			enumConstantRecoder =new EnumConstantRecoder(((EnumModel)oldTypeModel).getEnumConstant(),((EnumModel)newTypeModel).getEnumConstant());
		}
		
		
		modifierRecoder = new ModifierRecoder(oldTypeModel.getModifier(),newTypeModel.getModifier());
		typeParameterRecoder = new TypeParameterRecoder(oldTypeModel.getSuperInterfaceTypes(), newTypeModel.getSuperInterfaceTypes());
		superInterfaceClassRecoder = new SuperInterfaceClassRecoder(oldTypeModel.getSuperInterfaceTypes(), newTypeModel.getSuperInterfaceTypes());
		fieldRecoder = new FieldRecoder(oldTypeModel.getFieldModels(), newTypeModel.getFieldModels());
		methodRecoder = new MethodRecoder(oldTypeModel.getMethodModels(), newTypeModel.getMethodModels());
		constructorMethodRecoder = new ConstructorMethodRecoder(oldTypeModel.getConstructorModel(),newTypeModel.getConstructorModel());
		
		if (modifierRecoder.isCompatibility()&& methodRecoder.isCompatibility()&&constructorMethodRecoder.isCompatibility()&&isUnchanged(superClassRecoder.getChangeStatus())&&isUnchanged(typeParameterRecoder.getChangeStatus())&&
				isUnchanged(superInterfaceClassRecoder.getChangeStatus())) {
			this.compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
		}else{
			this.compatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			if (superClassRecoder.getChangeStatus().equals(ChangeStatus.MODIFIED)) {
				superClassCompatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			}
			
			if (superInterfaceClassRecoder.getChangeStatus().equals(ChangeStatus.MODIFIED)) {
				superInterfaceCompatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			}
			
			if (!methodRecoder.isCompatibility()){
				methodCompatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			}			
		}
				
		List<AbstractClassModel> oldInners = oldTypeModel.getInnerClassModels();
		List<AbstractClassModel> newInners = newTypeModel.getInnerClassModels();
		
		for (AbstractClassModel oldInner : oldInners) {
			if (!newInners.contains(oldInner)) {
				removedInnerTypeModels.add(oldInner);
				this.compatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			}
		}
		
		for (AbstractClassModel newInner : newInners) {
			if (!oldInners.contains(newInner)) {
				newInnerTypeModels.add(newInner);
				this.compatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
			}
		}
		
		for (AbstractClassModel newInner : newInners) {
			if (oldInners.contains(newInner)) {
				int index = oldInners.indexOf(newInner);
				ClassCompatibilityRecoder innertypeChangeRecoder = new ClassCompatibilityRecoder(oldInners.get(index), newInner); 
				if (innertypeChangeRecoder.getCompatibilityStatus().equals(CompatibilityStatus.COMPATIBILITY)) {
					uncompatibilityInnerTypeModels.add(newInner);
				}else {
					compatibilityInnerTypeModels.add(newInner);
					this.compatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
				}
			}
		}		
		return this.compatibilityStatus;		
	}		
	
	
	private boolean isUnchanged(ChangeStatus changeStatus) {
		return changeStatus.equals(ChangeStatus.UNCHANGED);
	}
	
	public CompatibilityStatus getSuperClassChangeStatus() {
		return superClassCompatibilityStatus;
	}
	public void setSuperClassChangeStatus(CompatibilityStatus superClassChangeStatus) {
		this.superClassCompatibilityStatus = superClassChangeStatus;
	}
	public CompatibilityStatus getSuperInterfaceChangeStatus() {
		return superInterfaceCompatibilityStatus;
	}
	public void setSuperInterfaceChangeStatus(CompatibilityStatus superInterfaceChangeStatus) {
		this.superInterfaceCompatibilityStatus = superInterfaceChangeStatus;
	}
	public CompatibilityStatus getFieldChangeStatus() {
		return fieldCompatibilityStatus;
	}
	public void setFieldChangeStatus(CompatibilityStatus fieldChangeStatus) {
		this.fieldCompatibilityStatus = fieldChangeStatus;
	}
	public CompatibilityStatus getMethodChangeStatus() {
		return methodCompatibilityStatus;
	}
	public void setMethodChangeStatus(CompatibilityStatus methodChangeStatus) {
		this.methodCompatibilityStatus = methodChangeStatus;
	}	

	public SuperClassRecoder getSuperClassRecoder() {
		return superClassRecoder;
	}

	public void setSuperClassRecoder(SuperClassRecoder superClassRecoder) {
		this.superClassRecoder = superClassRecoder;
	}


	public CompatibilityStatus getCompatibilityStatus() {
		return compatibilityStatus;
	}


	public void setChangeStatus(CompatibilityStatus changeStatus) {
		this.compatibilityStatus = changeStatus;
	}
	
	public AbstractClassModel getOldTypeModel(){
		return oldTypeModel;
	}
	
	public AbstractClassModel getNewTypeModel(){
		return newTypeModel;
	}
	
	public MethodRecoder getMethodRecoder(){
		return methodRecoder;
	}
	
	public ModifierRecoder getModifierRecoder(){
		return modifierRecoder;
	}
	
	public ConstructorMethodRecoder getConstructorMethodRecoder(){
		return constructorMethodRecoder;
	}

}

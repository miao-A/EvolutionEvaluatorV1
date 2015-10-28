package cn.seu.edu.integrabilityevaluator.changecomparator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.ClassModel;
import cn.seu.edu.integrabilityevaluator.model.EnumModel;

public class ClassChangeRecoder {

	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus superClassChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus superInterfaceChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus fieldChangeStatus = ChangeStatus.UNCHANGED;
	private ChangeStatus methodChangeStatus = ChangeStatus.UNCHANGED;
	
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
	private List<AbstractClassModel> unchangedInnerTypeModels = new LinkedList<>();
	private Map<AbstractClassModel,AbstractClassModel> modifiedInnerTypeModels = new HashMap<>();
	
	
	public ClassChangeRecoder(AbstractClassModel oldModel,AbstractClassModel newModel){
		this.oldTypeModel = oldModel;
		this.newTypeModel = newModel;
		changeStatus = compareRun();
	}
	
	
	public ChangeStatus compareRun(){
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
		constructorMethodRecoder = new ConstructorMethodRecoder(oldTypeModel.getConstructorModel(), newTypeModel.getConstructorModel());
		
		if (isUnchanged(modifierRecoder.getChangeStatus())&&isUnchanged(superClassRecoder.getChangeStatus())&&isUnchanged(typeParameterRecoder.getChangeStatus())&&
				isUnchanged(superInterfaceClassRecoder.getChangeStatus())&&isUnchanged(fieldRecoder.getChangeStatus())&&isUnchanged(methodRecoder.getChangeStatus())&&
				isUnchanged(constructorMethodRecoder.getChangeStatus())) {
			this.changeStatus = ChangeStatus.UNCHANGED;
		}else {
			this.changeStatus = ChangeStatus.MODIFIED;			
			superClassChangeStatus = superClassRecoder.getChangeStatus();
			superInterfaceChangeStatus = superInterfaceClassRecoder.getChangeStatus();
			fieldChangeStatus = fieldRecoder.getChangeStatus();
			methodChangeStatus = methodRecoder.getChangeStatus();
		}
		
		
		List<AbstractClassModel> oldInners = oldTypeModel.getInnerClassModels();
		List<AbstractClassModel> newInners = newTypeModel.getInnerClassModels();
		
		for (AbstractClassModel oldInner : oldInners) {
			if (!newInners.contains(oldInner)) {
				removedInnerTypeModels.add(oldInner);
				this.changeStatus = ChangeStatus.MODIFIED;
			}
		}
		
		for (AbstractClassModel newInner : newInners) {
			if (!oldInners.contains(newInner)) {
				newInnerTypeModels.add(newInner);
				this.changeStatus = ChangeStatus.MODIFIED;
			}
		}
		
		for (AbstractClassModel newInner : newInners) {
			if (oldInners.contains(newInner)) {
				int index = oldInners.indexOf(newInner);
				ClassChangeRecoder innertypeChangeRecoder = new ClassChangeRecoder(oldInners.get(index), newInner); 
				if (isUnchanged(innertypeChangeRecoder.getChangeStatus())) {
					unchangedInnerTypeModels.add(newInner);
				}else {
					modifiedInnerTypeModels.put(oldInners.get(index), newInner);
					this.changeStatus = ChangeStatus.MODIFIED;
				}
			}
		}		
		return this.changeStatus;		
	}	
	
	
	
	private boolean isUnchanged(ChangeStatus changeStatus) {
		return changeStatus.equals(ChangeStatus.UNCHANGED);
	}
	
	public ChangeStatus getSuperClassChangeStatus() {
		return superClassChangeStatus;
	}
	public void setSuperClassChangeStatus(ChangeStatus superClassChangeStatus) {
		this.superClassChangeStatus = superClassChangeStatus;
	}
	public ChangeStatus getSuperInterfaceChangeStatus() {
		return superInterfaceChangeStatus;
	}
	public void setSuperInterfaceChangeStatus(ChangeStatus superInterfaceChangeStatus) {
		this.superInterfaceChangeStatus = superInterfaceChangeStatus;
	}
	public ChangeStatus getFieldChangeStatus() {
		return fieldChangeStatus;
	}
	public void setFieldChangeStatus(ChangeStatus fieldChangeStatus) {
		this.fieldChangeStatus = fieldChangeStatus;
	}
	public ChangeStatus getMethodChangeStatus() {
		return methodChangeStatus;
	}
	public void setMethodChangeStatus(ChangeStatus methodChangeStatus) {
		this.methodChangeStatus = methodChangeStatus;
	}	

	public SuperClassRecoder getSuperClassRecoder() {
		return superClassRecoder;
	}

	public void setSuperClassRecoder(SuperClassRecoder superClassRecoder) {
		this.superClassRecoder = superClassRecoder;
	}


	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}


	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
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

	public ConstructorMethodRecoder getConstructorMethodRecoder() {
		// TODO Auto-generated method stub
		return constructorMethodRecoder;
	}
	

}

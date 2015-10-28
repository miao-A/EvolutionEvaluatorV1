package cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder;

import java.util.LinkedList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;

/*public class ClassComparator {
	
	private List<AbstractClassModel> removedType = new LinkedList<>();
	private List<AbstractClassModel> newType = new LinkedList<>();
	
	private List<ClassCompatibilityRecoder>  typeChangeRecoders = new LinkedList<>();
	
	public ClassComparator(List<AbstractClassModel> oldModels,List<AbstractClassModel> newModels){

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
		
		for (AbstractClassModel newTypeModel : newModels) {
			if (oldModels.contains(newTypeModel)){
				int index = oldModels.indexOf(newTypeModel);
				typeChangeRecoders.add(new ClassCompatibilityRecoder(oldModels.get(index),newTypeModel));
			}
		}		
	}
	
	public List<ClassCompatibilityRecoder> getTypeChangeRecoders(){

		return typeChangeRecoders;
	}	
	
	
}
*/
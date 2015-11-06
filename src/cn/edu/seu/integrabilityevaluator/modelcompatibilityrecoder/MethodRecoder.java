package cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.edu.seu.integrabilityevaluator.model.MethodModel;




public class MethodRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<MethodModel> oldMethodModels = new LinkedList<>();
	private List<MethodModel> newMethodModels = new LinkedList<>();
	
	private List<MethodModel> newAddMethodModels = new LinkedList<>();
	private List<MethodModel> removedMethodModels = new LinkedList<>();
	private List<MethodModel> unchangedMethodModels = new LinkedList<>();
//	private List<MethodModel> modifiedMethodModels = new LinkedList<>();

	private Map<MethodModel, MethodModel> compatibilityMethodMap = new HashMap<MethodModel, MethodModel>();
	
	private Map<MethodModel, MethodModel> modifiedMethodMap = new HashMap<MethodModel, MethodModel>();
	
	//private Map<MethodModel, MethodModel> uncompatibilityMethodMap = new HashMap<MethodModel, MethodModel>();
	
	private CompatibilityStatus compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	
	public MethodRecoder(List<MethodModel> oldMethodModels,List<MethodModel> newMethodModels) {
		this.oldMethodModels = oldMethodModels;
		this.newMethodModels = newMethodModels;
		compatibilityStatus = compareMethodModel();
	}
	
	public MethodRecoder() {
		// TODO Auto-generated constructor stub
	}

	public CompatibilityStatus compareMethodModel(){
		
		
		if (oldMethodModels.containsAll(newMethodModels)&&newMethodModels.containsAll(oldMethodModels)) {
			setChangeStatus(ChangeStatus.UNCHANGED);
			unchangedMethodModels = oldMethodModels;
		}else {
			setChangeStatus(ChangeStatus.MODIFIED);
			for (MethodModel oldMethodModel : oldMethodModels) {
				if (!newMethodModels.contains(oldMethodModel)) {
					removedMethodModels.add(oldMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
				if (!oldMethodModels.contains(newMethodModel)) {
					newAddMethodModels.add(newMethodModel);
				}			
			}
			
			for (MethodModel newMethodModel : newMethodModels) {
				if (oldMethodModels.contains(newMethodModel)){
					int index = oldMethodModels.indexOf(newMethodModel);
					unchangedMethodModels.add(newMethodModel);	
								
				}
			}
		}
		

		Iterator<MethodModel> removedIterator = removedMethodModels.iterator();
		while (removedIterator.hasNext()) {
			MethodModel reMethodModel = removedIterator.next();
			Iterator<MethodModel> addmethodIterator = newAddMethodModels.iterator();
			while (addmethodIterator.hasNext()) {				
				MethodModel addMethodModel = addmethodIterator.next();
				if (reMethodModel.getMethodName().equals(addMethodModel.getMethodName())) {
					if (addMethodModel.canCompatibility(reMethodModel)) {
						removedIterator.remove();
						addmethodIterator.remove();
						compatibilityMethodMap.put(reMethodModel,addMethodModel);
						break;
					}					
				}				
			}			
		}				
		
		if (removedMethodModels.size() == 0) {
			compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
		}else {
			System.out.println(removedMethodModels.size());
			System.out.println(unchangedMethodModels.size());
			compatibilityStatus = CompatibilityStatus.UNCOMPATIBILITY;
		}	
		
		return compatibilityStatus;
	}

	public Map<MethodModel, MethodModel> getCompatibilityMethodMap(){
		return compatibilityMethodMap;
	}
	
	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public List<MethodModel> getNewAddMethodModels(){
		return newAddMethodModels;
	}
	
	public List<MethodModel> getRemovedMethodModels(){
		return removedMethodModels;
	}
	
	public List<MethodModel> getUnchangedMethodModels(){
		return unchangedMethodModels;
	}

	public Map<MethodModel, MethodModel> getModifiedMethodMap() {
		return modifiedMethodMap;
	}
	
	public int getNewAddMehodNum(){
		int count =0;
		for (MethodModel methodModel : newAddMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getRemovedMehodNum(){
		int count =0;
		for (MethodModel methodModel : removedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getUnchangedMethodNum(){
		int count =0;
		for (MethodModel methodModel : unchangedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}		
		return count;
	}


	public int getModifiedMethodNum(){
		int count =0;	
		for (MethodModel methodModel : modifiedMethodMap.keySet()) {
			if (methodModel.getModifier().isPUBLIC()&&modifiedMethodMap.get(methodModel).getModifier().isPUBLIC()) {
				count++;
			}			
		}
		return count;
	}	
	
	public boolean isCompatibility(){
		return compatibilityStatus.equals(CompatibilityStatus.COMPATIBILITY);	 
		
	}
	
}

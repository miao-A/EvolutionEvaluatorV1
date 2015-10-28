package cn.seu.edu.integrabilityevaluator.changecomparator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.seu.edu.integrabilityevaluator.model.ConstructorMethodModel;
import cn.seu.edu.integrabilityevaluator.model.SingleVariableModel;

public class ConstructorMethodRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<ConstructorMethodModel> oldMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> newMethodModels = new LinkedList<>();
	
	private List<ConstructorMethodModel> newAddMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> removedMethodModels = new LinkedList<>();
	private List<ConstructorMethodModel> unchangedMethodModels = new LinkedList<>();
//	private List<MethodModel> modifiedMethodModels = new LinkedList<>();

//	private Map<ConstructorMethodModel, ConstructorMethodModel> compatibilityMethodMap = new HashMap<ConstructorMethodModel, ConstructorMethodModel>();
	
	private Map<ConstructorMethodModel, ConstructorMethodModel> modifiedMethodMap = new HashMap<ConstructorMethodModel, ConstructorMethodModel>();
	
	//private Map<MethodModel, MethodModel> uncompatibilityMethodMap = new HashMap<MethodModel, MethodModel>();

	public ConstructorMethodRecoder(List<ConstructorMethodModel> oldMethodModels,List<ConstructorMethodModel> newMethodModels) {
		this.oldMethodModels = oldMethodModels;
		this.newMethodModels = newMethodModels;
		changeStatus = compareMethodModel();
	}
	
	public ConstructorMethodRecoder() {
		// TODO Auto-generated constructor stub
	}

	public ChangeStatus compareMethodModel(){
		
		if (oldMethodModels.containsAll(newMethodModels)&&newMethodModels.containsAll(oldMethodModels)) {
			setChangeStatus(ChangeStatus.UNCHANGED);
			unchangedMethodModels = oldMethodModels;
		}else {
			setChangeStatus(ChangeStatus.MODIFIED);
			for (ConstructorMethodModel oldMethodModel : oldMethodModels) {
				if (!newMethodModels.contains(oldMethodModel)) {
					removedMethodModels.add(oldMethodModel);
				}			
			}
			
			for (ConstructorMethodModel newMethodModel : newMethodModels) {
				if (!oldMethodModels.contains(newMethodModel)) {
					newAddMethodModels.add(newMethodModel);
				}			
			}
			
			for (ConstructorMethodModel newMethodModel : newMethodModels) {
				if (oldMethodModels.contains(newMethodModel)){
					//int index = oldMethodModels.indexOf(newMethodModel);
					unchangedMethodModels.add(newMethodModel);	
								
				}
			}
			
		}
		

		Iterator<ConstructorMethodModel> removedIterator = removedMethodModels.iterator();
		while (removedIterator.hasNext()) {
			ConstructorMethodModel reMethodModel = removedIterator.next();
			Iterator<ConstructorMethodModel> addmethodIterator = newAddMethodModels.iterator();
			while (addmethodIterator.hasNext()) {				
				ConstructorMethodModel addMethodModel = addmethodIterator.next();
				if (reMethodModel.getMethodName().equals(addMethodModel.getMethodName())) {
					List<SingleVariableModel> oldList = reMethodModel.getFormalParameters();
					List<SingleVariableModel> newList = addMethodModel.getFormalParameters();
					if (oldList.size() == newList.size()) {
						boolean flag = true;
						for (int i = 0; i < oldList.size(); i++) {
							if (!oldList.get(i).equals(newList.get(i))) {
								flag = false;
							}
						}
						
						if (flag) {
							removedIterator.remove();
							addmethodIterator.remove();
							modifiedMethodMap.put(reMethodModel,addMethodModel);
							break;
						}						
					}								
				}				
			}			
		}		
		return changeStatus;
	}

	/*public Map<ConstructorMethodModel, ConstructorMethodModel> getCompatibilityConstructorMethodMap(){
		return compatibilityMethodMap;
	}*/
	
	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public List<ConstructorMethodModel> getNewAddMethodModels(){
		return newAddMethodModels;
	}
	
	public List<ConstructorMethodModel> getRemovedMethodModels(){
		return removedMethodModels;
	}
	
	public List<ConstructorMethodModel> getUnchangedMethodModels(){
		return unchangedMethodModels;
	}

	public Map<ConstructorMethodModel, ConstructorMethodModel> getModifiedMethodMap() {
		return modifiedMethodMap;
	}
	
	public int getNewAddMehodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : newAddMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getRemovedMehodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : removedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}
	
	public int getUnchangedMethodNum(){
		int count =0;
		for (ConstructorMethodModel methodModel : unchangedMethodModels) {
			if (methodModel.getModifier().isPUBLIC()) {
				count++;
			}
		}
		return count;
	}


	public int getModifiedMethodNum(){
		int count =0;	
		for (ConstructorMethodModel methodModel : modifiedMethodMap.keySet()) {
			if (methodModel.getModifier().isPUBLIC()&&modifiedMethodMap.get(methodModel).getModifier().isPUBLIC()) {
				count++;
			}			
		}
		return count;
	}
	
	public boolean isUnchanged(){
		return changeStatus.equals(ChangeStatus.UNCHANGED);	 
		
	}

}

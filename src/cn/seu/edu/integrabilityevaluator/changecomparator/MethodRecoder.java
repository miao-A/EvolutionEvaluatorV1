package cn.seu.edu.integrabilityevaluator.changecomparator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.seu.edu.integrabilityevaluator.model.MethodModel;
import cn.seu.edu.integrabilityevaluator.model.SingleVariableModel;




public class MethodRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private List<MethodModel> oldMethodModels = new LinkedList<>();
	private List<MethodModel> newMethodModels = new LinkedList<>();
	
	private List<MethodModel> newAddMethodModels = new LinkedList<>();
	private List<MethodModel> removedMethodModels = new LinkedList<>();
	private List<MethodModel> unchangedMethodModels = new LinkedList<>();
//	private List<MethodModel> modifiedMethodModels = new LinkedList<>();

	private Map<MethodModel, MethodModel> modifiedMethodMap = new HashMap<MethodModel, MethodModel>();
	
	
	public MethodRecoder(List<MethodModel> oldMethodModels,List<MethodModel> newMethodModels) {
		this.oldMethodModels = oldMethodModels;
		this.newMethodModels = newMethodModels;
		changeStatus = compareMethodModel();
	}
	
	public MethodRecoder() {
		// TODO Auto-generated constructor stub
	}

	public ChangeStatus compareMethodModel(){
		
		
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
					List<SingleVariableModel> oldList = reMethodModel.getFormalParameters();
					List<SingleVariableModel> newList = addMethodModel.getFormalParameters();
					if (oldList.size() == newList.size()) {						
						boolean flag = true;
						for (int i = 0; i < oldList.size(); i++) {
							if (oldList.get(i).getName().equals("theClass")&&newList.get(i).getName().equals("theClass")) {
								System.out.println();
							}
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
			count++;
		}
		return count;
	}
	
	public int getRemovedMehodNum(){
		int count =0;
		for (MethodModel methodModel : removedMethodModels) {
			count++;
		}
		return count;
	}
	
	public int getUnchangedMethodNum(){
		int count =0;
		for (MethodModel methodModel : unchangedMethodModels) {
			count++;
		}		
		return count;
	}


	public int getModifiedMethodNum(){
		int count =0;	
		for (MethodModel methodModel : modifiedMethodMap.keySet()) {
			count++;
						
		}
		return count;
	}	
	
	public boolean isUnchanged(){
		return changeStatus.equals(ChangeStatus.UNCHANGED);	 		
	}
	
}

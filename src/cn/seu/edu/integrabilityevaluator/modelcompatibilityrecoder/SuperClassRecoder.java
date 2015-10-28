package cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder;


public class SuperClassRecoder {
	
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	
	private String oldSuperClass = "";
	private String newSuperClass = "";
	
	public SuperClassRecoder(String oldString,String newString){
		if (oldString == null) {
			oldSuperClass = "";
		}else {
			oldSuperClass = oldString;
		}
		
		if (newString == null) {
			newSuperClass = "";
		}else {
			newSuperClass = newString;
		}
		changeStatus = compareSuperClass();
	}	
	
	public SuperClassRecoder() {
		// TODO Auto-generated constructor stub
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}
	
	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}	
	
	public String getOldSuperClass() {
		return oldSuperClass;
	}
	public void setOldSuperClass(String oldSuperClass) {
		this.oldSuperClass = oldSuperClass;
	}
	public String getNewSuperClass() {
		return newSuperClass;
	}
	public void setNewSuperClass(String newSuperClass) {
		this.newSuperClass = newSuperClass;
	}
	
	public ChangeStatus compareSuperClass(){
		if (oldSuperClass.equals(newSuperClass)) {
			return ChangeStatus.UNCHANGED;
		}else {
			return ChangeStatus.MODIFIED;
		}
	}	
	
}

package cn.seu.edu.integrabilityevaluator.modelcompatibilityrecoder;

import java.util.LinkedList;
import java.util.List;

public class EnumConstantRecoder {

	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	
	private List<String> oldEnumConstants = new LinkedList<>();
	private List<String> newEnumConstants = new LinkedList<>();
	
	public EnumConstantRecoder(){
	}
	
	public EnumConstantRecoder(List<String> olds,List<String> news){
		oldEnumConstants = olds;
		newEnumConstants = news;
		changeStatus = compareEnumConstants();		
	}
	
	
	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}
	
	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public ChangeStatus compareEnumConstants(){
		if(oldEnumConstants.containsAll(newEnumConstants)&&newEnumConstants.containsAll(oldEnumConstants)) {
			return ChangeStatus.UNCHANGED;
		}else {
			return ChangeStatus.MODIFIED;
		}
	}	
}

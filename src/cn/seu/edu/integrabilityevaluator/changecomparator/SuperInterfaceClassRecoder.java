package cn.seu.edu.integrabilityevaluator.changecomparator;

import java.util.LinkedList;
import java.util.List;

public class SuperInterfaceClassRecoder {
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	
	private List<String> oldSuperInterfaceClasses = new LinkedList<>();
	private List<String> newSuperInterfaceClasses = new LinkedList<>();

	
	public SuperInterfaceClassRecoder(List<String> olds,List<String> news){
		oldSuperInterfaceClasses = olds;
		newSuperInterfaceClasses = news;
		changeStatus = compareSuperInterfaceClasses();
		
	}
	
	
	public SuperInterfaceClassRecoder() {
		// TODO Auto-generated constructor stub
	}


	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}
	
	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}
	
	public ChangeStatus compareSuperInterfaceClasses(){
		if(oldSuperInterfaceClasses.containsAll(newSuperInterfaceClasses)&&newSuperInterfaceClasses.containsAll(oldSuperInterfaceClasses)) {
			return ChangeStatus.UNCHANGED;
		}else {
			return ChangeStatus.MODIFIED;
		}
	}	
}

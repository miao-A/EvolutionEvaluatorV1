package cn.seu.edu.integrabilityevaluator.changecomparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.Flags;

import cn.seu.edu.integrabilityevaluator.model.JModifier;

public class ModifierRecoder {
	private JModifier oldModifier;
	private JModifier newModifier;
	private Map<String,ChangeStatus> modifierChangeStatus = new HashMap<>();
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;	
	private boolean changeflag = false; 
	
	public ModifierRecoder(JModifier oldJModifier,JModifier newModifier){
		this.oldModifier = oldJModifier;
		this.newModifier = newModifier;
		if(oldJModifier.CanCompatibility(newModifier)){
			changeStatus = ChangeStatus.UNCHANGED;
		}else {
			changeStatus = ChangeStatus.MODIFIED;
		}
	}
	
	public ModifierRecoder() {
		// TODO Auto-generated constructor stub
	}

	
	public boolean isCompatibility(){		
		if(this.oldModifier.CanCompatibility(this.newModifier)){
			return true;
		}else {
			return false;
		}		
	}	
	
	public Map<String, ChangeStatus> getModifierChangeStatus(){
		return modifierChangeStatus;
	}

	public boolean hasChange() {
		return changeflag;
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

}

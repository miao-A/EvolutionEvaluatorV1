package cn.edu.seu.integrabilityevaluator.modelcompatibilityrecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.Flags;

import cn.edu.seu.integrabilityevaluator.model.JModifier;

public class ModifierRecoder {
	private JModifier oldModifier;
	private JModifier newModifier;
	private Map<String,ChangeStatus> modifierChangeStatus = new HashMap<>();
	private ChangeStatus changeStatus = ChangeStatus.UNCHANGED;
	private CompatibilityStatus compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
	
	private boolean changeflag = false; 
	
	public ModifierRecoder(JModifier oldJModifier,JModifier newModifier){
		this.oldModifier = oldJModifier;
		this.newModifier = newModifier;
		if(oldJModifier.CanCompatibility(newModifier)){
			compatibilityStatus = CompatibilityStatus.COMPATIBILITY;
		}else {
			compatibilityStatus = compatibilityStatus.UNCOMPATIBILITY;
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
		
		/*if (!oldModifier.isABSTRACT()) {
			if (newModifier.isABSTRACT()) {
				return false;
			}
		}
		
		if (!oldModifier.isFINAL()) {
			if (newModifier.isFINAL()) {
				return false;
			}
		}
		
		if (oldModifier.isPUBLIC()) {
			if (!newModifier.isPUBLIC()) {
				return false;
			}
		}
		
		if ((!oldModifier.isPRIVATE())&&(oldModifier.isSTATIC())) {
			if (!newModifier.isSTATIC()) {
				return false;
			}
		}
		
		if (oldModifier.isPROTECTED()&&newModifier.isPRIVATE()) {
			return false;
		}		
		return true;*/
	}
	
	public void compareModifier() {
		// TODO Auto-generated method stub		
		
		if (oldModifier.isABSTRACT()^newModifier.isABSTRACT()) {
			modifierChangeStatus.put("abstract", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("abstract", ChangeStatus.UNCHANGED);			
		}
		
		if (oldModifier.isFINAL()^newModifier.isFINAL()) {
			modifierChangeStatus.put("final", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("final", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isNATIVE()^newModifier.isNATIVE()) {
			modifierChangeStatus.put("native", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("native", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isPRIVATE()^newModifier.isPRIVATE()) {
			modifierChangeStatus.put("private", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("private", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isPROTECTED()^newModifier.isPROTECTED()) {
			modifierChangeStatus.put("protected", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("protected", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isPUBLIC()^newModifier.isPUBLIC()) {
			modifierChangeStatus.put("public", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("public", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isSTATIC()^newModifier.isSTATIC()) {
			modifierChangeStatus.put("static", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("static", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isSTRICTFP()^newModifier.isSTRICTFP()) {
			modifierChangeStatus.put("strictfp", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("strictfp", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isSYNCHRONIZED()^newModifier.isSYNCHRONIZED()) {
			modifierChangeStatus.put("synchronized", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("synchronized", ChangeStatus.UNCHANGED);
		}
		
		
		if (oldModifier.isTRANSIENT()^newModifier.isTRANSIENT()) {
			modifierChangeStatus.put("transient", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("transient", ChangeStatus.UNCHANGED);
		}
		
		if (oldModifier.isVOLATILE()^newModifier.isVOLATILE()) {
			modifierChangeStatus.put("volatile", ChangeStatus.MODIFIED);
			changeflag = true;
		}else {
			modifierChangeStatus.put("volatile", ChangeStatus.UNCHANGED);
		}
		
		if (changeflag) {
			setChangeStatus(ChangeStatus.MODIFIED);
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

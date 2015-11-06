package cn.edu.seu.integrabilityevaluator.model;

public class JModifier {
		
	private boolean PUBLIC = false;
	private boolean PRIVATE = false;
	private boolean PROTECTED = false;
	private boolean STATIC = false;
	private boolean ABSTRACT = false;
	private boolean FINAL = false;
	private boolean NATIVE = false;
	private boolean SYNCHRONIZED = false;
	private boolean TRANSIENT = false;
	private boolean VOLATILE = false;
	private boolean STRICTFP = false;
	
	public boolean isPUBLIC() {
		return PUBLIC;
	}
	
	public void setPUBLIC(boolean pUBLIC) {
		PUBLIC = pUBLIC;
	}

	public boolean isPRIVATE() {
		return PRIVATE;
	}

	public void setPRIVATE(boolean pRIVATE) {
		PRIVATE = pRIVATE;
	}

	public boolean isPROTECTED() {
		return PROTECTED;
	}

	public void setPROTECTED(boolean pROTECTED) {
		PROTECTED = pROTECTED;
	}

	public boolean isSTATIC() {
		return STATIC;
	}

	public void setSTATIC(boolean sTATIC) {
		STATIC = sTATIC;
	}

	public boolean isABSTRACT() {
		return ABSTRACT;
	}

	public void setABSTRACT(boolean aBSTRACT) {
		ABSTRACT = aBSTRACT;
	}

	public boolean isFINAL() {
		return FINAL;
	}

	public void setFINAL(boolean fINAL) {
		FINAL = fINAL;
	}

	public boolean isNATIVE() {
		return NATIVE;
	}

	public void setNATIVE(boolean nATIVE) {
		NATIVE = nATIVE;
	}

	public boolean isTRANSIENT() {
		return TRANSIENT;
	}

	public void setTRANSIENT(boolean tRANSIENT) {
		TRANSIENT = tRANSIENT;
	}

	public boolean isVOLATILE() {
		return VOLATILE;
	}

	public void setVOLATILE(boolean vOLATILE) {
		VOLATILE = vOLATILE;
	}

	public boolean isSYNCHRONIZED() {
		return SYNCHRONIZED;
	}

	public void setSYNCHRONIZED(boolean sYNCHRONIZED) {
		SYNCHRONIZED = sYNCHRONIZED;
	}

	public boolean isSTRICTFP() {
		return STRICTFP;
	}

	public void setSTRICTFP(boolean sTRICTFP) {
		STRICTFP = sTRICTFP;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof JModifier) {
			if(this.isABSTRACT()^((JModifier) obj).isABSTRACT())
			{
				return false;
			}
			
			if(this.isFINAL()^((JModifier) obj).isFINAL())
			{
				return false;
			}
			
			if(this.isNATIVE()^((JModifier) obj).isNATIVE())
			{
				return false;
			}
			if(this.isPRIVATE()^((JModifier) obj).isPRIVATE())
			{
				return false;
			}
			if(this.isPROTECTED()^((JModifier) obj).isPROTECTED())
			{
				return false;
			}
			if(this.isPUBLIC()^((JModifier) obj).isPUBLIC())
			{
				return false;
			}
			if(this.isSTATIC()^((JModifier) obj).isSTATIC())
			{
				return false;
			}
			if(this.isSTRICTFP()^((JModifier) obj).isSTRICTFP())
			{
				return false;
			}
			if(this.isSYNCHRONIZED()^((JModifier) obj).isSYNCHRONIZED())
			{
				return false;
			}
			if(this.isTRANSIENT()^((JModifier) obj).isTRANSIENT())
			{
				return false;
			}
			if(this.isVOLATILE()^((JModifier) obj).isVOLATILE())
			{
				return false;
			}
				
		}	
		
		return true;
		
	}
	
	
	public String getModifierInfo(){
		String string = new String();
		if (isPRIVATE()) {
			string += "private ";
		}

		if (isPUBLIC()) {
			string += "public ";
		}

		if (isPROTECTED()) {
			string += "protected ";
		}
		
		if (isABSTRACT()) {
			string += "abstract ";
		}
		

		if (isSTRICTFP()) {
			string += "strictfp ";
		}
		
		
		if (isFINAL()) {
			string += "final ";
		}
		
		if (isSTATIC()) {
			string += "static ";
		}

		if (isNATIVE()) {
			string += "native ";
		}
		
		if (isSYNCHRONIZED()) {
			string += "synchronized ";
		}
		
		if (isTRANSIENT()) {
			string += "transient ";
		}
		
		if (isVOLATILE()) {
			string += "volatile ";
		}	

		return string;
	}

	public boolean CanCompatibility(JModifier newModifier){
		if (!this.isABSTRACT()) {
			if (newModifier.isABSTRACT()) {
				return false;
			}
		}
		
		if (!this.isFINAL()) {
			if (newModifier.isFINAL()) {
				return false;
			}
		}
		
		if (this.isPUBLIC()) {
			if (!newModifier.isPUBLIC()) {
				return false;
			}
		}
		
		if ((!this.isPRIVATE())&&(this.isSTATIC())) {
			if (!newModifier.isSTATIC()) {
				return false;
			}
		}
		
		if (this.isPROTECTED()&&newModifier.isPRIVATE()) {
			return false;
		}		
		return true;
	}


}

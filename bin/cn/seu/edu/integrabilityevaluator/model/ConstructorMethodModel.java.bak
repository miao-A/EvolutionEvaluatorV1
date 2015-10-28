package cn.seu.edu.integrabilityevaluator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConstructorMethodModel {
	
private String methodName;	
	
	private JModifier modifier = new JModifier();
	private List<String> typeParameters = new LinkedList<>();
	
	private int extraDimensions = 0;
	
	private ArrayList<SingleVariableModel> formalParameters = new ArrayList<SingleVariableModel>();
	private List<String> thrownList = new LinkedList<>();	
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodeName) {
		this.methodName = methodeName;
	}

	
	public void setModifier(JModifier jModifier){
		modifier = jModifier;
	}
	
	public JModifier getModifier(){
		return modifier;
	}

	
	
	public List<String> getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(List<String> typeParameters) {
		this.typeParameters = typeParameters;
	}	
	
	public void addTypeParameter(String typeParameter){
		this.typeParameters.add(typeParameter);
	}

	
	public List<SingleVariableModel> getFormalParameters() {
		return formalParameters;
	}

	public void setFormalParameters(ArrayList<SingleVariableModel> formalParameters) {
		this.formalParameters = formalParameters;
	}
	
	public void addFormalParameters(SingleVariableModel formalParameter) {
		this.formalParameters.add(formalParameter);
	}

	public int getExtraDimensions() {
		return extraDimensions;
	}

	public void setExtraDimensions(int extraDimensions) {
		this.extraDimensions = extraDimensions;
	}

	public List<String> getThrownList() {
		return thrownList;
	}

	public void setThrownList(List<String> thrownList) {
		this.thrownList = thrownList;
	}
	
	public void addThrownList(String thrown) {
		this.thrownList.add(thrown);
	}
	
	public String getFullName(){
		try{
		String string = new String();
		string += this.getModifier().getModifierInfo()+" "
				+this.getMethodName()+"(";
		List<SingleVariableModel> tpList =  this.getFormalParameters();
		for (int i = 0; i < tpList.size(); i++) {
			string += tpList.get(i).getFullName();
			if (i!=tpList.size()-1) {
				string +=",";
			}
		}
		string += ")";
		return string;
		}catch(NullPointerException n)
		{
			System.out.println("");
			return "";
		}
		
		
		
	}

	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}		
	
		if( this.getMethodName().equals(((ConstructorMethodModel) obj).getMethodName())){
			List<SingleVariableModel> oldList = this.getFormalParameters();
			List<SingleVariableModel> newList = ((ConstructorMethodModel) obj).getFormalParameters();
			if (oldList.size() != newList.size()) {
				return false;
			}
			
			for (int i = 0; i < oldList.size(); i++) {
				if (!oldList.get(i).equals(newList.get(i))) {
					return false;
				};
			}
			
			if (!this.getModifier().equals(((ConstructorMethodModel) obj).getModifier())) {
				return false;
			}
			
			return true;
			
		}else {
			return false;
		}			

	}
	
	public boolean canCompatibility(ConstructorMethodModel removedModel){		
		if(!removedModel.getModifier().CanCompatibility(this.getModifier())){
			return false;
		}
		
		if (this.getFormalParameters().size() != removedModel.getFormalParameters().size()){
			return false;
		}else if (this.getFormalParameters().size() == removedModel.getFormalParameters().size()) {				
			List<SingleVariableModel> removed = removedModel.getFormalParameters();
			List<SingleVariableModel> newadd = this.getFormalParameters();
			for (int i = 0; i < removed.size(); i++) {
				if(!newadd.get(i).getType().CanCompatibility(removed.get(i).getType())){
					return false;
				}
			}
		}		
		
		return true;		
	}


}

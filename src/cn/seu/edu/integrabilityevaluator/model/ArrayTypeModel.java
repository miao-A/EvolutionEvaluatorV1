package cn.seu.edu.integrabilityevaluator.model;

import org.omg.CORBA.PUBLIC_MEMBER;

public class ArrayTypeModel extends TypeModel {
	private int dimiensions;
	private SimpleTypeModel elementType;
	
	public ArrayTypeModel(String typeName){
		super(typeName);
		dimiensions = 0;
		elementType = new SimpleTypeModel(typeName);
	}
	
	
	public ArrayTypeModel(String typeName,int dimiensions,String element){
		super(typeName);
		this.dimiensions = dimiensions;
		elementType = new SimpleTypeModel(element);
	}
	
	public SimpleTypeModel getElementType(){
		return elementType;
	}


	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		if (this == typeModel) {
			return true;
		}
		
		if (typeModel instanceof SimpleTypeModel) {
			if (elementType.CanCompatibility(typeModel)) {
				return true;
			}
		}
		if (typeModel instanceof ArrayTypeModel) {
			if (this.getElementType().CanCompatibility(((ArrayTypeModel) typeModel).getElementType())&&(this.getDimiensions() == ((ArrayTypeModel) typeModel).getDimiensions())) {
				return true;
			}
			
			
		}
		return false;
	}
	
	public int getDimiensions(){
		return dimiensions;
	}


	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = "";
		string += this.getTypeName();
		for (int i = 0; i < dimiensions; i++) {
			string += "[]";
		}
		return string;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ArrayTypeModel) {
			if (this.getTypeName().equals(((ArrayTypeModel) obj).getTypeName())&&(this.getDimiensions() == ((ArrayTypeModel) obj).getDimiensions())) {
				return true;
			}
		}
		return false;
	}
	
	
}

package cn.seu.edu.integrabilityevaluator.model;

public class PrimitiveTypeModel extends TypeModel {

	public PrimitiveTypeModel(String typeName){
		super(typeName);
	}

	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		if (typeModel instanceof PrimitiveTypeModel) {
			if (typeModel.getTypeName().equals("void")&&this.getTypeName().equals("void")) {
				return true;				
			}			
			
			if (typeModel.getTypeName().equals("char")) {
				if (this.getTypeName().equals("char")||this.getTypeName().equals("long")||this.getTypeName().equals("float")||this.getTypeName().equals("double")) {
					return true;
				}
			}
			
			if (typeModel.getTypeName().equals("byte")) {
				if (this.getTypeName().equals("byte")||this.getTypeName().equals("int")) {
					return true;
				}
			}
			
			if (typeModel.getTypeName().equals("short")) {
				if (this.getTypeName().equals("short")||this.getTypeName().equals("int")) {
					return true;
				}
			}

			if (typeModel.getTypeName().equals("int")) {
				if (this.getTypeName().equals("long")||this.getTypeName().equals("int")||this.getTypeName().equals("float")||this.getTypeName().equals("double")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("long")) {
				if (this.getTypeName().equals("long")||this.getTypeName().equals("float")||this.getTypeName().equals("double")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("float")) {
				if (this.getTypeName().equals("float")||this.getTypeName().equals("double")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("double")) {
				if (this.getTypeName().equals("double")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("boolean")) {
				if (this.getTypeName().equals("boolean")) {
					return true;
				}
			}
			
		}
		
		return false;
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return this.getTypeName();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof PrimitiveTypeModel) {
			if (this.getFullName().equals(((PrimitiveTypeModel) obj).getFullName())) {
				return true;
			}
		}
		return false;
	}

	
}

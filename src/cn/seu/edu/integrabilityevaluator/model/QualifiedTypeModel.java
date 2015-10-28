package cn.seu.edu.integrabilityevaluator.model;
//不确定类型表现形式
/*QualifiedType:
    Type . SimpleName*/
 
public class QualifiedTypeModel extends TypeModel {
	
	private String qualifiedString = "";
	
	public void setQualifiedName(String string){
		this.qualifiedString = string;
	}

	
	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = qualifiedString+"."+this.getTypeName();
		return this.getTypeName();
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
}


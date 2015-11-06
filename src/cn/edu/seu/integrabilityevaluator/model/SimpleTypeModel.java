package cn.edu.seu.integrabilityevaluator.model;

import java.util.HashMap;
import java.util.List;

import javax.security.auth.kerberos.KerberosKey;

import org.eclipse.swt.internal.win32.INITCOMMONCONTROLSEX;

//后添加父类或集成信息
public class SimpleTypeModel extends TypeModel {

	
	
	private String superClass = "Object";
	
	public SimpleTypeModel(String typeName){
		super(typeName);		
	}
	
	public String getSuperClass() {
		return superClass;
	}
	public void setSuperClass(String superClass) {
		if (superClass == null) {
			return;
		}
		this.superClass = superClass;
		
	}
		
	
	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		if (typeModel instanceof SimpleTypeModel) {
			if (this.getTypeName().equals("Object")){
				return true;
			}
			if (this.getTypeName().equals(((SimpleTypeModel) typeModel).getTypeName())||this.getTypeName().equals(((SimpleTypeModel) typeModel).getSuperClass())) {
				return true;
			}
		}else if (typeModel instanceof PrimitiveTypeModel) {			
			if (typeModel.getTypeName().equals("void")&&this.getTypeName().equals("void")) {
				return true;				
			}
			
			if (typeModel.getTypeName().equals("char")) {
				if (this.getTypeName().equals("Char")) {
					return true;
				}
			}
			
			
			if (typeModel.getTypeName().equals("byte")) {
				if (this.getTypeName().equals("Byte")) {
					return true;
				}
			}
			
			if (typeModel.getTypeName().equals("short")) {
				if (this.getTypeName().equals("Short")) {
					return true;
				}
			}
			
			if (typeModel.getTypeName().equals("int")) {
				if (this.getTypeName().equals("Integer")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("long")) {
				if (this.getTypeName().equals("Long")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("float")) {
				if (this.getTypeName().equals("Float")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("double")) {
				if (this.getTypeName().equals("Double")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("boolean")) {
				if (this.getTypeName().equals("Boolean")) {
					return true;
				}
			}
			if (typeModel.getTypeName().equals("long")) {
				if (this.getTypeName().equals("Long")) {
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
		if (obj instanceof SimpleTypeModel) {
			if (this.getTypeName().equals(((SimpleTypeModel) obj).getTypeName())) {
				return true;
			}
		}
		return false;
	}
	
	
}


package cn.edu.seu.integrabilityevaluator.model;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WildcardType;
//通配符类型
public class WildCardTypeModel extends TypeModel {


	private boolean upperBound;
	private TypeModel typeModel;
	
	public  WildCardTypeModel(Boolean upperBound, Type type) {
		super("wildcard");
		this.typeModel = setType(type);
		this.upperBound = upperBound;
	}	
	
	public boolean isUpperBound() {
		return upperBound;
	}

	public void setUpperBound(boolean upperBound) {
		this.upperBound = upperBound;
	}

	public TypeModel getTypeModel(){
		return typeModel;
	}
	
	/*public String getBoundType() {
		return boundType;
	}

	public void setBoundType(String boundType) {
		this.boundType = boundType;
	}
*/

	@Override
	public boolean CanCompatibility(TypeModel obj) {
		// TODO Auto-generated method stub
		if (obj instanceof WildCardTypeModel) {
			if ((this.isUpperBound()==((WildCardTypeModel) obj).isUpperBound())&&this.getTypeModel().CanCompatibility(((WildCardTypeModel) obj).getTypeModel())) {
				return true;
			}
		}
		
		return false;
	}


	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = "<";
		string += "? extends ";
		string += typeModel.getFullName();
		string +=">";
		return string;
	}



	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		
		if (obj instanceof WildCardTypeModel) {
			if ((this.isUpperBound()==((WildCardTypeModel) obj).isUpperBound())&&this.getTypeModel().equals(((WildCardTypeModel) obj).getTypeModel())) {
				return true;
			}
		}
		return false;
	}
	
	public TypeModel setType(Type type) {
		
		TypeModel typeModel = null;
		if(type == null){
			return typeModel = new SimpleTypeModel("Object");
		}
		
		if (type instanceof PrimitiveType) {
			System.out.println(type.getClass().getName()+" "+type.toString());
			typeModel = new PrimitiveTypeModel(type.toString());
		}else if (type instanceof ArrayType) {
			System.out.println(type.getClass().getName()+" "+((ArrayType) type).getComponentType().toString()+" "+((ArrayType) type).getDimensions()+" "+((ArrayType) type).getElementType().toString());						
			typeModel = new ArrayTypeModel(((ArrayType) type).getComponentType().toString(), ((ArrayType) type).getDimensions(), ((ArrayType) type).getElementType().toString());
		}else if (type instanceof SimpleType) {
			System.out.println(type.getClass().getName()+" "+((SimpleType) type).getName());
			typeModel = new SimpleTypeModel(((SimpleType) type).getName().toString());
		}else if (type instanceof QualifiedType) {
			System.out.println(type.getClass().getName());
			typeModel = new QualifiedTypeModel();
			typeModel.setTypeName(((QualifiedType) type).getName().toString());
			((QualifiedTypeModel) typeModel).setQualifiedName(((QualifiedType) type).getQualifier().toString());
			System.out.println(typeModel.getFullName());
		}else if (type instanceof WildcardType) {
			System.out.println(type.getClass().getName());
			
			typeModel = new WildCardTypeModel(((WildcardType)type).isUnionType(),((WildcardType)type).getBound());
			
		}else if (type instanceof ParameterizedType) {
			System.out.println(type.getClass().getName()+" "+((ParameterizedType) type).getType().toString()+" ");
			typeModel = new ParameterizedTypeModel(((ParameterizedType) type).getType().toString());
			List<Type> types = ((ParameterizedType) type).typeArguments();
			((ParameterizedTypeModel) typeModel).setTypeArguments(types);						
		}else if (type instanceof UnionType) {
			System.out.println("Union");
		}		
		return typeModel;
	}
	
	
}

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

public class SingleVariableModel {

	private String name="";
	private JModifier modifier;
	private TypeModel typeModel;
	private boolean varargs = false;
	private int extraDimensions = 0;
	
	public SingleVariableModel(Type type, String name){
		setType(type);
		this.name = name;			
	}
	
	public SingleVariableModel() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JModifier getModifier() {
		return modifier;
	}	
	
	public void setModifier(JModifier modifier) {
		this.modifier = modifier;
	}
	public TypeModel getType() {
		
		return typeModel;
	}
	
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = typeModel.getFullName()+" "+name; 
		return string;
	}
	
	public void setType(Type type) {
		
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
		
	}
	public boolean isVarargs() {
		return varargs;
	}
	public void setVarargs(boolean varargs) {
		this.varargs = varargs;
	}
	public int getExtraDimensions() {
		return extraDimensions;
	}
	public void setExtraDimensions(int extraDimensions) {
		this.extraDimensions = extraDimensions;
	}	
    
	
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof SingleVariableModel) {
			/*if (this.getName().equals(((SingleVariableModel) obj).getName())&&this.getType().getTypeName().equals(((SingleVariableModel) obj).getType().getTypeName())&&(this.isVarargs()==((SingleVariableModel) obj).isVarargs())) {
				return true;
			}*/		
			if (this.getType().equals(((SingleVariableModel) obj).getType())&&this.getName().equals(((SingleVariableModel) obj).getName())&&(this.isVarargs()==((SingleVariableModel) obj).isVarargs())) {
				return true;
			}
		}		
		return false;
	}

}




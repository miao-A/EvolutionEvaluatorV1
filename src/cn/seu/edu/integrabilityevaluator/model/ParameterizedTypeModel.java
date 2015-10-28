package cn.seu.edu.integrabilityevaluator.model;

import java.io.ObjectInputStream.GetField;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WildcardType;

public class ParameterizedTypeModel extends TypeModel {
	
	
	private TypeModel typeModel;
	private List<TypeModel> typeArguments = new LinkedList<>();
	private SimpleTypeModel simpletype;
	
	
	public ParameterizedTypeModel(String typeName){
		super(typeName);
		simpletype = new SimpleTypeModel(typeName);
	}
	
	public SimpleTypeModel getSimpleType(){
		return simpletype;
	}

	public List<TypeModel> getTypeArguments() {
		return typeArguments;
	}

	public void setTypeArguments(List<Type> types) {
		for (Type type : types) {
			TypeModel typeModel= getType(type);
			/*if (type instanceof PrimitiveType) {
				typeModel = new PrimitiveTypeModel(type.toString());
			}else if (type instanceof ArrayType) {
				typeModel = new ArrayTypeModel(((ArrayType) type).getComponentType().toString(), ((ArrayType) type).getDimensions(), ((ArrayType) type).getElementType().toString());
			}else if (type instanceof SimpleType) {
				typeModel = new SimpleTypeModel(((SimpleType) type).getName().toString());
			}else if (type instanceof QualifiedType) {
				System.out.println(type.getClass().getName());
			}else if (type instanceof WildcardType) {
				System.out.println(type.getClass().getName());
				typeModel = new WildCardTypeModel(((WildcardType)type).isUnionType(),setType(((WildcardType)type).getBound()));
			}else if (type instanceof ParameterizedType) {
				typeModel = new ParameterizedTypeModel(((ParameterizedType) type).getType().toString());
				List<Type> types2 = ((ParameterizedType) type).typeArguments();
				((ParameterizedTypeModel) typeModel).setTypeArguments(types2);						
			}else if (type instanceof UnionType) {
				System.out.println("Union");
			}*/		
			
			this.typeArguments.add(typeModel);
		}

	}
	
	public TypeModel getType(Type type) {		
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

	@Override
	public boolean CanCompatibility(TypeModel typeModel) {
		// TODO Auto-generated method stub
		if (this == typeModel) {
			return true;
		}
		
		
		if (typeModel instanceof ParameterizedTypeModel) {
			this.getSimpleType().getTypeName().equals(((ParameterizedTypeModel) typeModel).getSimpleType().getTypeName());
			List<TypeModel> thisList = this.getTypeArguments();
			List<TypeModel> otherList = ((ParameterizedTypeModel) typeModel).getTypeArguments();
			if (thisList.size() != otherList.size()) {
				return false;
			}else {
				for (int i = 0; i < thisList.size(); i++) {
					if(!thisList.get(i).CanCompatibility(otherList.get(i))){
						return false;
					}
				}
				return true;
			}
		}		
		return false;
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		String string = "";
		string +=this.simpletype.getFullName();
		if (typeArguments.size()!=1) {
			string += "<";
		}		
		for (int i = 0; i < typeArguments.size(); i++) {
			string += typeArguments.get(i).getFullName();
			if (i == typeArguments.size()-1) {
				if (typeArguments.size()!=1) {
					string += ">";
				}
			}else {
				string += ",";
			}
		}
		return string;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		
		if (obj instanceof ParameterizedTypeModel) {
			this.getSimpleType().getTypeName().equals(((ParameterizedTypeModel) obj).getSimpleType().getTypeName());
			List<TypeModel> thisList = this.getTypeArguments();
			List<TypeModel> otherList = ((ParameterizedTypeModel) obj).getTypeArguments();
			if (thisList.size() != otherList.size()) {
				return false;
			}else {
				for (int i = 0; i < thisList.size(); i++) {
					if(thisList.get(i).equals(otherList.get(i))){
						return true;
					}
				}
			}
		}
		
		return false;
	}	
	
}

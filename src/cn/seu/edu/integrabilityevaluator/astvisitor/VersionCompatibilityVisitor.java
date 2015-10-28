package cn.seu.edu.integrabilityevaluator.astvisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WildcardType;


import cn.seu.edu.integrabilityevaluator.model.AbstractClassModel;
import cn.seu.edu.integrabilityevaluator.model.ArrayTypeModel;
import cn.seu.edu.integrabilityevaluator.model.ConstructorMethodModel;
import cn.seu.edu.integrabilityevaluator.model.EnumModel;
import cn.seu.edu.integrabilityevaluator.model.FieldModel;
import cn.seu.edu.integrabilityevaluator.model.JModifier;
import cn.seu.edu.integrabilityevaluator.model.MethodModel;
import cn.seu.edu.integrabilityevaluator.model.ParameterizedTypeModel;
import cn.seu.edu.integrabilityevaluator.model.PrimitiveTypeModel;
import cn.seu.edu.integrabilityevaluator.model.QualifiedTypeModel;
import cn.seu.edu.integrabilityevaluator.model.SimpleTypeModel;
import cn.seu.edu.integrabilityevaluator.model.SingleVariableModel;
import cn.seu.edu.integrabilityevaluator.model.ClassModel;
import cn.seu.edu.integrabilityevaluator.model.TypeModel;
import cn.seu.edu.integrabilityevaluator.model.WildCardTypeModel;

public class VersionCompatibilityVisitor extends ASTVisitor {

	private ClassModel classModel;
	private EnumModel enumModel;
	private String packageName = null;
	private HashSet<String> importPackageStrings;
	
	public VersionCompatibilityVisitor(){
		classModel = new ClassModel();
		enumModel = new EnumModel();
		importPackageStrings = new HashSet<String>();
	}	
	
	public boolean visit(PackageDeclaration node){
		packageName =node.getName().toString();
		return true;	
	}

	public boolean visit(EnumDeclaration node){
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		
		enumModel = getEnumModel(node);
		enumModel.setPackage(packageName);
		return true;		
	}
	///////////////////////////////////////////////////////////////////
	public boolean visit(SimpleType node){

		ITypeBinding binding = node.resolveBinding();

		if (binding == null) {
			System.out.println("simpleType binding is null in class:"  );
			return true;
		}
		if (binding.getPackage()==null) {
			return true;
		}

		String importpackageName = binding.getPackage().getName();
		if (!importpackageName.equals(packageName)) {
			importPackageStrings.add(importpackageName);
		}
				
		return true;
	}
	

	public boolean visit(MethodInvocation  node){
		IMethodBinding binding = node.resolveMethodBinding();
		if (binding == null) {
//				System.out.println("MethodInvocation binding is null in class:" +classString );				
				return true;
		}
	
		String importpackageName = binding.getDeclaringClass().getPackage().getName();
		if (!importpackageName.equals(packageName)) {
			importPackageStrings.add(importpackageName);
		}
				
		return true;
	}

	
	
	public void endVisit(CompilationUnit node){
		
	
		System.out.println("----------------------------------------------------------");
		System.out.println("package "+ packageName );
		
		if (!classModel.isEmpty()) {
			classModel.setImportPackages(importPackageStrings);			
		}

		if (!enumModel.isEmpty()) {
			enumModel.setImportPackages(importPackageStrings);			
		}	
		System.out.println("----------------------------------------------------------");
		
//		importPackageStrings.clear();
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	public boolean visit(TypeDeclaration node){
	
		//内部类或匿名类
		if (node.isMemberTypeDeclaration()) {
			return true;
		}
		
		if (node.getSuperclassType() != null) {
			if (node.getSuperclassType() instanceof SimpleType) {
				((SimpleType) node.getSuperclassType()).getName();
			}
		}		
		
		classModel = getClassType(node);
		classModel.setPackage(packageName);
		
		return true;
	}
	
	private ClassModel getClassType(TypeDeclaration node){
		ClassModel classModel = new ClassModel();
		classModel.setEmpty(false);
		String string=node.getName().getIdentifier();
		classModel.setClassName(string);
		
		System.out.println("Type:\t"+node.getName().getIdentifier());
		if (node.getSuperclassType() != null) {
			classModel.setSuperClass(node.getSuperclassType().toString());
		}
		
		List<TypeParameter> typeParameters = node.typeParameters();
		for (TypeParameter typeParameter : typeParameters) {
			System.out.println("typeParameter:"+typeParameter.getName());
			classModel.addTypeParameter(typeParameter.getName().toString());
		}
		
		classModel.setINTERFACE(node.isInterface());
		
		//class继承的接口类
		List<Type> list = node.superInterfaceTypes();
		for (Type interfaceType : list) {
			classModel.setSuperInterfaceType(interfaceType.toString());
		}

		//class的属性
		classModel.setModifier(getJModifier(node));	 
		
		//class的父类
		if (node.getSuperclassType() != null) {
			classModel.setSuperClass(node.getSuperclassType().toString());
		}	
	
		//处理field //声明部分记录，未记录后面的初始化	
		classModel.setFieldModels(getFieldModels(node));	
		
		//处理method //记录函数签名
		classModel.setMethodModels(getMethodModels(node));
		classModel.setConstructorMethodModels(getConstructorMethodModels(node));
			
		//处理Body部分类型
		List bd = node.bodyDeclarations();
		for (Object object : bd) {
			if (object instanceof EnumDeclaration) {
//				System.out.println("EnumDeclaration:\t"+((EnumDeclaration) object).getName());
				classModel.addInnerClassModel(getEnumModel((EnumDeclaration) object));
			} else if(object instanceof TypeDeclaration){
				classModel.addInnerClassModel(getClassType((TypeDeclaration)object));
			} else if (object instanceof MethodDeclaration) {
				// already handle

			} else if (object instanceof FieldDeclaration) {
//				System.out.println("FieldDeclarationType:\t"+((FieldDeclaration) object).getType());
				
			} else if(object instanceof Initializer) {
				System.out.println("Initializer");
			} 
		}		
		
		return classModel;
	
	}	
	
	private EnumModel getEnumModel(EnumDeclaration node){
		
		EnumModel enumModel = new EnumModel();
		enumModel.setEmpty(false);
		String string = node.getName().getIdentifier();
		enumModel.setClassName(string);
		System.out.println("EnumType:\t"+node.getName().getIdentifier());
		
		//class继承的接口类
		List<Type> list = node.superInterfaceTypes();
		for (Type interfaceType : list) {
			enumModel.setSuperInterfaceType(interfaceType.toString());
		}

		//class的属性
		enumModel.setModifier(getJModifier(node));	
		
		//处理field //声明部分记录，未记录后面的初始化	
		enumModel.setFieldModels(getFieldModels(node));	
		
		//处理method 
		enumModel.setMethodModels(getMethodModels(node));
		enumModel.setConstructorMethodModels(getConstructorMethodModels(node));
		
		
		List<EnumConstantDeclaration> list2= ((EnumDeclaration) node).enumConstants();
		for (EnumConstantDeclaration enumConstantDeclaration : list2) {
//			System.out.println(enumConstantDeclaration.getName());
			enumModel.addEnumConstant(enumConstantDeclaration.getName().toString());
		}
		
		
		
		//处理枚举类型
		List bd = node.bodyDeclarations();
		for (Object object : bd) {
			if (object instanceof EnumDeclaration) {
//				System.out.println("EnumDeclaration:\t"+((EnumDeclaration) object).getName());
				enumModel.addInnerClassModel(getEnumModel((EnumDeclaration) object));
			} else if(object instanceof TypeDeclaration){
				enumModel.addInnerClassModel(getClassType((TypeDeclaration)object));
			} else if (object instanceof MethodDeclaration) {

			} else if (object instanceof FieldDeclaration) {
//				System.out.println("FieldDeclarationType:\t"+((FieldDeclaration) object).getType());
			} else if(object instanceof Initializer) {
				System.out.println("Initializer");
			} 
		}		
		
		return enumModel;
	}
	
	
	private JModifier getJModifier(ASTNode node){
		List<IExtendedModifier> ieModifiers = new LinkedList<IExtendedModifier>();
		if (node instanceof TypeDeclaration) {
			ieModifiers = ((TypeDeclaration) node).modifiers();	

		}else if (node instanceof EnumDeclaration) {
			 ieModifiers = ((EnumDeclaration) node).modifiers();

		}else if (node instanceof MethodDeclaration) {
			ieModifiers = ((MethodDeclaration) node).modifiers();

		}else if (node instanceof FieldDeclaration) {
			ieModifiers = ((FieldDeclaration) node).modifiers();
			
		}else if (node instanceof SingleVariableDeclaration) {
			ieModifiers = ((SingleVariableDeclaration) node).modifiers();
		}else {
			System.out.println("!!!!not include, check!!!");
		}
		
		JModifier jm = new JModifier();
		for (IExtendedModifier modifier : ieModifiers) {
			if (modifier.isModifier()) {
				if (((Modifier) modifier).isAbstract()) {
					jm.setABSTRACT(true);
				}
				
				if (((Modifier) modifier).isFinal()) {
					jm.setFINAL(true);
				}
				
				if (((Modifier) modifier).isNative()) {
					jm.setNATIVE(true);
				}
				
				if (((Modifier) modifier).isPublic()) {
					jm.setPUBLIC(true);
				}
				
				if (((Modifier) modifier).isPrivate()) {
					jm.setPRIVATE(true);
				}
				
				if (((Modifier) modifier).isProtected()) {
					jm.setPROTECTED(true);
				}
				
				if (((Modifier) modifier).isStatic()) {
					jm.setSTATIC(true);
				}
				
				if (((Modifier) modifier).isStrictfp()) {
					jm.setSTRICTFP(true);
				}
				
				if (((Modifier) modifier).isSynchronized()) {
					jm.setSYNCHRONIZED(true);
				}
				
				if (((Modifier) modifier).isTransient()) {
					jm.setTRANSIENT(true);
				}
				
				if (((Modifier) modifier).isVolatile()) {
					jm.setVOLATILE(true);
				}				
			}			
		}		
		return jm;		
	}	

	private List<FieldModel> getFieldModels(ASTNode node){
		
		List<FieldModel> list = new LinkedList<>(); 	
		if (node instanceof TypeDeclaration) {
			FieldDeclaration[] fields = ((TypeDeclaration) node).getFields();
			for (FieldDeclaration fieldDeclaration : fields) {
				
				FieldModel fieldModel = new FieldModel();
				fieldModel.setModifier(getJModifier(fieldDeclaration));
				fieldModel.setType(fieldDeclaration.getType().toString());
				List<VariableDeclarationFragment> variableDeclarationFragments = fieldDeclaration.fragments();
				for (VariableDeclarationFragment vdf :variableDeclarationFragments) {
					fieldModel.setFieldName(vdf.getName().toString());
					list.add(fieldModel);
				}
			}		

		}else if (node instanceof EnumDeclaration) {			  
			  List<BodyDeclaration> list2= ((EnumDeclaration) node).bodyDeclarations();
			  for (BodyDeclaration bodyDeclaration : list2) {
				if (bodyDeclaration instanceof FieldDeclaration) {
					FieldModel fieldModel = new FieldModel();
					fieldModel.setModifier(getJModifier((FieldDeclaration)bodyDeclaration));
					fieldModel.setType(((FieldDeclaration)bodyDeclaration).getType().toString());
					List<VariableDeclarationFragment> variableDeclarationFragments = ((FieldDeclaration)bodyDeclaration).fragments();
					for (VariableDeclarationFragment vdf :variableDeclarationFragments) {
						fieldModel.setFieldName(vdf.getName().toString());
						list.add(fieldModel);
					}
				} 
			}	
		}		
		return list;
	}	
	

	public List<MethodModel> getMethodModels(ASTNode node) {

		List<MethodModel> list = new LinkedList<>();
		if (node instanceof TypeDeclaration) {
			MethodDeclaration[] methods = ((TypeDeclaration) node).getMethods();
			for (MethodDeclaration methodDeclaration : methods) {
				MethodModel methodModel = new MethodModel();
				methodModel.setMethodName(methodDeclaration.getName()
						.getIdentifier());
				methodModel.setModifier(getJModifier(methodDeclaration));
				if (methodDeclaration.isConstructor()) {
					continue;
				}
				TypeModel typeModel = null;
				Type type = methodDeclaration.getReturnType2();
				if (type instanceof PrimitiveType) {
					//System.out.println(type.getClass().getName() + " "
					//		+ type.toString());
					typeModel = new PrimitiveTypeModel(type.toString());
				} else if (type instanceof ArrayType) {
					/*System.out.println(type.getClass().getName() + " "
							+ ((ArrayType) type).getComponentType().toString()
							+ " " + ((ArrayType) type).getDimensions() + " "
							+ ((ArrayType) type).getElementType().toString());*/
					typeModel = new ArrayTypeModel(((ArrayType) type)
							.getComponentType().toString(),
							((ArrayType) type).getDimensions(),
							((ArrayType) type).getElementType().toString());
				} else if (type instanceof SimpleType) {
					/*System.out.println(type.getClass().getName() + " "
							+ ((SimpleType) type).getName());*/
					Name name = ((SimpleType) type).getName();
					if (name instanceof QualifiedName) {
						((QualifiedName) name).getQualifier();
						typeModel = new SimpleTypeModel(((QualifiedName) name)
								.getName().toString());
					} else {
						typeModel = new SimpleTypeModel(((SimpleType) type)
								.getName().toString());
					}

					if (type.resolveBinding() != null) {
						if (type.resolveBinding().getSuperclass() != null) {
							/*System.out
									.println(((SimpleType) type)
											.resolveBinding().getSuperclass()
											.getName());*/
							((SimpleTypeModel) typeModel)
									.setSuperClass(((SimpleType) type)
											.resolveBinding().getSuperclass()
											.getName());
						}
					}
				} else if (type instanceof QualifiedType) {
					//System.out.println(type.getClass().getName());
					QualifiedTypeModel qualifiedTypeModel = new QualifiedTypeModel();
					qualifiedTypeModel.setTypeName(((QualifiedType) type)
							.getName().toString());
					qualifiedTypeModel.setQualifiedName(((QualifiedType) type)
							.getQualifier().toString());
					//System.out.println(qualifiedTypeModel.getFullName());

				} else if (type instanceof WildcardType) {
					//System.out.println(type.getClass().getName());
					typeModel = new WildCardTypeModel(
							((WildcardType) type).isUpperBound(),
							((WildcardType) type).getBound());
				} else if (type instanceof ParameterizedType) {
					/*System.out.println(type.getClass().getName() + " "
							+ ((ParameterizedType) type).getType().toString()
							+ " ");*/
					typeModel = new ParameterizedTypeModel(
							((ParameterizedType) type).getType().toString());
					List<Type> types = ((ParameterizedType) type).typeArguments();
					((ParameterizedTypeModel) typeModel)
							.setTypeArguments(types);
				} else if (type instanceof UnionType) {
					//System.out.println("Union");
				}

				methodModel.setReturnType(typeModel);

				List<SingleVariableDeclaration> singleVariableDeclarations = methodDeclaration.parameters();
				for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
					SingleVariableModel svm = new SingleVariableModel();
					svm.setModifier(getJModifier(singleVariableDeclaration));
					svm.setType(singleVariableDeclaration.getType());
					svm.setVarargs(singleVariableDeclaration.isVarargs());
					svm.setExtraDimensions(singleVariableDeclaration
							.getExtraDimensions());
					svm.setName(singleVariableDeclaration.getName().toString());
					methodModel.addFormalParameters(svm);
				}
				methodModel.setExtraDimensions(methodDeclaration
						.getExtraDimensions());
				List<Name> throwList = methodDeclaration.thrownExceptions();
				for (Name name : throwList) {
					methodModel.addThrownList(name.getFullyQualifiedName());
				}
				list.add(methodModel);
			}

		} else if (node instanceof EnumDeclaration) {
			List<BodyDeclaration> list2 = ((EnumDeclaration) node).bodyDeclarations();
			for (BodyDeclaration bodyDeclaration : list2) {
				if (bodyDeclaration instanceof MethodDeclaration) {
					MethodModel methodModel = new MethodModel();

					methodModel.setMethodName(((MethodDeclaration) bodyDeclaration).getName().toString());
					methodModel.setModifier(getJModifier((MethodDeclaration) bodyDeclaration));
					if (((MethodDeclaration) bodyDeclaration).isConstructor()) {
						continue;
					}
					TypeModel typeModel = null;
					Type type = ((MethodDeclaration) bodyDeclaration).getReturnType2();
					if (type instanceof PrimitiveType) {
						/*System.out.println(type.getClass().getName() + " "
								+ type.toString());*/
						typeModel = new PrimitiveTypeModel(type.toString());
					} else if (type instanceof ArrayType) {
						/*System.out.println(type.getClass().getName()
								+ " "
								+ ((ArrayType) type).getComponentType().toString()
								+ " "
								+ ((ArrayType) type).getDimensions()
								+ " "
								+ ((ArrayType) type).getElementType().toString());*/
						typeModel = new ArrayTypeModel(((ArrayType) type).getComponentType().toString(),
								((ArrayType) type).getDimensions(),
								((ArrayType) type).getElementType().toString());
					} else if (type instanceof SimpleType) {
						System.out.println(type.getClass().getName() + " "
								+ ((SimpleType) type).getName());
						Name name = ((SimpleType) type).getName();
						if (name instanceof QualifiedName) {
							((QualifiedName) name).getQualifier();
							typeModel = new SimpleTypeModel(((QualifiedName) name).getName().toString());
						} else {
							typeModel = new SimpleTypeModel(((SimpleType) type).getName().toString());
						}

						if (type.resolveBinding() != null) {
							if (type.resolveBinding().getSuperclass() != null) {
								System.out.println(((SimpleType) type).resolveBinding().getSuperclass().getName());
								((SimpleTypeModel) typeModel).setSuperClass(((SimpleType) type).resolveBinding().getSuperclass().getName());
							}
						}
					} else if (type instanceof QualifiedType) {
						//System.out.println(type.getClass().getName());
						QualifiedTypeModel qualifiedTypeModel = new QualifiedTypeModel();
						qualifiedTypeModel.setTypeName(((QualifiedType) type)
								.getName().toString());
						qualifiedTypeModel
								.setQualifiedName(((QualifiedType) type)
										.getQualifier().toString());
						//System.out.println(qualifiedTypeModel.getFullName());

					} else if (type instanceof WildcardType) {
						//System.out.println(type.getClass().getName());
						typeModel = new WildCardTypeModel(
								((WildcardType) type).isUpperBound(),
								((WildcardType) type).getBound());
					} else if (type instanceof ParameterizedType) {
						/*System.out.println(type.getClass().getName()
								+ " "
								+ ((ParameterizedType) type).getType()
										.toString() + " ");*/
						typeModel = new ParameterizedTypeModel(
								((ParameterizedType) type).getType().toString());
						List<Type> types = ((ParameterizedType) type).typeArguments();
						((ParameterizedTypeModel) typeModel)
								.setTypeArguments(types);
					} else if (type instanceof UnionType) {
						System.out.println("Union");
					}

					methodModel.setReturnType(typeModel);

					List<SingleVariableDeclaration> singleVariableDeclarations = ((MethodDeclaration) bodyDeclaration).parameters();
					for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
						SingleVariableModel svm = new SingleVariableModel();
						svm.setModifier(getJModifier(singleVariableDeclaration));
						svm.setType(singleVariableDeclaration.getType());
						svm.setVarargs(singleVariableDeclaration.isVarargs());
						svm.setExtraDimensions(singleVariableDeclaration
								.getExtraDimensions());
						svm.setName(singleVariableDeclaration.getName()
								.toString());
						methodModel.addFormalParameters(svm);
					}
					methodModel.setExtraDimensions(((MethodDeclaration) bodyDeclaration).getExtraDimensions());
					List<Name> throwList = ((MethodDeclaration) bodyDeclaration).thrownExceptions();
					for (Name name : throwList) {
						methodModel.addThrownList(name.getFullyQualifiedName());
					}
					list.add(methodModel);
				}

			}
		}
		return list;
	}
	
public List<ConstructorMethodModel> getConstructorMethodModels(ASTNode node){
		
		List<ConstructorMethodModel> list = new LinkedList<>(); 	
		if (node instanceof TypeDeclaration) {
			MethodDeclaration[] methods = ((TypeDeclaration) node).getMethods();
			for (MethodDeclaration methodDeclaration : methods) {
				ConstructorMethodModel methodModel = new ConstructorMethodModel();
				methodModel.setMethodName(methodDeclaration.getName().getIdentifier());
				methodModel.setModifier(getJModifier(methodDeclaration));
				if (!methodDeclaration.isConstructor()){
					continue;					
				}
				
				List<TypeParameter> typeParameters = methodDeclaration.typeParameters();
				for (TypeParameter typeParameter : typeParameters) {
					System.out.println("typeParameter:"+typeParameter.getName());
					methodModel.addTypeParameter(typeParameter.getName().toString());					
				}
				
				List<SingleVariableDeclaration> singleVariableDeclarations = methodDeclaration.parameters();
				for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
					SingleVariableModel svm = new SingleVariableModel();
					svm.setModifier(getJModifier(singleVariableDeclaration));
					svm.setType(singleVariableDeclaration.getType());
					svm.setVarargs(singleVariableDeclaration.isVarargs());
					svm.setExtraDimensions(singleVariableDeclaration.getExtraDimensions());
					svm.setName(singleVariableDeclaration.getName().toString());
					methodModel.addFormalParameters(svm);
				}
				methodModel.setExtraDimensions(methodDeclaration.getExtraDimensions());
				List<Name> throwList = methodDeclaration.thrownExceptions();
				for (Name name : throwList) {
					methodModel.addThrownList(name.getFullyQualifiedName());
				}
				list.add(methodModel);
			}		

		}else if (node instanceof EnumDeclaration) {			  
			  List<BodyDeclaration> list2= ((EnumDeclaration) node).bodyDeclarations();
			  for (BodyDeclaration bodyDeclaration : list2) {
				if (bodyDeclaration instanceof MethodDeclaration) {
					ConstructorMethodModel methodModel = new ConstructorMethodModel();

					
					methodModel.setMethodName(((MethodDeclaration) bodyDeclaration).getName().toString());
					methodModel.setModifier(getJModifier((MethodDeclaration)bodyDeclaration));
					if (!((MethodDeclaration)bodyDeclaration).isConstructor()){
						continue;
					}
					
					List<TypeParameter> typeParameters = ((MethodDeclaration)bodyDeclaration).typeParameters();
					for (TypeParameter typeParameter : typeParameters) {
						System.out.println("typeParameter:"+typeParameter.getName());
						methodModel.addTypeParameter(typeParameter.getName().toString());					
					}
					
					List<SingleVariableDeclaration> singleVariableDeclarations = ((MethodDeclaration)bodyDeclaration).parameters();
					for (SingleVariableDeclaration singleVariableDeclaration : singleVariableDeclarations) {
						SingleVariableModel svm = new SingleVariableModel();
						svm.setModifier(getJModifier(singleVariableDeclaration));
						svm.setType(singleVariableDeclaration.getType());
						svm.setVarargs(singleVariableDeclaration.isVarargs());
						svm.setExtraDimensions(singleVariableDeclaration.getExtraDimensions());
						svm.setName(singleVariableDeclaration.getName().toString());
						methodModel.addFormalParameters(svm);
					}
					methodModel.setExtraDimensions(((MethodDeclaration)bodyDeclaration).getExtraDimensions());
					List<Name> throwList = ((MethodDeclaration)bodyDeclaration).thrownExceptions();
					for (Name name : throwList) {
						methodModel.addThrownList(name.getFullyQualifiedName());
					}
					list.add(methodModel);				
				} 
			
			}	
		}		
		return list;
	}
	
	public AbstractClassModel getTypeModel() {
		AbstractClassModel atm = null ;
		if (!classModel.isEmpty()) {
			System.out.println("get typemodel");
			atm = classModel;
		}

		if (!enumModel.isEmpty()) {
			System.out.println("get enumModel");
			atm = enumModel;
		}	
		
		return atm;		
	}	
	
}

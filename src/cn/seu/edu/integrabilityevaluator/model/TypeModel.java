package cn.seu.edu.integrabilityevaluator.model;

/*PrimitiveType:
    byte
    short
    char
    int
    long
    float
    double
    boolean
    void
 ArrayType:
    Type [ ]
 SimpleType:
    TypeName
 ParameterizedType:
    Type < Type { , Type } >
 QualifiedType:
    Type . SimpleName
 WildcardType:
    ? [ ( extends | super) Type ]
 */
public abstract class TypeModel {

	private String typeName;
	
	public TypeModel(){
		typeName = new String();
	}
	
	public TypeModel(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public abstract boolean CanCompatibility(TypeModel typeModel);
	
	public abstract String getFullName();
	
	public abstract boolean equals(Object obj);
	
}

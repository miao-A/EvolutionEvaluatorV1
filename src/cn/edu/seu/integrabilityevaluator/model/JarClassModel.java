package cn.edu.seu.integrabilityevaluator.model;

import java.util.ArrayList;
import java.util.List;

public class JarClassModel {
	private String packagename;
	private String classname;
	private List<JarMethodModel> methodList = new ArrayList<>();
	
	private String fromClass;
	
	public JarClassModel(String pName, String ppName) {
		// TODO Auto-generated constructor stub
		packagename = pName;
		classname = ppName;
	}
	
	public String getClassName(){
		return classname;
	}
	
	public String getPackageName(){
		return packagename;
	}
	
	public void addmethod(JarMethodModel jarMethodModel){
		methodList.add(jarMethodModel);
	}
	
	public List<JarMethodModel> getmethod(){
		return methodList;
	}
	
	public boolean equals(Object o){
		if (o == this) {
			return true;
		}
		
		if (o instanceof JarClassModel) {
			return this.getClassName().equals(((JarClassModel) o).getClassName())&&this.getPackageName().equals(((JarClassModel) o).getPackageName());
		}
		
		return false;
	}

	public String getFromClass() {
		return fromClass;
	}

	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}
	
	
}

package cn.edu.seu.integrabilityevaluator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ThisExpression;

public class JarMethodModel {
	String methodName;
	List<String> parameterlist = new ArrayList<>();
	
	public JarMethodModel(String sm) {
		// TODO Auto-generated constructor stub
		methodName = sm;
	}

	public String getMethodName() {
		/*String str = methodName + "(";
		for (String string : parameterlist) {
			str += string;
			if (parameterlist.lastIndexOf(string) != -1) {
				str +=",";
			}
		}
		str +=")";*/
		return methodName;
	}
	
/*	public boolean equals(Object o) {
		if(o==this){
			return true;
		}
		
		if (o instanceof JarMethodModel) {
			if (this.getMethodName().equals(((JarMethodModel) o).getMethodName())) {				
			
				if (this.getParameters().size() == ((JarMethodModel) o).getParameters().size()) {
					if (this.getParameters().size()==0) {
						return true;
					}
					for (int i = 0; i < this.getParameters().size(); i++) {
						if(((JarMethodModel) o).getParameters().get(i).equals("null")){
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;
						}else if (this.getParameters().get(i).equals(((JarMethodModel) o).getParameters().get(i))) {
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;						
						}else if (canCampatibility(this.getParameters().get(i), ((JarMethodModel) o).getParameters().get(i))) {
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;
						}else {
							return false;
						}				
					}
				}else {
					return false;
				}
			
			}		
		}
		return false;
		
	}*/
	
	@Override
	public boolean equals(Object o) {
		if(o==this){
			return true;
		}
		
		if (o instanceof JarMethodModel) {
			if (this.getMethodName().equals(((JarMethodModel) o).getMethodName())) {				
			
				if (this.getParameters().size() == ((JarMethodModel) o).getParameters().size()) {
					if (this.getParameters().size()==0) {
						return true;
					}
					for (int i = 0; i < this.getParameters().size(); i++) {
						if(this.getParameters().get(i).equals("null")){
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;
						}else if (this.getParameters().get(i).equals(((JarMethodModel) o).getParameters().get(i))) {
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;						
						}else if (canCampatibility(((JarMethodModel) o).getParameters().get(i), this.getParameters().get(i))) {
							if (i == this.getParameters().size()-1) {
								return true;
							}
							continue;
						}else {
							return false;
						}				
					}
				}else {
					return false;
				}
			
			}		
		}
		return false;
		
	}
	
	public boolean canCampatibility(String str1,String str2){
		
		if (str1.equals("java.lang.Comparable")) {
			if (str2.equals("java.lang.String")) {
				return true;
			}
		}
		
		if (str1.equals("java.io.OutputStream")) {
			if (str2.equals("java.io.FileOutputStream")) {
				return true;
			}
		}
		
		if (str1.equals("double")) {
			if (str2.equals("int")) {
				return true;
			}
		}
		return false;
	}
	
	public List<String> getParameters(){
		return this.parameterlist;
	}
	
	public void setParameters(List<String> parameterList){
		this.parameterlist.addAll(parameterList);
	}
}

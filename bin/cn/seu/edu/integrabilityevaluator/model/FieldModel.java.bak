package cn.seu.edu.integrabilityevaluator.model;

import java.util.LinkedList;
import java.util.List;

public class FieldModel {
	
	
	private String fieldName;
	private String type;
	private JModifier modifier = new JModifier();
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public JModifier getModifier() {
		return modifier;
	}
	public void setModifier(JModifier modifier) {
		this.modifier = modifier;
	}
	
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj instanceof FieldModel) {
			return this.getFieldName().equals(((FieldModel) obj).getFieldName())&&this.getType().equals(((FieldModel) obj).getType());
		}
		return false;
	}
}

package cn.seu.edu.integrabilityevaluator.model;

import java.util.HashMap;

public class TypeJudgeCompatibility {

	static HashMap<String, String> relationmMap = new HashMap<>();
	
	
	public TypeJudgeCompatibility(TypeModel oldtype, TypeModel newtype){
		initRelationMap();
	}
	
	private void initRelationMap(){
		relationmMap.put("HashMap","Map");
		relationmMap.put("TreeMap","SortedMap");
		relationmMap.put("SortedMap","Map");
		relationmMap.put("TreeSet","SortedSet");
		relationmMap.put("SortedSet","Set");
		relationmMap.put("LinkedHashSet","HashSet");
		relationmMap.put("HashSet","Set");
		relationmMap.put("LinkedList","List");
		relationmMap.put("ArrayList","List");
		relationmMap.put("List","Collection");
		relationmMap.put("Set","Collection");
	}
	
	/*public void addRelationMap(TypeModel oldtype, TypeModel newtype){
		relationmMap.put(oldtype.getTypeName(), newtype.get)
	}*/
	
	public static boolean isCompatibility(TypeModel oldtype, TypeModel newtype){
		if (newtype.getTypeName().equals("Object")) {
			return true;
		}
		
		if (oldtype.getTypeName().equals(newtype.getTypeName())) {
			return true;
		}
		
		String key = oldtype.getFullName();
		while (relationmMap.containsKey(key)) {
			if (relationmMap.get(key).equals(newtype.getTypeName())) {
				return true;
			}			
			key = relationmMap.get(key);			
		}
		
		return false;		
	}
	
}

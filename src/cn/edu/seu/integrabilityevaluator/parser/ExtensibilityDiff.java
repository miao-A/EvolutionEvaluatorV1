package cn.edu.seu.integrabilityevaluator.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.seu.integrabilityevaluator.dbconnect.ExtensibilityConnector;

public class ExtensibilityDiff {

	private String project = null;
	private HashMap<String, HashMap<String, List<String>>> diffMap = null;
	
	
	public ExtensibilityDiff(String project){
		this.project = project;
	}
	
	public HashMap<String, HashMap<String, List<String>>> diffInProject(String preVersion,String postVersion){
		
		ExtensibilityConnector preProject = new ExtensibilityConnector(project, preVersion);
		ExtensibilityConnector postProject = new ExtensibilityConnector(project, postVersion);
				
		HashMap<String,ArrayList<String>> preHashMap = new HashMap<>();
		HashMap<String,ArrayList<String>> postHashMap = new HashMap<>();
		
		for (String packageName : preProject.getpackageName()) {			
	//		System.out.println(packageName);
			ArrayList<String> typelist = preProject.packageExtensibilityInfo(packageName);
			preHashMap.put(packageName, typelist);		
		}
				
		for (String packageName : postProject.getpackageName()) {
	//		System.out.println(packageName);
			ArrayList<String> typelist = postProject.packageExtensibilityInfo(packageName);
			postHashMap.put(packageName, typelist);
		}
		
		Iterator<String> preIterator = preHashMap.keySet().iterator();
		Iterator<String> postIterator = postHashMap.keySet().iterator();
		HashMap<String, HashMap<String, List<String>>> diffMap = new HashMap<>();
		while (preIterator.hasNext()) {		
			String packageName = preIterator.next();
			
			if (postHashMap.containsKey(packageName)) {
				ArrayList<String> prelist = preHashMap.get(packageName);
				ArrayList<String> postlist = postHashMap.get(packageName);
				if (prelist.containsAll(postlist)&&postlist.containsAll(prelist)) {
					preIterator.remove();
					postHashMap.remove(packageName);	
				}else {
					System.out.println(packageName);
					System.out.println("something change");
					HashMap<String, List<String>> map = diffInPackage(prelist,postlist);
					diffMap.put(packageName, map);
				}		
					
			}		
		}
		
		return diffMap;
	}
		
	

	
	private HashMap<String, List<String>> diffInPackage(ArrayList<String> prelist,ArrayList<String> postlist) {

		HashMap<String, List<String>> map = new HashMap<>();		
		for (String classname : prelist) {
			if (!postlist.contains(classname)) {
				String[] string = classname.split("&");
				String keystring = "-"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
				
			}else {
				String[] string = classname.split("&");
				String keystring = string[string.length-1];
				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
			}
		}
		
		for (String classname : postlist) {
			if (!prelist.contains(classname)) {
				String[] string = classname.split("&");
				String keystring = "+"+string[string.length-1];
				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);					
				}else {
					List<String> classList = new LinkedList<>();
					classList.add(string[0]);
					map.put(keystring, classList);
				}
			}
		}		
		
		return map;
	}

	public HashMap<String, HashMap<String, List<String>>> getDiffMap() {
		return diffMap;
	}

	public void setDiffMap(HashMap<String, HashMap<String, List<String>>> diffMap) {
		this.diffMap = diffMap;
	}
}

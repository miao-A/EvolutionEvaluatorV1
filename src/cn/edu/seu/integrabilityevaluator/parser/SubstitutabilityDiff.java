package cn.edu.seu.integrabilityevaluator.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.edu.seu.integrabilityevaluator.dbconnect.SubstitutabilityConnector;


public class SubstitutabilityDiff {
	private String project = null;
	private HashMap<String, HashMap<String, List<String>>> diffMap = null;
	
	
	public SubstitutabilityDiff(String project){
		this.project = project;
	}
	
	public HashMap<String, HashMap<String, List<String>>> diffInProject(String preVersion,String postVersion){		
		
/*		ClassChangeabilityConnector preProject = new ClassChangeabilityConnector(project, preVersion);
		ClassChangeabilityConnector postProject = new ClassChangeabilityConnector(project, postVersion);*/
			
		SubstitutabilityConnector preProject = new SubstitutabilityConnector(project, preVersion);
		SubstitutabilityConnector postProject = new SubstitutabilityConnector(project, postVersion);
		
		HashMap<String,ArrayList<String>> preHashMap = new HashMap<>();
		HashMap<String,ArrayList<String>> postHashMap = new HashMap<>();
		
		for (String packageName : preProject.getpackageName()) {			
			ArrayList<String> exportlist = preProject.packageAffernetCouplingslist(packageName);
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			ArrayList<String> importlist = preProject.packageEffernetCouplingslist(packageName);		
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			preHashMap.put(packageName, exportlist);		
		}
				
		for (String packageName : postProject.getpackageName()) {
//			System.out.println("packageName:"+packageName);
			ArrayList<String> exportlist = postProject.packageAffernetCouplingslist(packageName);

//			ArrayList<String> exportlist = postProject.class_packageAffernetCouplingslist(packageName);
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			
			ArrayList<String> importlist = postProject.packageEffernetCouplingslist(packageName);
//			ArrayList<String> importlist = postProject.class_packageEffernetCouplingslist(packageName);
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			postHashMap.put(packageName, exportlist);
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
	
	public HashMap<String, HashMap<String, List<String>>> moreDiffInProject(String preVersion,String postVersion){
		
		
		/*ClassChangeabilityConnector preProject = new ClassChangeabilityConnector(project, preVersion);
		ClassChangeabilityConnector postProject = new ClassChangeabilityConnector(project, postVersion);*/
		
		SubstitutabilityConnector preProject = new SubstitutabilityConnector(project, preVersion);
		SubstitutabilityConnector postProject = new SubstitutabilityConnector(project, postVersion);
		
		HashMap<String,ArrayList<String>> preHashMap = new HashMap<>();
		HashMap<String,ArrayList<String>> postHashMap = new HashMap<>();
		
		for (String packageName : preProject.getpackageName()) {			
//			System.out.println("packageName:"+packageName);
			ArrayList<String> exportlist = preProject.class_packageAffernetCouplingslist(packageName);
//			System.out.println("ca:"+importlist.size());
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			ArrayList<String> importlist = preProject.class_packageEffernetCouplingslist(packageName);
//			System.out.println("ce:"+exportlist.size());
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			preHashMap.put(packageName, exportlist);		
		}
				
		for (String packageName : postProject.getpackageName()) {
//			System.out.println("packageName:"+packageName);
			ArrayList<String> exportlist = postProject.class_packageAffernetCouplingslist(packageName);
//			System.out.println("ca:"+importlist.size());
			for (String string : exportlist) {
				exportlist.set(exportlist.indexOf(string), string+"&export");
			}			
			
			ArrayList<String> importlist = postProject.class_packageEffernetCouplingslist(packageName);
//			System.out.println("ce:"+exportlist.size());
			for (String string : importlist) {
				importlist.set(importlist.indexOf(string), string+"&import");
			}
			exportlist.addAll(importlist);
			postHashMap.put(packageName, exportlist);
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
		for (String packagename : prelist) {
			if (!postlist.contains(packagename)) {
				String[] string = packagename.split("&");
				String keystring = "-"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packageList = new LinkedList<>();
					packageList.add(string[0]);
					map.put(keystring, packageList);
				}
				
			}else {
				String[] string = packagename.split("&");
				String keystring = string[string.length-1];
				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packageList = new LinkedList<>();
					packageList.add(string[0]);
					map.put(keystring, packageList);
				}
			}
		}
		
		for (String packagename : postlist) {
			System.out.println(packagename);
			if (!prelist.contains(packagename)) {
//				addClasslList.add(classname);
				String[] string = packagename.split("&");
				String keystring = "+"+string[string.length-1];

				if (map.keySet().contains(keystring)) {
					map.get(keystring).add(string[0]);
					
				}else {
					List<String> packagList = new LinkedList<>();
					packagList.add(string[0]);
					map.put(keystring, packagList);
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

package cn.edu.seu.integrabilityevaluator.chart;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.edu.seu.integrabilityevaluator.dbconnect.ExtensibilityInfoConnector;
import cn.edu.seu.integrabilityevaluator.dbconnect.ProjectConnector;

public class ExtensibilityLineChart extends LineChart  {

	private String projectName = new String();
	
		
	public ExtensibilityLineChart(String title,String projectName){
		super(title);
		this.projectName = projectName;
	}
	
	@Override
	public void creatDataSet() {
		// TODO Auto-generated method stub
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);	

		LinkedHashMap<String, HashMap<String, Double>> dataMap = new LinkedHashMap<String, HashMap<String, Double>>();		
		
		for (String version : versionlist){
			HashMap<String, Double> map = new HashMap<String, Double>();
			ExtensibilityInfoConnector dbConnector = new ExtensibilityInfoConnector(projectName, version);
			List<String> pkgNameList = dbConnector.getpackageName();
			int pkgNum = pkgNameList.size();
			for (String pkgName : pkgNameList) {
				double ratio = dbConnector.getExtensibilityRatio(pkgName);
				map.put(pkgName, new Double(ratio));
			}			
			dataMap.put(projectName+version+" ("+pkgNum+")", map);
		}
		
		this.setDataSet(dataMap);
	}	

}

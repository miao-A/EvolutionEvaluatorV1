package cn.seu.edu.integrabilityevaluator.chart;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectConnector;
import cn.seu.edu.integrabilityevaluator.dbconnect.SubstitutabilityInfoConnector;

public class SubstitutabilityLineChart  extends LineChart {

	private String projectName= new String();
	
	public SubstitutabilityLineChart(){
		super();
		creatDataSet();
	}
	
	public SubstitutabilityLineChart(String title,String projectName){
		super(title);
		this.projectName = projectName;
	}
	@Override
	public void creatDataSet() {
		// TODO Auto-generated method stub
		ProjectConnector pConnector = new ProjectConnector();
		List<String> versionlist = pConnector.getVersion(projectName);
		
		LinkedHashMap<String, HashMap<String, Double>> dataMap = new LinkedHashMap<String, HashMap<String, Double>>();		
		
		for (String version : versionlist) {
			SubstitutabilityInfoConnector dbConnector = new SubstitutabilityInfoConnector(projectName, version);
			HashMap<String, Double> map = new HashMap<String, Double>();
			List<String> pkgNameList = dbConnector.getpackageName();
			int pkgNum = pkgNameList.size();
		
			for (String pkgName : pkgNameList) {
				
				double ratio = dbConnector.getChangeabilityRatio(pkgName);
				map.put(pkgName, new Double(ratio));
			}
			dataMap.put(projectName+version+" ("+pkgNum+")", map);			
		}
		this.setDataSet(dataMap);
	}
	
}

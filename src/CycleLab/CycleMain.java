package CycleLab;

import java.util.ArrayList;

public class CycleMain {
	public static void main(String[] args) {
		SubstitutabilityConnector dbConnector = new SubstitutabilityConnector("junit","3.4");
		final ArrayList<CouplingNode> packageNodeList = new ArrayList<CouplingNode>();
		final ArrayList<CouplingNode> classNodeList = new ArrayList<CouplingNode>();
		ArrayList<String> packageList = dbConnector.getpackageName();
				//dbConnector.getpackageName();
		for (String string : packageList) {
			CouplingNode packageNode = new CouplingNode(string);
			packageNode.setAfferents(dbConnector.packageAffernetCouplingslist(string));
			packageNode.setEfferents(dbConnector.packageEffernetCouplingslist(string));			
			packageNodeList.add(packageNode);		
		}
		
		//System.out.println(packageNodeList.size());
		
		MyGraph G = new MyGraph();
		G.createGraph(packageNodeList);  
		G.checkCircle(); 
		System.exit(0); 
	}
}

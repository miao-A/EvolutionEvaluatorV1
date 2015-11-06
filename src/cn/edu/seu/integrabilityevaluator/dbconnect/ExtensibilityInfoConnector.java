package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExtensibilityInfoConnector extends DBConnector {
	private Connection connect = null;
	private String projectNameString;
	private String versionString;
		
		public ExtensibilityInfoConnector(String projectName, String version){
			super();
			connect = getConnection();
			this.projectNameString = projectName;
			this.versionString = version;
		}
		
		public ArrayList<String> getpackageName(){
			ArrayList<String> list = new ArrayList<String>();
			try {

				Statement stmt = connect.createStatement();
				String sql = "SELECT pkgname FROM " + dBname + ".classTypeinfo where ProjName = '"
						+ projectNameString 
						+ "' and verID = '"
						+ versionString 
						+"' group by pkgName";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					list.add(rs.getString("pkgName"));
//					System.out.println(rs.getString("pkgname"));
					}	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("extensibility Connector error");
			}
			
			return list; 

		}
		
		


		public void setExtensibilityInfo(String packageName){
			
			ArrayList<String> rStrings = new ArrayList<String>();
			try {
				Statement stmt = connect.createStatement();
				//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				
				String str = "Select  count(classname) as result FROM " + dBname + ".classTypeinfo where pkgname = '"
				+ packageName 
				+ "' and VerID = '" 
				+ versionString + "' and projName = '"
				+projectNameString +"'";
				
				String concretestr = str +" and classtype = 'concrete'";
				String abstractstr = str +" and classtype = 'abstract'";
				String interfacestr = str +" and classtype = 'interface'";
				
				rStrings.add(packageName);
				
				ResultSet rs ;
				int concreteNum=0;
				int abstractNum=0;
				int interfaceNum=0;
				int totalNum = 0;
				
				rs= stmt.executeQuery(concretestr);			
				while (rs.next()) {
					concreteNum = rs.getInt("result");
					rStrings.add(concreteNum+"");
					
				}
				
				rs= stmt.executeQuery(abstractstr);
				while (rs.next()) {
					abstractNum = rs.getInt("result");
					rStrings.add(abstractNum+"");			
				}
				
				rs= stmt.executeQuery(interfacestr);
				while (rs.next()) {
					interfaceNum = rs.getInt("result");
					rStrings.add(interfaceNum+"");	
					
				}
				
				totalNum = interfaceNum + abstractNum + concreteNum;
				rStrings.add(totalNum+"");
				
				double ratioOfInterface = 100.0*(interfaceNum)/(interfaceNum+abstractNum+concreteNum);
				DecimalFormat df = new DecimalFormat("#.00");
				rStrings.add(df.format(ratioOfInterface));	
				
				String extenSql = "INSERT INTO `" + dBname + "`.`extensibilityinfo` (`pkgName`, `projName`, `verID`, `interfaceNum`, `abstractNum`, `concreteNum`,`ratio`) VALUES ('"
						+ packageName + "' , '"
						+ projectNameString + "', '"
						+ versionString + "', "
						+ interfaceNum + ", "
						+ abstractNum + ", "
						+ concreteNum + ","
						+ ratioOfInterface + ");";
				
				stmt.executeUpdate(extenSql);

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("failed to run extensibility query!");
				}
		}
		
		public double getExtensibilityRatio(String pkgName){
			double ratio=0.0;
			try {
				Statement stmt = connect.createStatement();
				//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				
				String str = "Select  ratio as result FROM " + dBname + ".extensibilityinfo where VerID = '"
						+ versionString + "' and projName = '"
						+projectNameString +"' and pkgName = '"
						+ pkgName +"'";
				
				
				ResultSet rs ;
				
				rs= stmt.executeQuery(str);			
				while (rs.next()) {					
					ratio = rs.getDouble("result");				
				}
				
				

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}
			return ratio;			
		}
		
		public double getProjectExtensibilityRatio(){
			
			double ratio=0.0;
			try {
				Statement stmt = connect.createStatement();
				//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				int interfaceCount = 0;
				int sumCount = 0;
				
				
				String str1 = "Select  sum(interfaceNum) as result FROM " + dBname + ".extensibilityinfo where VerID = '"
						+ versionString + "' and projName = '"
						+projectNameString+"'";		
				
				ResultSet rs1 ;
				
				rs1= stmt.executeQuery(str1);			
				while (rs1.next()) {					
					interfaceCount = rs1.getInt("result");				
				}
				
				String str2 = "Select  sum(interfaceNum+abstractNum+concreteNum) as result FROM " + dBname + ".extensibilityinfo where VerID = '"
						+ versionString + "' and projName = '"
						+projectNameString+"'";		
				
				ResultSet rs2 ;
				
				rs2 = stmt.executeQuery(str2);			
				while (rs2.next()) {					
					sumCount = rs2.getInt("result");				
				}
				
				ratio = 1.0*interfaceCount/sumCount;
				
				

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}
			return ratio;			
		}

		
		
		
		
}

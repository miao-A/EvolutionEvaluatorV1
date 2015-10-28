package cn.seu.edu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class ChangeabilityInfoConnector extends DBConnector {
	private Connection connect = null;
	private String projectName;
	private String version;
		
		public ChangeabilityInfoConnector(String projectName, String version){
			super();
			connect = getConnection();
			this.projectName = projectName;
			this.version = version;
		}
		
		public ArrayList<String> getpackageName(){
			ArrayList<String> list = new ArrayList<String>();
			try {

				Statement stmt = connect.createStatement();
				String sql = "SELECT pkgname FROM " + dBname + ".class_packageinfo where ProjName = '"
						+ projectName 
						+ "' and verID = '"
						+ version 
						+ " 'group by pkgname";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					list.add(rs.getString("pkgName"));
//					System.out.println(rs.getString("pkgname"));
					}	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ChangeabilityInfo Connector error");
			}
			
			return list; 

		}
		
		


		public void setChangeabilityInfo(String packageName){
			

			try {
				Statement stmt = connect.createStatement();

				// / efferent  couplings 被该包依赖的外部包数目
				String cestr = "Select  count(distinct importPkgName) as result FROM " + dBname + ".class_packageinfo where pkgname = '"
						+ packageName
						+ "' and verID = '"
						+ version
						+ "' and projName = '" + projectName + "'";


				ResultSet rs;
				int ce = 0;
				int ca = 0;

				rs = stmt.executeQuery(cestr);
				while (rs.next()) {
					ce = rs.getInt("result");

				}

				

				String castr = "Select  count(distinct pkgName) as result FROM " + dBname + ".class_packageinfo where importPkgName = '"
						+ packageName
						+ "' and verID = '"
						+ version
						+ "' and projName = '" + projectName + "'";


				rs = stmt.executeQuery(castr);
				while (rs.next()) {
					ca = rs.getInt("result");
				}
				

				double changeability = 100.0*ce/(ca+ce);
				DecimalFormat df = new DecimalFormat("0.00");				

				
				String changeSql = "INSERT INTO `" + dBname + "`.`changeabilityinfo`  (`pkgName`, `projName`, `verID`, `coupleAfferent`, `coupleEfferent`, `ratio`) VALUES ('"
						+ packageName + "' , '"
						+ projectName + "', '"
						+ version + "', "
						+ ca + ", "
						+ ce + ", "
						+ changeability + ")";
				
				stmt.executeUpdate(changeSql);

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("failed to run changeabilityinfo update!");
				}
		}
		
		public double getChangeabilityRatio(String pkgName){
			double ratio=0.0;
			try {
				Statement stmt = connect.createStatement();
				//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
				
				String str = "Select  ratio as result FROM " + dBname + ".changeabilityinfo where VerID = '"
						+ version + "' and projName = '"
						+projectName +"' and pkgName = '"
						+ pkgName +"'";				
				
				ResultSet rs ;
				
				rs= stmt.executeQuery(str);			
				while (rs.next()) {					
					ratio = rs.getDouble("result");
				}
				
				

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run changeabilityinfo query!");
			}
			return ratio;			
		}
}

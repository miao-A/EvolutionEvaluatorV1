package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExtensibilityConnector {

private Connection connect = null;
private String projectNameString;
private String versionString;

	
	public ExtensibilityConnector(String projectName, String version){
		//super();
		
		this.projectNameString = projectName;
		this.versionString = version;
	}
	
	public ArrayList<String> getpackageName(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		connect = DBConnector.getConnection();
		try {

			stmt = connect.createStatement();
			String sql = "SELECT pkgname FROM classTypeinfo where ProjName = '"
					+ projectNameString 
					+ "' and verID = '"
					+ versionString 
					+"' group by pkgName";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("pkgName"));
//				System.out.println(rs.getString("pkgname"));
				}	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("extensibility Connector error");
		}finally{
			DBConnector.closeFun(rs, stmt, connect);
		}
		
		return list; 

	}
	
	
	public void extendsibilityUpdateStatement(String packageName,String className,String classType ){
		connect = DBConnector.getConnection();
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO classTypeinfo (`pkgName`, `ClassName`, `ProjName` , `VerID`, `ClassType`) VALUES ('"
					+ packageName
					+"','"
					+className
					+"','"
					+projectNameString
					+"','"
					+versionString
					+"','"
					+classType
					+"')";
			stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("insert failed!");
			}finally{
				DBConnector.closeFun(null, stmt, connect);
			}
		
	
	}
	

///将来改造为接受外部分析输入来源，目前内部用字符串替代了选择	
	public ArrayList<String> packageExtensibilityRatio(String packageName){
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			String str = "Select  count(classname) as result FROM classTypeinfo where pkgname = '"
			+ packageName 
			+ "' and VerID = '" 
			+ versionString + "' and projName = '"
			+projectNameString +"'";
			
			String concretestr = str +" and classtype = 'concrete'";
			String abstractstr = str +" and classtype = 'abstract'";
			String interfacestr = str +" and classtype = 'interface'";
			
			rStrings.add(packageName);
			
			
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
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}finally{
				DBConnector.closeFun(rs, stmt, connect);
			}
		return rStrings;
	}
	
public ArrayList<String> packageExtensibilityInfo(String packageName){
	connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			String str = "Select  * FROM classTypeinfo where pkgname = '"
			+ packageName 
			+ "' and VerID = '" 
			+ versionString + "' and projName = '"
			+projectNameString +"'";


/*			
			String concretestr = str +" and classtype = 'concrete'";
			String abstractstr = str +" and classtype = 'abstract'";
			String interfacestr = str +" and classtype = 'interface'";*/
			
			//rStrings.add(packageName);
			
			

			
			rs= stmt.executeQuery(str);			
			while (rs.next()) {
				rStrings.add(rs.getString("classname")+"&"+rs.getString("classtype"));
				
			}			
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}finally{
				DBConnector.closeFun(rs, stmt, connect);
			}
		return rStrings;
	}
	
	
	
	public ArrayList<String> projectExtensibilityRatio(){
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			//{"PackageName","concereteClass", "interfaceClass","abstractClass","totalClass","ratio %"};
			
			String str = "Select  count(classname) as result FROM classTypeinfo where VerID = '"
			+ versionString + "' and projName = '"
					+projectNameString +"'";
			String concretestr = str +" and classtype = 'concrete'";
			String abstractstr = str +" and classtype = 'abstract'";
			String interfacestr = str +" and classtype = 'interface'";
			
			rStrings.add("project");
			
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
			

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("failed to run extensibility query!");
			}finally{
				DBConnector.closeFun(rs, stmt, connect);
			}
		return rStrings;
	}
	
	
	
	public ArrayList<String> selcetStatement(String str,String selcetString){
		connect = DBConnector.getConnection();
		ArrayList<String> rStrings = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(str);
			while (rs.next()) {
				rStrings.add(rs.getString(selcetString));
				}			
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				DBConnector.closeFun(rs, stmt, connect);
			}
		return rStrings;
	}
}

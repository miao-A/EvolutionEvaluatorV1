package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectInfoConnector extends DBConnector {

	private Connection connect = null;
	private String projectName;
	private String version;
	private String introduction;
	
	public ProjectInfoConnector(){
		super();
		connect = getConnection();
	};
	
	public ProjectInfoConnector(String projectName, String version,String introduction){
		super();
		connect = getConnection();
		this.projectName = projectName;
		this.version = version;
		this.introduction = introduction;
		
		
		try {		

			Statement stmt = connect.createStatement();
			String sql = "INSERT INTO `" + dBname + "`.`projectinfo` (`projName`, `verID`, `projectInfo`) VALUES ('"
				+ projectName + "', '"
				+ version + "', '"
				+introduction +"')";
			stmt.executeUpdate(sql);
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("insert failed!");
			}		
	}
	
	public ArrayList<String> getProject() {
		
		ArrayList<String> rsList = new ArrayList<String>();
		String sqlstr = "SELECT projName FROM " + dBname + ".projectinfo group by projName";
		Statement stmt;
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(sqlstr);
			while (rs.next()) {
				rsList.add(rs.getString("projName"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ProjectConnector error!");
		}
		
		return rsList;
	}
	
	public ArrayList<String> getVersion(String projectName) {
		
		ArrayList<String> versionList = new ArrayList<String>();
		
		String sqlstr = "SELECT verID FROM " + dBname + ".projectinfo where projName = '"
				+ projectName +"'";
		Statement stmt;
		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(sqlstr);
			while (rs.next()) {
				versionList.add(rs.getString("verID"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ProjectConnector error!");
		}		
		return versionList;		
	}
	
	public void setIntroduction(String string){
		this.introduction = string;
	}
	
	public String getIntroduction(){
		return introduction;
	}
}

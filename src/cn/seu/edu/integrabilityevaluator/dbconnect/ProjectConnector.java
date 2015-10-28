package cn.seu.edu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectConnector extends DBConnector {
	

	private Connection connect = null;
	
	public ProjectConnector(){
		super();
		connect = getConnection();
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
				+ projectName +"' order by verID asc";
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

}

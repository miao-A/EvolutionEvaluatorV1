﻿package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectConnector {
	

	private Connection connect = null;

	public ProjectConnector(){
		//super();		
	}
	
	public ArrayList<String> getProject() {
		connect = DBConnector.getConnection();
		ArrayList<String> rsList = new ArrayList<String>();
		String sqlstr = "SELECT projName FROM projectinfo group by projName";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(sqlstr);
			while (rs.next()) {
				rsList.add(rs.getString("projName"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ProjectConnector error!");
		}finally{
			DBConnector.closeFun(rs, stmt, connect);
		}
		
		return rsList;
	}
	
	public ArrayList<String> getVersion(String projectName) {
		
		ArrayList<String> versionList = new ArrayList<String>();
		connect = DBConnector.getConnection();
		String sqlstr = "SELECT verID FROM projectinfo where projName = '"
				+ projectName +"' order by verID asc";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(sqlstr);
			while (rs.next()) {
				versionList.add(rs.getString("verID"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ProjectConnector error!");
		}finally{
			DBConnector.closeFun(rs, stmt, connect);
		}
		
		return versionList;
		
	}

}

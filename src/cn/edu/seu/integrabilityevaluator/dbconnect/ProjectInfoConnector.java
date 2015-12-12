package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectInfoConnector{

	private Connection connect = null;
	private String projectName;
	private String version;
	private String introduction;
	public ProjectInfoConnector() {
		//super();
		//connect = DBConnector.getConnection();
	};

	public ProjectInfoConnector(String projectName, String version,
			String introduction) {
		super();
		connect = DBConnector.getConnection();
		this.projectName = projectName;
		this.version = version;
		this.introduction = introduction;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			stmt = connect.createStatement();
			String sql = "INSERT INTO projectinfo (`projName`, `verID`, `projectInfo`) VALUES ('"
					+ projectName + "', '" + version + "', '" + introduction
					+ "')";
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert failed!");
		}finally{
			DBConnector.closeFun(rs, stmt, connect);
		}
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
		connect = DBConnector.getConnection();
		ArrayList<String> versionList = new ArrayList<String>();

		String sqlstr = "SELECT verID FROM projectinfo where projName = '" + projectName + "'";
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

	public void setIntroduction(String string) {
		this.introduction = string;
	}

	public String getIntroduction() {
		return introduction;
	}
}

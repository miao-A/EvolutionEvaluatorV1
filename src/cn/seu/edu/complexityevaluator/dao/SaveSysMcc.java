﻿package cn.seu.edu.complexityevaluator.dao;

import java.sql.Connection;
import java.sql.SQLException;

import cn.seu.edu.complexityevaluator.creatast.Requestor;
import cn.seu.edu.complexityevaluator.util.DBConnection;

public class SaveSysMcc {

	// 计算并存储系统的Mccabe数据
	public void saveMccabeInfo(String projName,String projVersion,String projPath) {
		int mccabeValue = Requestor.allMccabeNum;
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Connection conn = DBConnection.conn();
			java.sql.PreparedStatement ps = conn
					.prepareStatement("insert into t_systemmccabe(PROJECTNAME,VERSIONID,MCCABE,PROJPATH) values(?,?,?,?)");
			ps.setString(1, projName);
			ps.setString(2, projVersion);
			ps.setInt(3, mccabeValue);
			ps.setString(4,projPath);
			ps.executeUpdate();
			DBConnection.closeFunc(ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


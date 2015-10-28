﻿package cn.seu.edu.complexityevaluator.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import cn.seu.edu.complexityevaluator.util.DBConnection;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.ClassMccabeVisitor;

public class SaveClassMcc {
	public void saveStatistics(AbstractVisitor visitor,String sourceFilePath,String projName,String projVersion) {
		//从全路径文件名中提取文件名
		File tempFile =new File( sourceFilePath.trim());   
	    String fileName = tempFile.getName();
		// 统计类层级圈复杂度
		if (visitor instanceof ClassMccabeVisitor) {
			ClassMccabeVisitor classMccabeVistor = (ClassMccabeVisitor) visitor;
			Map<String, Integer> classMccabeInfo = classMccabeVistor
					.getClassMccabeInfo();
			//System.out.println(classMccabeInfo);
			Iterator iter = classMccabeInfo.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// 将前民的metodpath拿到
				String path = (String) entry.getKey();
				String allpath[] = path.split(":");
				// 包名
				String packageName = allpath[0];
				// 类型
				String className = allpath[1];
				// 将圈复杂的值拿到
				int mccabeValue = (int) entry.getValue();
				// 将数据存贮到数据库中
				DBConnection dbConn = new DBConnection();
				// 加载驱动
				try {
					Class.forName("com.mysql.jdbc.Driver");

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Connection conn = dbConn.conn();
					java.sql.PreparedStatement ps = conn
							.prepareStatement("insert into t_classmccabe(PROJECTNAME,VERSIONID,FILENAME,PACKAGENAME,CLASSNAME,MCCABE) values(?,?,?,?,?,?)");
					ps.setString(1, projName);
					ps.setString(2, projVersion);
					ps.setString(3, fileName);
					ps.setString(4, packageName);
					ps.setString(5, className);
					ps.setInt(6, mccabeValue);
					ps.executeUpdate();
					dbConn.closeFunc(ps, conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		
		
	}
}

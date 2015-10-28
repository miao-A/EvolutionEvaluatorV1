﻿package cn.seu.edu.complexityevaluator.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Bag;

import cn.seu.edu.complexityevaluator.util.DBConnection;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.ClassHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.MethodHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.ClassMccabeVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MethodMccabeVisitor;

public class SaveClassHstd {
	public void saveStatistics(AbstractVisitor visitor,String sourceFilePath,String projName,String projVersion) {
		//从全路径文件名中提取文件名
		File tempFile =new File( sourceFilePath.trim());   
	    String fileName = tempFile.getName();

		// 类层级halstead数据存贮
		if (visitor instanceof ClassHalsteadVisitor) {
			ClassHalsteadVisitor typeHalsteadVistor = (ClassHalsteadVisitor) visitor;
			Map<String, String> typeHalsteadInfo = typeHalsteadVistor
					.getTypeHalsteadInfo();
			Bag<String> typeOperators = typeHalsteadVistor.getTypeOperators();
			Bag<String> typeOperand = typeHalsteadVistor.getTypeOperands();
			//System.out.println("全部操作数"+typeOperand);
			//System.out.println("全部操作符"+typeOperators);
			Iterator it = typeHalsteadInfo.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				// 将前面的类路径拿到
				String path = (String) entry.getKey();
				String allpath[] = path.split(":");
				// 包名
				String packageName = allpath[0];
				// 类型
				String className = allpath[1];
				// 操作符操作数存贮格式 0：0：0：0，分别取出来
				String halsteadvalue = (String) entry.getValue();
				// 唯一操作符数目
				int uniqueOpertorNum = Integer.parseInt(halsteadvalue
						.split(":")[0]);
				// 唯一操作符数目
				int uniqueOperandNum = Integer.parseInt(halsteadvalue
						.split(":")[1]);

				// 总的操作符数目
				int OpertorNum = Integer.parseInt(halsteadvalue.split(":")[2]);
				// 总的操作数数目
				int OperandNum = Integer.parseInt(halsteadvalue.split(":")[3]);
				// 程序预测长度
				double length = OpertorNum+OperandNum;
				// 容量
				double volume = (OpertorNum + OperandNum)
						* Math.log(uniqueOpertorNum + uniqueOperandNum);
				// bugs数目
				double bugs = volume / 3000;
				// 程序难度
				double difficulty = (uniqueOpertorNum / 2)
						* (OperandNum / uniqueOperandNum);
				// 工作量
				double effort = volume*difficulty;
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
							.prepareStatement("insert into t_classhalstead(PROJECTNAME,VERSIONID,FILENAME,PACKAGENAME,CLASSNAME,UNIQUEOPERATORNUM,UNIQUEOPERANDNUM,OPERATORNUM,OPERANDNUM,LENGTH,VOLUME,BUGS,EFFORT,DIFFICULTY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, projName);
					ps.setString(2, projVersion);
					ps.setString(3, fileName);
					ps.setString(4, packageName);
					ps.setString(5, className);
					ps.setInt(6, uniqueOpertorNum);
					ps.setInt(7, uniqueOperandNum);
					ps.setInt(8, OpertorNum);
					ps.setInt(9, OperandNum);
					ps.setDouble(10, length);
					ps.setDouble(11, volume);
					ps.setDouble(12, bugs);
					ps.setDouble(13, effort);
					ps.setDouble(14, difficulty);
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

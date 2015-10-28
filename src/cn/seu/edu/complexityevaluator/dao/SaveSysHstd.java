package cn.seu.edu.complexityevaluator.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.collections15.Bag;

import cn.seu.edu.complexityevaluator.creatast.Requestor;
import cn.seu.edu.complexityevaluator.util.DBConnection;

public class SaveSysHstd {
	// 计算并存储系统的halstead数据
	public void saveSystemHalsteadInfo(String projName,String projVersion,String projPath) {
		Bag<String> allOperators = Requestor.allOperators;
		Bag<String> allOperands = Requestor.allOperands;
		int uniqueOpertorNum = allOperators.uniqueSet().size();
		// 唯一操作数数目
		int uniqueOperandNum = allOperands.uniqueSet().size();
		// 总的操作符数目
		int OpertorNum = allOperators.size();
		// 总的操作数数目
		int OperandNum = allOperands.size();
		// 程序预测长度
		int length =OpertorNum+OperandNum;
		// 容量
		double volume = (double)(OpertorNum + OperandNum)
				* Math.log(uniqueOpertorNum + uniqueOperandNum);
		// bugs数目
		double bugs = volume / 3000.0;
		// 程序难度
		double difficulty = (uniqueOpertorNum / 2.0)
				* (OperandNum /(double) uniqueOperandNum);
		// 工作量
		double effort = volume*difficulty;
		// 将数据存贮到数据库中
	
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
					.prepareStatement("insert into t_systemhalstead(PROJECTNAME,VERSIONID,UNIQUEOPERATORNUM,UNIQUEOPERANDNUM,OPERATORNUM,OPERANDNUM,LENGTH,VOLUME,BUGS,EFFORT,DIFFICULTY) values(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, projName);
			ps.setString(2, projVersion);
			ps.setInt(3, uniqueOpertorNum);
			ps.setInt(4, uniqueOperandNum);
			ps.setInt(5, OpertorNum);
			ps.setInt(6, OperandNum);
			ps.setDouble(7, length);
			ps.setDouble(8, volume);
			ps.setDouble(9, bugs);
			ps.setDouble(10, effort);
			ps.setDouble(11, difficulty);
			ps.executeUpdate();
			DBConnection.closeFunc(ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

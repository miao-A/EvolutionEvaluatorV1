/* 作者 何磊
 * 日期 2015-5-15
 * 版本 2.0  简化了查询数据库的很多相同操作，将这个类进行了抽象化
 * 描述  主要是查询数据库的操作
 * */
package cn.seu.edu.complexityevaluator.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryAndDeleteUtil {
//	private static  ResultSet rs;
	// 到数据库将数据查询到需要的数据方法
	public ResultSet queryInfo(String sql) {
		PreparedStatement ps;
		ResultSet rs=null;
		// 建立数据库连接
		Connection conn = DBConnection.conn();
		// 创建statement
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			// dbConnection.closeFuncc(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public void deleteInfo(String sql){
		PreparedStatement ps;
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 建立数据库连接
		Connection conn = DBConnection.conn();
		// 创建statement
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

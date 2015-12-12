package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


  

public class DBConnector {
	private static String user = "root";
	private static String pwd = "123456";
	private static String url = "jdbc:mysql://localhost:3306/ee";
	//private Connection connect = null;
	//protected String dBname = "ee";

	public DBConnector() {
		
	}

	protected static Connection getConnection() {
		Connection connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
			// System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(url, user, pwd);
			// 连接URL为 jdbc:mysql//服务器地址/数据库名
			// 后面的2个参数分别是登陆用户名和密码
			// System.out.println("Success connect Mysql server!");

		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
		return connect;
	}

	// 关闭资源
	public static void closeFun(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) { // 关闭记录集
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) { // 关闭声明
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) { // 关闭连接对象
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 关闭资源
	public static void closeFunc(PreparedStatement stmt, Connection conn) {
		if (stmt != null) { // 关闭声明
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) { // 关闭连接对象
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

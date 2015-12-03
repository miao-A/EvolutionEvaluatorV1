package cn.edu.seu.integrabilityevaluator.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
	private String user = "root";
	private String pwd = "123456";
	private String url = "jdbc:mysql://localhost:3306/mysql";
	private Connection connect=null;
	protected String dBname = "eosti";
	
	public DBConnector(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载MYSQL JDBC驱动程序
			// Class.forName("org.gjt.mm.mysql.Driver");
			//System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = DriverManager.getConnection(url,user,pwd);
			// 连接URL为 jdbc:mysql//服务器地址/数据库名
			// 后面的2个参数分别是登陆用户名和密码
			//System.out.println("Success connect Mysql server!");

		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection(){
		return connect;
	}
	
	protected void finalize() {

		try {
			connect.close();
			//System.out.println("release connect Mysql server!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	

}

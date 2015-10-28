package cn.seu.edu.complexityevaluator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.seu.edu.complexityevaluator.util.DBConnection;
import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;

public class QueryProjInfoDaoImpl implements QueryProjInfoDao {
	private QueryAndDeleteUtil queryData = new QueryAndDeleteUtil();
	@Override
	//查询已有项目名
	public List<String> queryExistProjName() {
		String projectName_sql = "select distinct projectName from t_systemmccabe";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = DBConnection.conn();
		List<String> projects=new ArrayList<String>();
		try {
			    
				 ps = conn.prepareStatement(projectName_sql);
				 rs = ps.executeQuery();
				 while(rs.next()){
					 String item = rs.getString(1);
					 projects.add(item);
				 }
					// dbConnection.closeFuncc(rs, ps, conn);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) { // 关闭声明
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}	
	}
		return projects;
	}

	@Override
	//查询选中项目名下版本号
	public List<String> queryExistProjVersion(String projName) {
		String projectVersion = "select distinct versionID from t_systemmccabe where projectName='"
				+ projName + "'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = DBConnection.conn();
		List<String> versions=new ArrayList<String>();
		try {
			    
				 ps = conn.prepareStatement(projectVersion);
				 rs = ps.executeQuery();
				 while(rs.next()){
					 String item = rs.getString(1);
					 versions.add(item);
				 }
					// dbConnection.closeFuncc(rs, ps, conn);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) { // 关闭声明
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}	
	}
		return versions;
	}
   //查询具体版本的系统圈复杂度
	@Override
	public int queryExactSysMcc(String projName, String Version) {
		String mccabeSysSql = "select mccabe from t_systemmccabe where projectName='"
				+ projName + "' and versionID='" + Version
				+ "'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = DBConnection.conn();
		int sysMcc=0;
		try {
			    
				 ps = conn.prepareStatement(mccabeSysSql);
				 rs = ps.executeQuery();
				 while(rs.next()){
					  sysMcc = rs.getInt(1);
				 }
					// dbConnection.closeFuncc(rs, ps, conn);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) { // 关闭声明
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}	
	}
		return sysMcc;
	}
   //查询具体项目的路径
	public String queryProjPath(String projName, String Version) {
		String mccabeSysSql = "select projPath from t_systemmccabe where projectName='"
				+ projName + "' and versionID='" + Version
				+ "'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = DBConnection.conn();
		String path=null;
		try {
			    
				 ps = conn.prepareStatement(mccabeSysSql);
				 rs = ps.executeQuery();
				 while(rs.next()){
					  path = rs.getString(1);
				 }
					// dbConnection.closeFuncc(rs, ps, conn);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) { // 关闭声明
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}	
	}
		return path;
	}
	//查询类级别圈复杂度
	@Override
	public ResultSet queryExactClassMccProjVersion(String projName,	String Version) {
		String methodMccSql = "select * from t_classmccabe where mccabe>50 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ Version
				+ "'order by mccabe desc";
		return queryData.queryInfo(methodMccSql);
	}

	@Override
	public ResultSet queryExactClassHsdProjVersion(String projName,
			String Version) {
		String classHsdSql = "select * from t_classhalstead where volume>7000 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ Version
				+ "'order by volume desc";
		return queryData.queryInfo(classHsdSql);
	}

	@Override
	public ResultSet queryExactMethodMccProjVersion(String projName,
			String Version) {
		String methodMccSql = "select * from t_methodmccabe where mccabe>10 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ Version
				+ "' order by mccabe desc";
		return queryData.queryInfo(methodMccSql);
	}

	@Override
	public ResultSet queryExactMethodHsdProjVersion(String projName,
			String Version) {
		String methodHsdSql = "select * from t_methodhalstead where volume>1500 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ Version
				+ "' order by volume desc";
		return queryData.queryInfo(methodHsdSql);
	}

	@Override
	public ResultSet queryExactSysHsdProjVersion(String projName, String Version) {
		String sysHalsteadSql = "select * from t_systemhalstead where projectName='"
				+ projName
				+ "' and versionID='"
				+ Version
				+ "'";
		return queryData.queryInfo(sysHalsteadSql);
	}

	@Override
	//限制结束版本号只有一个
	public ResultSet queryEndVersion(String projName, String sversion) {
		String projectVersion = "select distinct versionID from t_systemmccabe where projectName='"
				+ projName
				+ "' and versionID>'"
				+ sversion + "'limit 1" ;
		return queryData.queryInfo(projectVersion);
	}
	//连续版本变更模块下面的查询语句
	//查询包名
	public ResultSet queryPackage(String projName, String eversion) {
		String packagetName = "select distinct packageName from t_methodmccabe where projectName='"
				+ projName
				+ "' and versionID='"
				+ eversion + "'";
		return queryData.queryInfo(packagetName);
	}
	//查询类名
	public ResultSet queryClassName(String projName, String eversion,String packageName,String fileName) {
		String packagetName = "select distinct className from t_methodmccabe where projectName='"
				+ projName
				+ "' and versionID='"
				+ eversion + "' and packageName='"+packageName+"' and fileName='"+fileName+"'";
		return queryData.queryInfo(packagetName);
	}
	//查询方法名
	public ResultSet queryMethodName(String projName, String eversion,String packageName,String className) {
		String packagetName = "select  methodName from t_methodmccabe where projectName='"
				+ projName
				+ "' and versionID='"
				+ eversion + "' and packageName='"+packageName+"' and className='"+className+"'";
		return queryData.queryInfo(packagetName);
	}
//拿到文件名
	@Override
	public ResultSet queryFileName(String projName, String eversion,
			String packageName) {
		String fileName = "select  distinct fileName from t_methodmccabe where projectName='"
				+ projName
				+ "' and versionID='"
				+ eversion + "' and packageName='"+packageName+"'";
		return queryData.queryInfo(fileName);
	}

	@Override
	public boolean isExist(String projName, String version) {
		String sql = "select projectName, versionID from t_systemmccabe where projectName='"
				+ projName + "'and versionID='"+version+"'";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = DBConnection.conn();
		boolean flag=false;//标志位，false是没有，true代表已存在
		try {
			    
				 ps = conn.prepareStatement(sql);
				 rs = ps.executeQuery();
				 if(rs.next()){
					flag=true;
				 }
					// dbConnection.closeFuncc(rs, ps, conn);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) { // 关闭记录集
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) { // 关闭声明
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) { // 关闭连接对象
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
		}	
	}
		return flag;
	}
}

package cn.seu.edu.complexityevaluator.dao;

import java.sql.ResultSet;
import java.util.List;

public interface QueryProjInfoDao {
	//查询数据库中已有项目的项目名称
	public List<String> queryExistProjName();
	//查询选中项目名的所有版本号
	public List<String> queryExistProjVersion(String projName);
	//查询选中起始号后结束版本号
	public ResultSet queryEndVersion(String projName,String sversion);
	//查询具体版本的系统圈复杂度数值
	public int queryExactSysMcc(String projName,String Version);
	//查询具体项目的存储路径
	public String queryProjPath(String projName,String Version);
	//查询系统层hasltead
	public ResultSet queryExactSysHsdProjVersion(String projName,String Version);
	//查询具体版本的类圈复杂度
	public ResultSet queryExactClassMccProjVersion(String projName,String Version);
	//查询类级别hastead复杂度
	public ResultSet queryExactClassHsdProjVersion(String projName,String Version);
	//查询方法圈复杂度
	public ResultSet queryExactMethodMccProjVersion(String projName,String Version);
	//查询方法及halstead
	public ResultSet queryExactMethodHsdProjVersion(String projName,String Version);
	//查询包名
	public ResultSet queryPackage(String projName, String eversion);
	//拿到文件名
	public ResultSet queryFileName(String projName,String eversion,String packageName);
	//查类名
	public ResultSet queryClassName(String projName, String eversion,String packageName,String fileName);
	//查方法名
	public ResultSet queryMethodName(String projName, String eversion,String packageName,String className);
	//查询项目和版本号是否已存在
	public boolean isExist(String projName,String version);
	
}

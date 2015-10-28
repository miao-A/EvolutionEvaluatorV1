/* 作者 何磊
 * 日期 2015-6-29
 * 描述 主要是对收集到的数据进行方法层和类层数据库存储的操作
 * 版本 2.0
 * 更新：增加存贮每个方法具体的条件操作选择符
 * */
package cn.seu.edu.complexityevaluator.dao;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.seu.edu.complexityevaluator.util.DBConnection;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MethodMccabeVisitor;
public class SaveMethodMcc {
	public void saveMethodMcc(AbstractVisitor visitor,String sourceFilePath,String projName,String projVersion) {
		//从全路径文件名中提取文件名
		File tempFile =new File( sourceFilePath.trim());   
	    String fileName = tempFile.getName();
		// 统计方法层圈复杂度数据
		if (visitor instanceof MethodMccabeVisitor) {
			MethodMccabeVisitor methodMccabeVistor = (MethodMccabeVisitor) visitor;
			Map<String, Integer> methodMccabeInfo = methodMccabeVistor
					.getMethodMccabeInfo();
			Map<String,List<String>> mccabeOperators=methodMccabeVistor.getMccabeOperators();
			//System.out.println(methodMccabeInfo);
			Iterator iter = methodMccabeInfo.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// 将前面的metodpath拿到
				String path = (String) entry.getKey();
		
				String allpath[] = path.split("\\|");
				// 包名
				String packageName = allpath[0];
				// 类型
				String className = allpath[1];
				// 方法全称
				String methodName = allpath[2];
				//获取具体的条件选择操作符
				List<String> operator=null;
				StringBuffer temp=new StringBuffer();//遍历这个operator的list,变为string进行存储
				if(mccabeOperators.containsKey(path)){
					operator=mccabeOperators.get(path);
					for(String s:operator){
						temp.append(s+"-");
					}
				}else{
					temp.append(" ");
				}
				
				
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
							.prepareStatement("insert into t_methodmccabe(PROJECTNAME,VERSIONID,FILENAME,PACKAGENAME,CLASSNAME,METHODNAME,MCCABE,OPERATOR) values(?,?,?,?,?,?,?,?)");
					ps.setString(1, projName);
					ps.setString(2, projVersion);
					ps.setString(3, fileName);
					ps.setString(4, packageName);
					ps.setString(5, className);
					ps.setString(6, methodName);
					ps.setInt(7, mccabeValue);
					ps.setString(8, temp.toString());
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

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

import org.apache.commons.collections15.Bag;
import org.apache.commons.collections15.bag.HashBag;

import cn.seu.edu.complexityevaluator.creatast.Requestor;
import cn.seu.edu.complexityevaluator.util.DBConnection;
import cn.seu.edu.complexityevaluator.visitor.AbstractVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.ClassHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.halsteadvisitor.MethodHalsteadVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.ClassMccabeVisitor;
import cn.seu.edu.complexityevaluator.visitor.mccabevisitor.MethodMccabeVisitor;
public class SaveMethodHstd {
	public void saveStatistics(AbstractVisitor visitor,String sourceFilePath,String projName,String projVersion) {
		//从全路径文件名中提取文件名
		File tempFile =new File( sourceFilePath.trim());   
	    String fileName = tempFile.getName();
		
	    // 统计方法的halstead数据
		if (visitor instanceof MethodHalsteadVisitor) {
			MethodHalsteadVisitor methodHalsteadVistor = (MethodHalsteadVisitor) visitor;
			Map<String, String> methodHalsteadInfo = methodHalsteadVistor
					.getMethodHalsteadInfo();
			//System.out.println(methodHalsteadInfo);
			Iterator iter = methodHalsteadInfo.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				// System.out.println(entry);
				// 将前民的metodpath拿到
				String path = (String) entry.getKey();
				String allpath[] = path.split(":");
				// 包名
				String packageName = allpath[0];
				// 类型
				String className = allpath[1];
				// 方法全称
				String methodName = allpath[2];
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
				// 程序实际长度
				double length = OpertorNum+OperandNum;
				// 容量
				double volume = (double)(OpertorNum + OperandNum)
						* Math.log(uniqueOpertorNum + uniqueOperandNum);
				// bugs数目
				double bugs = volume / 3000.0;
				// 程序难度
				double difficulty = (uniqueOpertorNum / 2.0)
						* (OperandNum / uniqueOperandNum);
				// 工作量
				double effort = volume *difficulty;
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
							.prepareStatement("insert into t_methodhalstead(PROJECTNAME,VERSIONID,FILENAME,PACKAGENAME,CLASSNAME,METHODNAME,UNIQUEOPERATORNUM,UNIQUEOPERANDNUM,OPERATORNUM,OPERANDNUM,LENGTH,VOLUME,BUGS,EFFORT,DIFFICULTY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, projName);
					ps.setString(2, projVersion);
					ps.setString(3, fileName);
					ps.setString(4, packageName);
					ps.setString(5, className);
					ps.setString(6, methodName);
					ps.setInt(7, uniqueOpertorNum);
					ps.setInt(8, uniqueOperandNum);
					ps.setInt(9, OpertorNum);
					ps.setInt(10, OperandNum);
					ps.setDouble(11, length);
					ps.setDouble(12, volume);
					ps.setDouble(13, bugs);
					ps.setDouble(14, effort);
					ps.setDouble(15, difficulty);
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

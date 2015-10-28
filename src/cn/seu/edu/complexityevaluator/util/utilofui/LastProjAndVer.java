/* 作者 何磊
 * 日期 2015/5/24
 * 版本：1.0
 * 描述：这个类是用于从数据库中获取最新的项目名和对应的版本号，设置界面上的默认项目名和项目版本号。主要是用在类层级和方法层级界面
 *      所以这里返回的是一个字符创数组，长度为2，第一个字符串是项目名，第二个是版本号
 * 问题：初始化为空的问题下个版本解决/已解决
 * */
package cn.seu.edu.complexityevaluator.util.utilofui;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;

public class LastProjAndVer {
	private QueryAndDeleteUtil queryData = new QueryAndDeleteUtil();

	public String[] last() {
		// int count=0;
		String[] initData = new String[2];
		String projectName_sql = "select distinct projectName from t_systemmccabe";
		ResultSet rs_projectName = queryData.queryInfo(projectName_sql);
		// 设置默认项目名，拿到最后的那个数据
		try {
			// 如果不为空的话，会进入循环
			while (rs_projectName.next()) {
				if (rs_projectName.isLast()) {
					initData[0] = rs_projectName.getString(1);
				}
			}
			// 为空next()的false
			//initData[0] = " ";
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 查询默认项目名下的版本号的数据
		// 首先拿到当前选中的项目号
		String projectVersion_sql = "select distinct versionID from t_systemmccabe where projectName='"
				+ initData[0] + "'";
		ResultSet rs_projectVersion = queryData.queryInfo(projectVersion_sql);
		try {
			while (rs_projectVersion.next()) {
				if (rs_projectVersion.last()) {
					initData[1] = rs_projectVersion.getString(1);
				}
			}
			//initData[1] = " ";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initData;
	}

	// 测试一下
	public static void main(String[] args) {
		LastProjAndVer init = new LastProjAndVer();
		init.last();
		System.out.println(init.last()[0]);
		System.out.println(init.last()[1]);
	}
}

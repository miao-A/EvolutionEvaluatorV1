﻿/* 
 * 日期 2015-6-1
 * 描述 用来初始化下拉框中的数据
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.util.utilofui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.swt.widgets.Combo;

import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;

public class InitialCombo {
	private QueryProjInfoDao queryProjInfoDao = new QueryProjInfoDaoImpl();

	// 初始化项目下拉条数据,没有设置默认数据
	public void initialProj(Combo name) {
		// 查询数据库中已有项目名
		List<String> projects = queryProjInfoDao.queryExistProjName();
		for(String s:projects){
			name.add(s);
		}	
	}
	//初始化项目下拉条，设置其默认数据
	public void initProj(Combo name){
		List<String> projects = queryProjInfoDao.queryExistProjName();
		for(String s:projects){
			name.add(s);			
		}
		name.select(projects.size()-1);
	}
	//初始化版本下拉条，设置默认值
	public void initVersion(String projName,Combo name){
		List<String> projectVersion=queryProjInfoDao.queryExistProjVersion(projName);
			for (String item:projectVersion) {
				name.add(item);
			}
			// 设置默认版本号
			name.select(projectVersion.size()-1);	
	}
	//初始化版本下拉条，没有默认数据
	public void initialVersion(String projName,Combo name){
		List<String> projectVersion=queryProjInfoDao.queryExistProjVersion(projName);
		
		for (String item:projectVersion) {
			name.add(item);
		}
	}
	//初始化结束版本下拉条
	public void initialEversion(Combo projectName,Combo sVersionName,Combo eVersionName){
		ResultSet rs_projectVersion = queryProjInfoDao.queryEndVersion(
				projectName.getText(), sVersionName.getText());
		try {
			while (rs_projectVersion.next()) {
				String item = rs_projectVersion.getString(1) + "";
				eVersionName.add(item);
			}
			// combo_version.select(rs_projectVersion.getRow());
			// 如果项目进行切换，会把版本号查出来，默认选择为空，然后将下面的数据清空

		} catch (SQLException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
}
}

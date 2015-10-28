﻿package cn.seu.edu.complexityevaluator.ui.halsteadui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.org.eclipse.wb.swt.SWTResourceManager;
import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;
import cn.seu.edu.complexityevaluator.util.utilofui.LastProjAndVer;

public class MethodHstdInfo extends Composite {
	private static Table methodHsd_table;

	public static Table getMethodHsd_table() {
		return methodHsd_table;
	}

	private QueryProjInfoDao queryMethodHsdData = new QueryProjInfoDaoImpl();
	// 初始化数据类
	private LastProjAndVer init = new LastProjAndVer();

	public MethodHstdInfo(Composite parent, int style) {
		super(parent, style);
		// 重点关注模块报表
		Label label = new Label(this, SWT.NONE);
		label.setBounds(5, 10, 164, 17);
		label.setText("Volume超过阈值的方法列表");
		// 添加滚动界面容器
		ScrolledComposite scrolledComposite = new ScrolledComposite(this,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(5, 33, 729, 356);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// 滚动容器里面添加容器，用于放置其他的控件
		Composite table_composite = new Composite(scrolledComposite, SWT.NONE);
		methodHsd_table = new Table(table_composite, SWT.BORDER);
		methodHsd_table.setBounds(0, 0, 715, 352);
		methodHsd_table.setHeaderVisible(true);
		methodHsd_table.setLinesVisible(true);
		// 设置滚动容器所依赖的容器
		scrolledComposite.setContent(table_composite);
		scrolledComposite.setMinSize(table_composite.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		// 包名列
		TableColumn package_column = new TableColumn(methodHsd_table, SWT.NONE);
		package_column.setWidth(133);
		package_column.setText("包名");

		TableColumn class_column = new TableColumn(methodHsd_table, SWT.NONE);
		class_column.setWidth(149);
		class_column.setText("类名");

		TableColumn method_column = new TableColumn(methodHsd_table, SWT.NONE);
		method_column.setWidth(135);
		method_column.setText("方法名");

		TableColumn mccabe_column = new TableColumn(methodHsd_table, SWT.NONE);
		mccabe_column.setWidth(64);
		mccabe_column.setText("长度");

		TableColumn tblclmnNewColumn = new TableColumn(methodHsd_table,
				SWT.NONE);
		tblclmnNewColumn.setWidth(69);
		tblclmnNewColumn.setText("容量");

		TableColumn tblclmnNewColumn_1 = new TableColumn(methodHsd_table,
				SWT.NONE);
		tblclmnNewColumn_1.setWidth(75);
		tblclmnNewColumn_1.setText("预测bug数");

		TableColumn tblclmnNewColumn_2 = new TableColumn(methodHsd_table,
				SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("工作量");
		// 查询数据库中需要的数据
		
		ResultSet rs = queryMethodHsdData.queryExactMethodHsdProjVersion(init.last()[0], init.last()[1]);
		try {
			while (rs.next()) {
				String packageName = rs.getString(5);
				String className = rs.getString(6);
				String methodName = rs.getString(7);
				String length = rs.getInt(12) + "";
				String volume = rs.getFloat(13) + "";
				String bugs = rs.getFloat(14) + "";
				String effort = rs.getFloat(15) + "";
				TableItem item = new TableItem(methodHsd_table, SWT.NONE);
				item.setText(new String[] { packageName, className, methodName,
						length, volume, bugs, effort });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

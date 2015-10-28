﻿package cn.seu.edu.complexityevaluator.ui.halsteadui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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

public class ClassHstdInfo extends Composite {
	private static Table classHsd_table;
	
	public static Table getClassHsd_table() {
		return classHsd_table;
	}

	private QueryProjInfoDao queryClassHsdData = new QueryProjInfoDaoImpl();
	// 初始化数据类
	private LastProjAndVer init = new LastProjAndVer();
	public ClassHstdInfo(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		// 重点关注模块报表
		Label label = new Label(this, SWT.NONE);
		label.setBounds(5, 10, 149, 17);
		label.setText("Volume超过阈值的类列表");
		// 添加滚动界面容器
		ScrolledComposite scrolledComposite = new ScrolledComposite(this,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(5, 33, 612, 357);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// 滚动容器里面添加容器，用于放置其他的控件
		Composite table_composite = new Composite(scrolledComposite, SWT.NONE);
		classHsd_table = new Table(table_composite, SWT.BORDER);
		classHsd_table.setBounds(0, 0, 608, 353);
		classHsd_table.setHeaderVisible(true);
		classHsd_table.setLinesVisible(true);
		// 设置滚动容器所依赖的容器
		scrolledComposite.setContent(table_composite);
		scrolledComposite.setMinSize(table_composite.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		// 包名列
		TableColumn package_column = new TableColumn(classHsd_table, SWT.NONE);
		package_column.setWidth(133);
		package_column.setText("包名");

		TableColumn class_column = new TableColumn(classHsd_table, SWT.NONE);
		class_column.setWidth(149);
		class_column.setText("类名");

		TableColumn mccabe_column = new TableColumn(classHsd_table, SWT.NONE);
		mccabe_column.setWidth(64);
		mccabe_column.setText("长度");

		TableColumn tblclmnNewColumn = new TableColumn(classHsd_table,
				SWT.NONE);
		tblclmnNewColumn.setWidth(69);
		tblclmnNewColumn.setText("容量");

		TableColumn tblclmnNewColumn_1 = new TableColumn(classHsd_table,
				SWT.NONE);
		tblclmnNewColumn_1.setWidth(75);
		tblclmnNewColumn_1.setText("预测bug数");

		TableColumn tblclmnNewColumn_2 = new TableColumn(classHsd_table,
				SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("工作量");

		// 查询数据库中容量大于1000的前25个目标
		ResultSet rs = queryClassHsdData.queryExactClassHsdProjVersion(init.last()[0],init.last()[1]);
		try {
			while (rs.next()) {
				String packageName = rs.getString(5);
				String className = rs.getString(6);
				String length = rs.getInt(11) + "";
				String volume = rs.getFloat(12) + "";
				String bugs = rs.getFloat(13) + "";
				String effort = rs.getFloat(14) + "";
				TableItem item = new TableItem(classHsd_table, SWT.NONE);
				item.setText(new String[] { packageName, className, length,
						volume, bugs, effort });
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

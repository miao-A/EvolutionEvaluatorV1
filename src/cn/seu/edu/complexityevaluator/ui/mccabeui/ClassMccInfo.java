﻿/* 
 * 日期 2015-5-4
 * 版本 1.0
 * 描述 类层次的界面数据展示
 * */

package cn.seu.edu.complexityevaluator.ui.mccabeui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

import cn.seu.edu.complexityevaluator.chart.classChart.mccChart.ClassMccPieChart;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.util.utilofui.LastProjAndVer;

public class ClassMccInfo extends Composite {
	private static Table classMcc_table;// 圈复杂度表格

	public static Table getClassMcc_table() {
		return classMcc_table;
	}

	private QueryProjInfoDao queryClassMccData = new QueryProjInfoDaoImpl();// 查询数据
	private LastProjAndVer init = new LastProjAndVer();// 初始化数据类
	private Label classMccInfo_label;//类层信息提示
	private ScrolledComposite high_scrolledComposite;//滚动容器
	private Composite high_composite;//容器
	private TableColumn colume_packName;//包名表头
	private TableColumn colume_className;//类名表头
	private TableColumn colume_mccabe;//圈复杂度表头
	private Label label;//图形分布提示

	private static ChartComposite classframe;
	public static ChartComposite getClassframe() {
		return classframe;
	}


	private JFreeChart chart;
	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public ClassMccInfo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		// 类层次圈复杂度提示
		classMccInfo_label = new Label(this, SWT.NONE);
		FormData fd_classMccInfo_label = new FormData();
		fd_classMccInfo_label.left = new FormAttachment(0, 10);
		fd_classMccInfo_label.top = new FormAttachment(0, 10);
		classMccInfo_label.setLayoutData(fd_classMccInfo_label);
		classMccInfo_label.setText("超过阈值的类列表");

		// 添加滚动容器
		high_scrolledComposite = new ScrolledComposite(this,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData fd_high_scrolledComposite = new FormData();
		fd_high_scrolledComposite.right = new FormAttachment(classMccInfo_label, 598);
		fd_high_scrolledComposite.top = new FormAttachment(classMccInfo_label, 6);
		fd_high_scrolledComposite.left = new FormAttachment(classMccInfo_label, 0, SWT.LEFT);
		high_scrolledComposite.setLayoutData(fd_high_scrolledComposite);
		high_scrolledComposite.setExpandHorizontal(true);
		high_scrolledComposite.setExpandVertical(true);
		// 容器中再添加容器
	    high_composite = new Composite(high_scrolledComposite,SWT.NONE);
		// 系统圈复杂度表
		classMcc_table = new Table(high_composite, SWT.FULL_SELECTION);
		classMcc_table.setLocation(0, 0);
		classMcc_table.setSize(594, 141);
		classMcc_table.setHeaderVisible(true);
		classMcc_table.setLinesVisible(true);

	    colume_packName = new TableColumn(classMcc_table,
				SWT.NONE);
		colume_packName.setWidth(224);
		colume_packName.setText("包名");

		colume_className = new TableColumn(classMcc_table,
				SWT.NONE);
		colume_className.setWidth(180);
		colume_className.setText("类名");

		colume_mccabe = new TableColumn(classMcc_table,
				SWT.NONE);
		colume_mccabe.setWidth(185);
		colume_mccabe.setText("圈复杂度");

		// 将两个容器整合
		high_scrolledComposite.setContent(high_composite);
		high_scrolledComposite.setMinSize(high_composite.computeSize(
				SWT.DEFAULT, SWT.DEFAULT));
		// 查询数据库中类级别圈复杂度需要的数据
		ResultSet rs = queryClassMccData.queryExactClassMccProjVersion(init.last()[0], init.last()[1]);
		try {
			while (rs.next()) {
				String packageName = rs.getString(5);
				String className = rs.getString(6);
				String mccabe = rs.getInt(7) + "";
				TableItem item = new TableItem(classMcc_table, SWT.NONE);
				item.setText(new String[] { packageName, className, mccabe });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 下面的分布图，阈值是50来画
		label = new Label(this, SWT.NONE);
		fd_high_scrolledComposite.bottom = new FormAttachment(label, -6);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 183);
		fd_label.left = new FormAttachment(classMccInfo_label, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		label.setText("类的圈复杂度分布图");
//		 //将饼图容器加进来
		chart = ClassMccPieChart.createChart(ClassMccPieChart.createDataset(init.last()[0],init.last()[1]));
		classframe = new ChartComposite(this,
				SWT.NONE, chart, true);
		FormData fd_classframe = new FormData();
		fd_classframe.bottom = new FormAttachment(label, 223, SWT.BOTTOM);
		fd_classframe.right = new FormAttachment(classMccInfo_label, 572);
		fd_classframe.top = new FormAttachment(label, 6);
		fd_classframe.left = new FormAttachment(classMccInfo_label, 0, SWT.LEFT);
		classframe.setLayoutData(fd_classframe);
		classframe.setBounds(0, 0, 175, 103);
		classframe.pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

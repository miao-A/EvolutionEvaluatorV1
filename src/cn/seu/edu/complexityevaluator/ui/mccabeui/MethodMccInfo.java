﻿/* 日期 2015-7-7
 * 描述 这个是单个版本的方法层圈复杂度的信息，里面有一个Table：显示圈复杂度较高的模块；还有一个复杂度分布饼图。
 * 
 * */
package cn.seu.edu.complexityevaluator.ui.mccabeui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.methodChart.mccChart.MethodMccPieChart;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDao;
import cn.seu.edu.complexityevaluator.dao.QueryProjInfoDaoImpl;
import cn.seu.edu.complexityevaluator.org.eclipse.wb.swt.SWTResourceManager;
import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;
import cn.seu.edu.complexityevaluator.util.utilofui.LastProjAndVer;

public class MethodMccInfo extends Composite {
	private static Table maccabe_table;

	public static Table getMaccabe_table() {
		return maccabe_table;
	}

	private QueryProjInfoDao queryLargeMccabeData = new QueryProjInfoDaoImpl();	
	private LastProjAndVer init = new LastProjAndVer();// 初始化数据类
    private Label label;//重点关注模块提示
    private ScrolledComposite scrolledComposite;//滚动容器
    private Composite table_composite;//表格
    private TableColumn column_packName;//包名表头
    private TableColumn column_className;//类名表头
    private TableColumn column_methodName;//方法名表头
    private TableColumn column_mccabe;//圈复杂度
    private Label  label_1;//复杂度分布图提示
    private JFreeChart chart;
    private static ChartComposite frame;

	public static ChartComposite getFrame() {
		return frame;
	}

	public static void setFrame(ChartComposite frame) {
		MethodMccInfo.frame = frame;
	}

	public MethodMccInfo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		// 重点关注模块报表
		label = new Label(this, SWT.NONE);
		FormData fd_classMccInfo_label = new FormData();
		fd_classMccInfo_label.left = new FormAttachment(0, 10);
		fd_classMccInfo_label.top = new FormAttachment(0, 10);
		label.setLayoutData(fd_classMccInfo_label);
		label.setText("超过阈值的方法列表");
		// 添加滚动界面容器
		scrolledComposite = new ScrolledComposite(this,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData fd_high_scrolledComposite = new FormData();
		fd_high_scrolledComposite.top = new FormAttachment(label, 6);
		fd_high_scrolledComposite.right = new FormAttachment(label, 637);
		fd_high_scrolledComposite.left = new FormAttachment(label, 0, SWT.LEFT);
		scrolledComposite.setLayoutData(fd_high_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// 滚动容器里面添加容器，用于放置其他的控件
		table_composite = new Composite(scrolledComposite, SWT.NONE);
		maccabe_table = new Table(table_composite, SWT.BORDER);
		maccabe_table.setBounds(0, 0, 633, 192);
		maccabe_table.setHeaderVisible(true);
		maccabe_table.setLinesVisible(true);
		// 设置滚动容器所依赖的容器
		scrolledComposite.setContent(table_composite);
		scrolledComposite.setMinSize(table_composite.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		// 包名列
		column_packName = new TableColumn(maccabe_table, SWT.NONE);
		column_packName.setWidth(131);
		column_packName.setText("包名");

		column_className = new TableColumn(maccabe_table, SWT.NONE);
		column_className.setWidth(199);
		column_className.setText("类名");

		column_methodName = new TableColumn(maccabe_table, SWT.NONE);
		column_methodName.setWidth(151);
		column_methodName.setText("方法名");

		column_mccabe = new TableColumn(maccabe_table, SWT.NONE);
		column_mccabe.setWidth(131);
		column_mccabe.setText("圈复杂度");

		label_1 = new Label(this, SWT.NONE);
		fd_high_scrolledComposite.bottom = new FormAttachment(100, -287);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(scrolledComposite, 6);
		fd_label.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label);
		label_1.setText("方法的圈复杂度分布图");
		// 查询数据库中需要的数据
		ResultSet rs = queryLargeMccabeData.queryExactMethodMccProjVersion(init.last()[0],init.last()[1]);
		try {
			while (rs.next()) {
				String packageName = rs.getString(5);
				String className = rs.getString(6);
				String methodName = rs.getString(7);
				String mccabe = rs.getInt(8) + "";
				TableItem item = new TableItem(maccabe_table, SWT.NONE);
				item.setText(new String[] { packageName, className, methodName,
						mccabe });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //将饼图容器加进来
		chart = MethodMccPieChart.createChart(MethodMccPieChart
				.createDataset(init.last()[0],init.last()[1]));
		frame = new ChartComposite(this,
				SWT.NONE, chart, true);
		FormData fd_frame = new FormData();
		fd_frame.top = new FormAttachment(label_1);
		fd_frame.right = new FormAttachment(label, 511);
		fd_frame.left = new FormAttachment(label, 0, SWT.LEFT);
		fd_frame.bottom = new FormAttachment(100, -26);
		frame.setLayoutData(fd_frame);
		frame.setBounds(0, 0, 175, 103);
		frame.pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

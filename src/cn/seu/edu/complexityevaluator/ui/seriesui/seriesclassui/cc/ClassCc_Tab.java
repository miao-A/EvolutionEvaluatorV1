﻿/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器包括 几个变更图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.cc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.classChart.mccChart.SeriesClassMccChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;
public class ClassCc_Tab extends TabFolder {
	private TabItem tb_classmcc;// 类圈复杂度
	private Composite classMccInfoComposite;
	private JFreeChart classmccchanged_chart;
	private static ChartComposite classmcc_frame;

	public static ChartComposite getClassmcc_frame() {
		return classmcc_frame;
	}

	private TabItem tb_change;// 变更信息
	private Composite mapComposite;

	public ClassCc_Tab(Composite parent, int style) {
		super(parent, style);
		// 2.类层圈复杂度变更图
		tb_classmcc = new TabItem(this, SWT.NONE);
		tb_classmcc.setText("类的圈复杂度变化");
		// 加入图形容器
		classMccInfoComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_classmcc.setControl(classMccInfoComposite);
		// mcc图形
		classmccchanged_chart = SeriesClassMccChart
				.createChart(SeriesClassMccChart.createDataset(SeriesMccComplexityInfo.combo_proj.getText(),SeriesMccComplexityInfo.combo_sversion.getText(),
						SeriesMccComplexityInfo.combo_eversion.getText()));
		classMccInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classmcc_frame = new ChartComposite(
				classMccInfoComposite, SWT.NONE, classmccchanged_chart, true);
		classmcc_frame.pack();
		// 1.变更信息
		tb_change = new TabItem(this, SWT.NONE);
		tb_change.setText("类编号映射表");
		// 加入表格
		mapComposite = new ClassCcTable(this, SWT.NONE);
		tb_change.setControl(mapComposite);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

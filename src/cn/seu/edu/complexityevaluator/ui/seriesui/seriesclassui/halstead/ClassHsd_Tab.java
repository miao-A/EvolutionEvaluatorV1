﻿/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器包括 几个变更图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.halstead;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassBugChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassDifChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassEffortChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassLengthChart;
import cn.seu.edu.complexityevaluator.chart.classChart.hsdChart.SeriesClassVolumeChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesHsdComplextyInfo;

public class ClassHsd_Tab extends TabFolder {
	private TabItem tb_change;// 变更信息
	private TabItem tb_classhsd;// 类层hasd
	private JFreeChart classhsdchanged_chart;
	private JFreeChart classlengthchanged_chart;
	private JFreeChart classbugchanged_chart;
	private JFreeChart classeffortchanged_chart;
	private JFreeChart classdifchanged_chart;
	private TabItem tb_bug;
	private TabItem tb_length;
	private TabItem tb_effort;
	private TabItem tb_dif;
	private Composite mapComposite;
	private static ChartComposite classhsd_frame;
	private static ChartComposite classlength_frame;
	private static ChartComposite classeffort_frame;
	private static ChartComposite classdif_frame;
	private static ChartComposite classbug_frame;
	public static ChartComposite getClasshsd_frame() {
		return classhsd_frame;
	}

	public static ChartComposite getClasslength_frame() {
		return classlength_frame;
	}

	public static ChartComposite getClasseffort_frame() {
		return classeffort_frame;
	}

	public static ChartComposite getClassdif_frame() {
		return classdif_frame;
	}

	public static ChartComposite getClassbug_frame() {
		return classbug_frame;
	}

	public ClassHsd_Tab(Composite parent, int style) {
		super(parent, style);

		// 3.类层volume变更图
		tb_classhsd = new TabItem(this, SWT.NONE);
		tb_classhsd.setText("volume变化图");
		final Composite classHsdInfoComposite = new Composite(this, SWT.NONE);
		tb_classhsd.setControl(classHsdInfoComposite);
		// volume图形
		classhsdchanged_chart = SeriesClassVolumeChart
				.createChart(SeriesClassVolumeChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		classHsdInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classhsd_frame = new ChartComposite(
				classHsdInfoComposite, SWT.NONE, classhsdchanged_chart, true);
//		tb_classhsd.setControl(classhsd_frame);
		classhsd_frame.pack();
		// 4.length
		tb_length = new TabItem(this, SWT.NONE);
		tb_length.setText("length变化图");
		final Composite classLengthComposite = new Composite(this, SWT.NONE);
		tb_length.setControl(classLengthComposite);
		// length图形
		classlengthchanged_chart = SeriesClassLengthChart
				.createChart(SeriesClassLengthChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		classLengthComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classlength_frame = new ChartComposite(
				classLengthComposite, SWT.NONE, classlengthchanged_chart, true);
		classlength_frame.pack();
		// 5.effort
		tb_effort = new TabItem(this, SWT.NONE);
		tb_effort.setText("effort变化图");
		final Composite classEffortComposite = new Composite(this, SWT.NONE);
		tb_effort.setControl(classEffortComposite);
		// effort图形
		classeffortchanged_chart = SeriesClassEffortChart
				.createChart(SeriesClassEffortChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		classEffortComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classeffort_frame = new ChartComposite(
				classEffortComposite, SWT.NONE, classeffortchanged_chart, true);
		classeffort_frame.pack();
		// 6.difficulty
		tb_dif = new TabItem(this, SWT.NONE);
		tb_dif.setText("difficulty变化图");
		final Composite classDifComposite = new Composite(this, SWT.NONE);
		tb_dif.setControl(classDifComposite);
		// dif图形
		classdifchanged_chart = SeriesClassDifChart
				.createChart(SeriesClassDifChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		classDifComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classdif_frame = new ChartComposite(classDifComposite,
				SWT.NONE, classdifchanged_chart, true);
		classdif_frame.pack();
		// 7.bug
		tb_bug = new TabItem(this, SWT.NONE);
		tb_bug.setText("bug变化图");
		final Composite classBugComposite = new Composite(this, SWT.NONE);
		tb_bug.setControl(classBugComposite);
		// bug图形
		classbugchanged_chart = SeriesClassBugChart
				.createChart(SeriesClassBugChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		classBugComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		classbug_frame = new ChartComposite(classBugComposite,
				SWT.NONE, classbugchanged_chart, true);
		classbug_frame.pack();
		// 1.变更信息
		tb_change = new TabItem(this, SWT.NONE);
		tb_change.setText("类编号映射表");
		// 加入表格
		mapComposite = new ClassHsdTable(this, SWT.NONE);
		tb_change.setControl(mapComposite);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

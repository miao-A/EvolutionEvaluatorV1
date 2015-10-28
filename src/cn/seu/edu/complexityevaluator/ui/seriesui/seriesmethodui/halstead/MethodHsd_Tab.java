﻿/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器包括 几个变更图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.halstead;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodBugChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodDifChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodEffortChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodLengthChart;
import cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart.SeriesMethodVolumeChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesHsdComplextyInfo;

public class MethodHsd_Tab extends TabFolder {

	private TabItem tb_methodchange;
	private TabItem tb_methodhsd;
	private JFreeChart methodHsdChanged_chart;
	private JFreeChart methodLengthChanged_chart;
	private JFreeChart methodEffortChanged_chart;
	private JFreeChart methodDifChanged_chart;
	private JFreeChart methodBugChanged_chart;
	private TabItem tb_length;
	private TabItem tb_dif;
	private TabItem tb_effort;
	private TabItem tb_bug;
	private Composite mapComposite;
	private static ChartComposite methodHsd_frame;
	private static ChartComposite methodLength_frame;
	private static ChartComposite methodEffort_frame;
	private static ChartComposite methodBug_frame;
	private static ChartComposite methodDif_frame;
	public static ChartComposite getMethodHsd_frame() {
		return methodHsd_frame;
	}

	public static ChartComposite getMethodLength_frame() {
		return methodLength_frame;
	}

	public static ChartComposite getMethodEffort_frame() {
		return methodEffort_frame;
	}

	public static ChartComposite getMethodBug_frame() {
		return methodBug_frame;
	}

	public static ChartComposite getMethodDif_frame() {
		return methodDif_frame;
	}

	public MethodHsd_Tab(Composite parent, int style) {
		super(parent, style);

		// 3.方法halstead复杂度
		tb_methodhsd = new TabItem(this, SWT.NONE);
		tb_methodhsd.setText("Volume变化图");
		final Composite methodHsdInfoComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_methodhsd.setControl(methodHsdInfoComposite);
		// 填充图形
		methodHsdChanged_chart = SeriesMethodVolumeChart
				.createChart(SeriesMethodVolumeChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		methodHsdInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		methodHsd_frame = new ChartComposite(
				methodHsdInfoComposite, SWT.NONE, methodHsdChanged_chart, true);
		methodHsd_frame.pack();
		// 4.length
		tb_length = new TabItem(this, SWT.NONE);
		tb_length.setText("length变化图");
		final Composite methodLengthComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_length.setControl(methodLengthComposite);
		// 填充图形
		methodLengthChanged_chart = SeriesMethodLengthChart
				.createChart(SeriesMethodLengthChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		methodLengthComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
	    methodLength_frame = new ChartComposite(
				methodLengthComposite, SWT.NONE, methodLengthChanged_chart,
				true);
		methodLength_frame.pack();
		// 5.effort
		tb_effort = new TabItem(this, SWT.NONE);
		tb_effort.setText("effort变化图");
		final Composite methodEffortComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_effort.setControl(methodEffortComposite);
		// 填充图形
		methodEffortChanged_chart = SeriesMethodEffortChart
				.createChart(SeriesMethodEffortChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		methodEffortComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		methodEffort_frame = new ChartComposite(
				methodEffortComposite, SWT.NONE, methodEffortChanged_chart,
				true);
		methodEffort_frame.pack();
		// 6.bug
		tb_bug = new TabItem(this, SWT.NONE);
		tb_bug.setText("预测bug变化图");
		final Composite methodBugComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_bug.setControl(methodBugComposite);
		// 填充图形
		methodBugChanged_chart = SeriesMethodBugChart
				.createChart(SeriesMethodBugChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		methodBugComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		methodBug_frame = new ChartComposite(methodBugComposite,
				SWT.NONE, methodBugChanged_chart, true);
		methodBug_frame.pack();
		// 7.difficulty变更
		tb_dif = new TabItem(this, SWT.NONE);
		tb_dif.setText("difficulty变化图");
		final Composite methodDifComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_dif.setControl(methodDifComposite);
		// 填充图形
		methodDifChanged_chart = SeriesMethodDifChart
				.createChart(SeriesMethodDifChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText(),SeriesHsdComplextyInfo.combo_sversion.getText(),
						SeriesHsdComplextyInfo.combo_eversion.getText()));
		methodDifComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		methodDif_frame = new ChartComposite(methodDifComposite,
				SWT.NONE, methodDifChanged_chart, true);
		methodDif_frame.pack();
		// 8.方法变更信息
		tb_methodchange = new TabItem(this, SWT.NONE);
		tb_methodchange.setText("方法名编号映射表");
		mapComposite = new MethodHsdTable(this, SWT.NONE);
		tb_methodchange.setControl(mapComposite);
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

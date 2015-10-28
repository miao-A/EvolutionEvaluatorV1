/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个Tab包括包个数，类个数，方法个数，圈复杂度变更，halstead变更几个图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriessysui.halstead;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesBugLineChart;
import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesDifLineChart;
import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesEffortLineChart;
import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesLengthLineChart;
import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesVolumeLineChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesHsdComplextyInfo;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;

public class SysHsd_Tab extends TabFolder {

	private TabItem volume_change;
	private TabItem length_change;
	private TabItem bugs_change;
	private TabItem effort_change;
	private TabItem dif_change;
	private JFreeChart chart_hsdsys;
	private JFreeChart chart_length;
	private JFreeChart chart_effort;
	private JFreeChart chart_bug;
	private JFreeChart chart_dif;
	public SysHsd_Tab(Composite parent, int style) {
		super(parent, style);
		
		volume_change = new TabItem(this, SWT.NONE);
		volume_change.setText("Volume变化图");
		
		length_change = new TabItem(this, SWT.NONE);
		length_change.setText("Length变化图");
		
		bugs_change = new TabItem(this, SWT.NONE);
		bugs_change.setText("Bug变化图");
		
		effort_change = new TabItem(this, SWT.NONE);
		effort_change.setText("effort变化图");
		
		dif_change = new TabItem(this, SWT.NONE);
		dif_change.setText("Difficulty变化图");

		//volume变更图形
		final Composite sysHsdInfoComposite = new Composite(this, SWT.NONE);
		volume_change.setControl(sysHsdInfoComposite);
		chart_hsdsys = SeriesVolumeLineChart
				.createChart(SeriesVolumeLineChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText()));
		sysHsdInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite frame_hsd = new ChartComposite(sysHsdInfoComposite, SWT.NONE,
				chart_hsdsys, true);
		frame_hsd.pack();
		//length变更图形
		final Composite lengthComposite = new Composite(this, SWT.NONE);
		length_change.setControl(lengthComposite);
		chart_length = SeriesLengthLineChart
				.createChart(SeriesLengthLineChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText()));
		lengthComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite frame_length = new ChartComposite(lengthComposite, SWT.NONE,
				chart_length, true);
		frame_length.pack();
		//effort变更图形
		final Composite effortComposite = new Composite(this, SWT.NONE);
		effort_change.setControl(effortComposite);
		chart_effort = SeriesEffortLineChart
				.createChart(SeriesEffortLineChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText()));
		effortComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite frame_effort = new ChartComposite(effortComposite, SWT.NONE,
				chart_effort, true);
		frame_effort.pack();
		//bug变更图形
		final Composite bugComposite = new Composite(this, SWT.NONE);
		bugs_change.setControl(bugComposite);
		chart_bug = SeriesBugLineChart
				.createChart(SeriesBugLineChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText()));
		bugComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite frame_bug = new ChartComposite(bugComposite, SWT.NONE,
				chart_bug, true);
		frame_bug.pack();
		//dif变更图形
		final Composite difComposite = new Composite(this, SWT.NONE);
		dif_change.setControl(difComposite);
		chart_dif = SeriesDifLineChart
				.createChart(SeriesDifLineChart.createDataset(SeriesHsdComplextyInfo.combo_proj.getText()));
		difComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite frame_dif = new ChartComposite(difComposite, SWT.NONE,
				chart_dif, true);
		frame_dif.pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

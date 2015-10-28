/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个Tab包括包个数，类个数，方法个数，圈复杂度变更，halstead变更几个图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriessysui.cc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.sysChart.SeriesMccComplexyLineChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;

public class SysCc_Tab extends TabFolder {
	private JFreeChart sysMccChanged_chart;
	private TabItem mcc_change;
	public SysCc_Tab(Composite parent, int style) {
		super(parent, style);
		mcc_change = new TabItem(this, SWT.NONE);
		mcc_change.setText("圈复杂度变化图");
		final Composite sysMccInfoComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		mcc_change.setControl(sysMccInfoComposite);
		// 填充图形
		sysMccChanged_chart = SeriesMccComplexyLineChart
				.createChart(SeriesMccComplexyLineChart.createDataset(SeriesMccComplexityInfo.combo_proj.getText()));
		sysMccInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		ChartComposite methodMcc_frame = new ChartComposite(
				sysMccInfoComposite, SWT.NONE, sysMccChanged_chart, true);
		methodMcc_frame.pack();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

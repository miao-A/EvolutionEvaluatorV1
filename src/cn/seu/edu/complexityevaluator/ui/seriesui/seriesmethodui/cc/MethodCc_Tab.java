/* 作者 何磊
 * 日期 2015-6-22
 * 版本 1.0
 * 描述：这个容器包括 几个变更图形
 * */
package cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.cc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.chart.methodChart.mccChart.SeriesMethodMccChart;
import cn.seu.edu.complexityevaluator.ui.seriesui.SeriesMccComplexityInfo;

public class MethodCc_Tab extends TabFolder {
	private TabItem tb_methodmcc;
	private JFreeChart methodMccChanged_chart;
	private Composite mapComposite;
	private TabItem tb_methodchange;
	private static ChartComposite methodMcc_frame;
	public static ChartComposite getMethodMcc_frame() {
		return methodMcc_frame;
	}

	public MethodCc_Tab(Composite parent, int style) {
		super(parent, style);

		// 2.方法圈复杂度图
		tb_methodmcc = new TabItem(this, SWT.NONE);
		tb_methodmcc.setText("方法的圈复杂度变化");
		final Composite methodMccInfoComposite = new Composite(this, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		tb_methodmcc.setControl(methodMccInfoComposite);
		// 填充图形
		methodMccChanged_chart = SeriesMethodMccChart
				.createChart(SeriesMethodMccChart.createDataset(SeriesMccComplexityInfo.combo_proj.getText(),SeriesMccComplexityInfo.combo_sversion.getText()
						,SeriesMccComplexityInfo.combo_eversion.getText()));
		methodMccInfoComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		methodMcc_frame = new ChartComposite(
				methodMccInfoComposite, SWT.NONE, methodMccChanged_chart, true);
		methodMcc_frame.pack();
		// 8.方法变更信息
		tb_methodchange = new TabItem(this, SWT.NONE);
		tb_methodchange.setText("方法名编号映射表");
		mapComposite = new MethodCcTable(this, SWT.NONE);
		tb_methodchange.setControl(mapComposite);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

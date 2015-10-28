/* 作者 何磊
 * 日期 2015-6-8
 * 描述 系统层复杂度的折线图，默认是查询当前选择的项目的所有版本的的复杂度
 * 版本 2.0
 * */
package cn.seu.edu.complexityevaluator.chart.sysChart;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.seu.edu.complexityevaluator.util.MinDataSet;
import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class SeriesMccComplexyLineChart {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private static QueryAndDeleteUtil queryClassMccData = new QueryAndDeleteUtil();
	private static MinDataSet min=new MinDataSet();
	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(CategoryDataset categoryDataset) {

		JFreeChart chart = ChartFactory.createLineChart("", // title
				"版本号", // x-axis label
				"复杂度", // y-axis label
				categoryDataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setRangeGridlinePaint(Color.white);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		//设置x轴数据角度
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		//设置Y轴起始数据为数据集中最小值的0.8倍,如果数据库中有数据的话
		if(categoryDataset.getRowCount()!=0){
		ValueAxis rangeAxis = categoryplot.getRangeAxis();
		rangeAxis.setLowerBound((Double) min.min(categoryDataset)*0.8);
		}
		//设置X轴上数据字体
		domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setItemMargin(0);
		//设置折线图上的点显示对应数据
		lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	public static CategoryDataset createDataset(String projName) {

		String series1 = "cc";// 折线的含义或者说类型
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //查询所有圈复杂度数据
		String allsql = "select * from t_systemmccabe where projectName ='"+projName+"'";
		ResultSet rs = queryClassMccData.queryInfo(allsql);
		try {
			while (rs.next()) {
					String proj = rs.getString(2) + rs.getString(3);
					int cc = rs.getInt(4);
					dataset.addValue(cc, series1, proj);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}
}

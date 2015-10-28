﻿/* 作者 何磊
 * 日期 2015-6-23
 * 描述 方法层次halstead volume变化图，目前没有将选中项目的触发效果结合在一起，下个版本完善
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class SeriesMethodVolumeChart {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private static QueryAndDeleteUtil queryClassHsdData = new QueryAndDeleteUtil();
	private static Map<Integer, String> methodVolumeMapping=new HashMap<Integer, String>();
	public static Map<Integer, String> getMethodVolumeMapping() {
		return methodVolumeMapping;
	}

	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(CategoryDataset categoryDataset) {

		JFreeChart chart = ChartFactory.createBarChart("", // title
				"方法名编号", // x-axis label
				"Volume", // y-axis label
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
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 8));
		BarRenderer renderer = (BarRenderer) categoryplot
				.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setItemMargin(0);
		domainAxis.setMaximumCategoryLabelLines(8);
		//设置数值在图形上显示
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	public static CategoryDataset createDataset(String proj,String sVersion,String eVersion) {

		String series1 = sVersion;// 折线的含义或者说类型
		String series2 = eVersion;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String allsql = "select * from t_methodhalstead  where projectName='"+proj+"'";
		ResultSet rs = queryClassHsdData.queryInfo(allsql);
		// 用来存贮前一个版本的方法层数据，key=方法名，value=复杂度值
		Map<String, Float> map = new HashMap<String, Float>();
		// 用来存储下一个相邻版本的数据
		Map<String, Float> nextMap = new HashMap<String, Float>();
		try {
			while (rs.next()) {
				String version = rs.getString(3);
				if (version.equals(sVersion)) {
					String path = rs.getString(4)+"|" + rs.getString(5)+"|"
							+ rs.getString(6)+"|"+ rs.getString(7);
					Float volume = rs.getFloat(13);
					map.put(path, volume);
				} else if (version.equals(eVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6)+"|" + rs.getString(7);
					//String id = rs.getInt(1) + "";
					Float volume = rs.getFloat(13);
					nextMap.put(path, volume);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 对上面两个Map进行处理，拿出里面共同存在的key值，且value不相等的数据
		int count=0;
		Iterator<Entry<String, Float>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Float> entry = it.next();
			String path = entry.getKey();
			float value = entry.getValue();
			//两个版本都有的的方法，且发生变化的
			if (nextMap.containsKey(path)) {
				float nextValue = nextMap.get(path);
				if (nextValue != value) {
					count++;
					dataset.addValue(value,series1,String.valueOf(count));
					dataset.addValue(nextValue,series2,String.valueOf(count));
					methodVolumeMapping.put( count,path);
				}
			}
		}
			//nextMap新增加的方法
			for(String p:nextMap.keySet()){
				if(!map.containsKey(p)){
					count++;
					dataset.addValue(nextMap.get(p), series2, String.valueOf(count));
					methodVolumeMapping.put(count,p);
				}
			}
			//nextMap删除的方法
			for(String p:map.keySet()){
				if(!nextMap.containsKey(p)){
					count++;
					dataset.addValue(map.get(p), series1,String.valueOf(count));
					methodVolumeMapping.put(count,p);
				}
			}
		return dataset;
	}
}

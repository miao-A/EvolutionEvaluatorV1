﻿/* 作者 何磊
 * 日期 2015-5-5
 * 描述 类层次圈复杂度的折线图
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.chart.methodChart.mccChart;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.seu.edu.complexityevaluator.util.QueryAndDeleteUtil;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class SeriesMethodMccChart {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private static QueryAndDeleteUtil queryClassMccData = new QueryAndDeleteUtil();
	private static Map<Integer, String> methodMccMapping=new HashMap<Integer, String>();
	public static Map<Integer, String> getMethodMccMapping() {
		return methodMccMapping;
	}

	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(CategoryDataset categoryDataset) {

		JFreeChart chart = ChartFactory.createBarChart("", // title
				"方法名编号", // x-axis label
				"圈复杂度", // y-axis label
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
		domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 8));
		BarRenderer renderer = (BarRenderer) categoryplot
				.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setItemMargin(0);//设置每个下标间柱状图的距离
		//设置数值显示
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
		// dataset.addValue(1.0D, series1, type1);

		String allsql = "select * from t_methodmccabe where projectName='"+proj+"'";
		ResultSet rs = queryClassMccData.queryInfo(allsql);
		//用来存贮前一个版本的数据
		Map<String, Integer> beforemap=new HashMap<String,Integer>();
		//用来存当前版本的方法层数据，key=方法名，value=复杂度值
		Map<String, Integer> map=new HashMap<String,Integer>();
		//用来存储下一个相邻版本的数据
		Map<String, Integer> nextMap=new HashMap<String,Integer>();
		//用来存贮上面两个Map中共同存在的key值，且value不相等的数据
		//拿到相邻版本数据
		try {
			while (rs.next()) {
				String version = rs.getString(3);
				if (version.equals(sVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6)+"|"+rs.getString(7);
					int cc = rs.getInt(8);
					map.put(path, cc);
				} else if (version.equals(eVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6)+"|"+rs.getString(7);
					int cc = rs.getInt(8);
					nextMap.put(path, cc);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 对上面两个Map进行处理，拿出里面共同存在的key值，且value不相等的数据
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		int count=0;
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			String key = entry.getKey();
			float value = entry.getValue();
			// 两个版本都有的的方法，且发生变化的
			if (nextMap.containsKey(key)) {
				float nextValue = nextMap.get(key);
				if (nextValue != value) {
					//只显示方法名
					count++;
					dataset.addValue(value, series1, count+"");
					dataset.addValue(nextValue, series2, count+"");
					methodMccMapping.put(count,key);
				}
			}
		}
			// nextMap新增加的方法
			for (String s : nextMap.keySet()) {
				if (!map.containsKey(s)) {
					//也是只显示方法名
					count++;
					dataset.addValue(nextMap.get(s), series2, count+"");
					methodMccMapping.put(count,s);
				}
			}
			// nextMap删除的方法
			for (String s : map.keySet()) {
				if (!nextMap.containsKey(s)) {
					//只显示方法名
					count++;
					dataset.addValue(map.get(s), series1, count+"");
					methodMccMapping.put(count,s);
				}
			}
		return dataset;
        }
}

package cn.seu.edu.complexityevaluator.chart.methodChart.hsdChart;

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

public class SeriesMethodOperatorChanged {
	private static QueryAndDeleteUtil queryClassHsdData = new QueryAndDeleteUtil();
	private static Map<Integer, String> methodLengthMapping=new HashMap<Integer, String>();
	public static Map<Integer, String> getMethodLengthMapping() {
		return methodLengthMapping;
	}

	public static JFreeChart createChart(CategoryDataset categoryDataset) {

		JFreeChart chart = ChartFactory.createBarChart("", // title
				"方法名编号", // x-axis label
				"operator", // y-axis label
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
		// 设置X轴上数据字体
		domainAxis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
		BarRenderer renderer = (BarRenderer) categoryplot
				.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setItemMargin(0);
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
		// dataset.addValue(1.0D, series1, type1);

		String allsql = "select * from t_methodhalstead  where projectName='"+proj+"'";
		ResultSet rs = queryClassHsdData.queryInfo(allsql);

		// 用来存贮前一个版本的方法层数据，key=方法名，value=复杂度值
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 用来存储下一个相邻版本的数据
		Map<String, Integer> nextMap = new HashMap<String, Integer>();
		try {
			while (rs.next()) {
				String version = rs.getString(3);
				if (version.equals(sVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6)+"|"+rs.getString(7);
				    //String id = rs.getInt(1) + "";
					int length = rs.getInt(12);
					map.put(path, length);
				} else if (version.equals(eVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6)+"|"+rs.getString(7);
					int length = rs.getInt(12);
					nextMap.put(path, length);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 对上面两个Map进行处理，拿出里面共同存在的key值，且value不相等的数据
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		int count=0;//增加全局变量，统计图形个数
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			String path = entry.getKey();
			float value = entry.getValue();
			//两个版本都有的的方法，且发生变化的
			if (nextMap.containsKey(path)) {
				float nextValue = nextMap.get(path);
				if (nextValue != value) {
					count++;
					dataset.addValue(value, series1,count+"");
					dataset.addValue(nextValue, series2,count+"");
					methodLengthMapping.put( count,path);
				}
			}
		}
			//nextMap新增加的方法
			for(String p:nextMap.keySet()){
				if(!map.containsKey(p)){
					count++;
					dataset.addValue(nextMap.get(p), series2, count+"");
					methodLengthMapping.put(count,p);
				}
			}
			//nextMap删除的方法
			for(String p:map.keySet()){
				if(!nextMap.containsKey(p)){
					count++;
					dataset.addValue(map.get(p), series1, count+"");
					methodLengthMapping.put(count,p);
				}
			}
		return dataset;
	}

}

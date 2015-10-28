/* 作者 何磊
 * 日期 2015-6-23
 * 描述 类层次圈复杂度的折线图
 * 版本 1.0
 * */
package cn.seu.edu.complexityevaluator.chart.classChart.hsdChart;
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
public class SeriesClassDifChart {

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private static QueryAndDeleteUtil queryClassHsdData = new QueryAndDeleteUtil();
	private static Map<Integer, String> classDifMapping=new HashMap<Integer, String>();
	
	public static Map<Integer, String> getClassDifMapping() {
		return classDifMapping;
	}

	@SuppressWarnings("deprecation")
	public static JFreeChart createChart(CategoryDataset categoryDataset) {

		JFreeChart chart = ChartFactory.createBarChart("", // title
				"类名编号", // x-axis label
				"Difficulty", // y-axis label
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
//		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
//				.createUpRotationLabelPositions(Math.PI / 2.0));
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

		String allsql = "select * from t_classhalstead where projectName='"+proj+"' ";
		ResultSet rs = queryClassHsdData.queryInfo(allsql);
		// 用来存贮前一个版本的方法层数据，key=方法名，value=复杂度值
		Map<String, Float> map = new HashMap<String, Float>();
		// 用来存储下一个相邻版本的数据
		Map<String, Float> nextMap = new HashMap<String, Float>();
		try {
			while (rs.next()) {
				String version = rs.getString(3);
				if (version.equals(sVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6);
				    //String id = rs.getInt(1) + "";
					Float dif = rs.getFloat(15);
					map.put(path, dif);
				} else if (version.equals(eVersion)) {
					String path = rs.getString(4) +"|"+ rs.getString(5)+"|"
							+ rs.getString(6);
					//String id = rs.getInt(1) + "";
					Float dif = rs.getFloat(15);
					nextMap.put(path, dif);
					// dataset.addValue(cc, series3, path);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(map.size()+" "+nextMap.size());
		// 对上面两个Map进行处理，拿出里面共同存在的key值，且value不相等的数据
		Iterator<Entry<String, Float>> it = map.entrySet().iterator();
		int count=0;//增加全局变量，统计图形个数
		while (it.hasNext()) {
			Map.Entry<String, Float> entry = it.next();
			String path = entry.getKey();
			float value = entry.getValue();
			//两个版本都有的的方法，且发生变化的
			if (nextMap.containsKey(path)) {
				float nextValue = nextMap.get(path);
				if (nextValue != value) {
					count++;
//					dataset.addValue(value, series1,key.split("\\|")[3]);
//					dataset.addValue(nextValue, series2,key.split("\\|")[3]);
					dataset.addValue(value, series1,count+"");
					dataset.addValue(nextValue, series2,count+"");
					classDifMapping.put(count,path);
				}
			}
		}
			//nextMap新增加的方法
			for(String p:nextMap.keySet()){
				if(!map.containsKey(p)){
					count++;
					dataset.addValue(nextMap.get(p), series2, count+"");
					classDifMapping.put(count,p);
				}
			}
			//nextMap删除的方法
			for(String p:map.keySet()){
				if(!nextMap.containsKey(p)){
					count++;
					dataset.addValue(map.get(p), series1, count+"");
					classDifMapping.put(count,p);
				}
			}
		return dataset;
	}
}

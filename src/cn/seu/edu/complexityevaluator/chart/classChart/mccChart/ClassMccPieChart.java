/* 
 * 日期 2015-4-22
 * 版本 1.0
 * 描述 描绘类层的圈复杂度的饼图
 * 问题 需要简化数据库查询操作
 * */

package cn.seu.edu.complexityevaluator.chart.classChart.mccChart;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import cn.seu.edu.complexityevaluator.util.DBConnection;

/**
 * This demo shows a time series chart that has multiple range axes.
 */
public class ClassMccPieChart {

	/**
	 * Creates a sample dataset.
	 * 
	 * @return A sample dataset.
	 * @throws Exception
	 */
	private static int[] mccabeInfo = new int[2];

	// 到数据库将数据查询到需要的数据方法
	private int[] queryClassMccInfo(String projName,String version) {
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 建立数据库连接
		DBConnection dbConnection = new DBConnection();
		Connection conn = dbConnection.conn();
		// 创建statement
		try {
			PreparedStatement psS = conn
					.prepareStatement("select count(*) from t_classmccabe where mccabe>50 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ version +"'");
			PreparedStatement psT = conn
					.prepareStatement("select count(*) from t_classmccabe where mccabe<=50 and projectName='"
				+ projName
				+ "' and VersionID='"
				+ version+"'");
			
			ResultSet rsS = psS.executeQuery();
			ResultSet rsT = psT.executeQuery();
			while (rsT.next()) {
				mccabeInfo[0] = rsT.getInt(1);
			}
			while (rsS.next()) {
				mccabeInfo[1] = rsS.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(mccabeInfo);
		return mccabeInfo;
	}

	public static PieDataset createDataset(String projName,String version) {
		ClassMccPieChart mmpc = new ClassMccPieChart();
		mmpc.queryClassMccInfo(projName,version);
		// 创建饼图
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("cc<=50", mccabeInfo[0]);
		dataset.setValue("cc>50", mccabeInfo[1]);
		return dataset;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A chart.
	 */
	public static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("", // chart
															// title
				dataset, // data
				true, // include legend
				true, false);
		chart.setBackgroundPaint(Color.white);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setSectionPaint("cc<50", new Color(124, 181, 42));
		plot.setSectionPaint("cc>=50", new Color(173, 100, 173));
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		return chart;

	}
}

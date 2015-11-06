package cn.edu.seu.integrabilityevaluator.parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.DataUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;  
import java.awt.Font;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.text.DecimalFormat;  
import java.text.NumberFormat;  
  
import java.util.ArrayList;
import java.util.Vector;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartUtilities;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.CategoryLabelPositions;  
import org.jfree.chart.axis.NumberAxis;  
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;  
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PiePlot3D;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.renderer.category.BarRenderer;  
import org.jfree.chart.renderer.category.LineAndShapeRenderer;  
import org.jfree.chart.renderer.category.StackedBarRenderer;  
import org.jfree.chart.title.TextTitle;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.general.DatasetUtilities;  
import org.jfree.data.general.DefaultPieDataset;  
import org.jfree.data.general.PieDataset; 


public class CreateChartServiceImpl {
	
	private static final String CHART_PATH = "D:/test/"; 
	private String[] rowKeys;
	private String[] columnKeys;
	

	public CreateChartServiceImpl(String[] rowkeys,String[] columKeys,ArrayList<Double> arraylist){

        String[] rowKeys = {"梨子", "葡萄"};  
       /* String[] columnKeys = {"D:/ProjectEOfHW/heritrix0.2.0","D:/ProjectEOfHW/heritrix0.4.0",
				"D:/ProjectEOfHW/heritrix0.6.0","D:/ProjectEOfHW/heritrix0.8.0",
				"D:/ProjectEOfHW/heritrix1.0.0","D:/ProjectEOfHW/heritrix1.10.0",
				"D:/ProjectEOfHW/heritrix1.12.0","D:/ProjectEOfHW/heritrix1.14.4",
				"D:/ProjectEOfHW/heritrix-3.0.0","D:/ProjectEOfHW/heritrix-3.1.0"};  
        */
        String[] columnKeys = {"D:/ProjectEOfHW/junit3.4","D:/ProjectEOfHW/junit3.5","D:/ProjectEOfHW/junit3.6",
			"D:/ProjectEOfHW/junit3.7","D:/ProjectEOfHW/junit3.8","D:/ProjectEOfHW/junit3.9","D:/ProjectEOfHW/junit4.0",
			"D:/ProjectEOfHW/junit4.1","D:/ProjectEOfHW/junit4.2","D:/ProjectEOfHW/junit4.3","D:/ProjectEOfHW/junit4.4",
			"D:/ProjectEOfHW/junit4.5","D:/ProjectEOfHW/junit4.6","D:/ProjectEOfHW/junit4.7","D:/ProjectEOfHW/junit4.8",
			"D:/ProjectEOfHW/junit4.9","D:/ProjectEOfHW/junit4.10","D:/ProjectEOfHW/junit4.11"};
        
		double[][] data = new double[2][arraylist.size()];
		for (int i = 0; i < arraylist.size(); i++) {
			data[0][i]=arraylist.get(i);
			data[1][i]=arraylist.get(i);
		}
		

		
        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);  
        createTimeXYChar("JUnit", "x轴", "y轴", dataset, "lineAndShap.png");  
		
	}
	 /** 
     * 生成折线图 
     */  
 /*   public void makeLineAndShapeChart() {  
        double[][] data = new double[][]{  
            {672, 766, 223, 540, 126},  
            {325, 521, 210, 340, 106},  
            {332, 256, 523, 240, 526}  
        };  
        String[] rowKeys = {"abc", "梨子", "葡萄"};  
        String[] columnKeys = {"北京", "上海", "广州", "成都", "深圳"};  
        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);  
        createTimeXYChar("折线图", "x轴", "y轴", dataset, "lineAndShap.png");  
    }  
  */
	
	 public CategoryDataset getBarData(double[][] data, String[] rowKeys,  
	            String[] columnKeys) { 

	        return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);  
	  
	    }  
	 
	 /** 
	     * 判断文件夹是否存在，如果不存在则新建 
	     * @param chartPath 
	     */  
	    private void isChartPathExist(String chartPath) {  
	        File file = new File(chartPath);  
	        if (!file.exists()) {  
	            file.mkdirs();  
	        // log.info("CHART_PATH="+CHART_PATH+"create.");  
	        }  
	    }  
	
	 /** 
     * 折线图 
     *  
     * @param chartTitle 
     * @param x 
     * @param y 
     * @param xyDataset 
     * @param charName 
     * @return 
     */      public String createTimeXYChar(String chartTitle, String x, String y,  
            CategoryDataset xyDataset, String charName) {  
  
        JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,  
                xyDataset, PlotOrientation.VERTICAL, true, true, false);  
  
        chart.setTextAntiAlias(false);  
        chart.setBackgroundPaint(Color.WHITE);  
        // 设置图标题的字体重新设置title  
        Font font = new Font("宋体", Font.BOLD, 25);  
        TextTitle title = new TextTitle(chartTitle);  
        title.setFont(font);  
        chart.setTitle(title);  
        // 设置面板字体  
        Font labelFont = new Font("宋体", Font.TRUETYPE_FONT, 12);  
  
        
        chart.setBackgroundPaint(Color.WHITE);  
  
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();  
        // x轴 // 分类轴网格是否可见  
        categoryplot.setDomainGridlinesVisible(true);  
        // y轴 //数据轴网格是否可见  
        categoryplot.setRangeGridlinesVisible(true);  
  
        categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩  
  
        categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩  
  
        categoryplot.setBackgroundPaint(Color.lightGray);  
  
        // 设置轴和面板之间的距离  
        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));  
  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();  
  
        domainAxis.setLabelFont(labelFont);// 轴标题  
  
        domainAxis.setTickLabelFont(labelFont);// 轴数值  
        
        
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的  
        // Lable  
        // 45度倾斜  
        // 设置距离图片左端距离  

        domainAxis.setLowerMargin(0.0);  
        // 设置距离图片右端距离  
        domainAxis.setUpperMargin(0.0);  
  
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
  //      numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  //整数显示

        numberaxis.setAutoRangeIncludesZero(false);
  
     
        // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！  
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();  
  
        lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见  
  
        lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见  
  
        // 显示折点数据  
        // lineandshaperenderer.setBaseItemLabelGenerator(new  
        // StandardCategoryItemLabelGenerator());  
        // lineandshaperenderer.setBaseItemLabelsVisible(true);  
  
        FileOutputStream fos_jpg = null;  
        try {  
            isChartPathExist(CHART_PATH);  
            String chartName = CHART_PATH + charName;  
            fos_jpg = new FileOutputStream(chartName);  
  
            // 将报表保存为png文件  
            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 510);  
  
            return chartName;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {  
                fos_jpg.close();  
                System.out.println("create time-createTimeXYChar.");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
	
}

package cn.edu.seu.integrabilityevaluator.chart;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.osgi.resource.Capability;

public abstract class LineChart{
	
	private DefaultCategoryDataset dataset = null;
	private String title;
	private JFreeChart chart = null;
	
	public LineChart() {
		// TODO Auto-generated constructor stub
		dataset = new DefaultCategoryDataset();
		title = "";
	}
	
	public LineChart(String title){
		dataset = new DefaultCategoryDataset();
		this.title = title;
		
	}
	
	public void createChart() throws IOException{ 
	    
        CategoryDataset dataset = getDataSet();       
        
        chart = ChartFactory.createLineChart( 
                           title, // 图表标题
                           "",//"项目版本（包数）", // 目录轴的显示标签
                           "%", // 数值轴的显示标签
                            dataset, // 数据集
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                            true,  // 
                            false, // 是否生成工具
                            false  // 是否生成 URL 链接
                            );
        //中文乱码
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        categoryplot.setRenderer(new LineAndShapeRenderer());
        
        //LineAndShapeRenderer renderer = (LineAndShapeRenderer) categoryplot.getRenderer();
        /*renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);*/
        
        
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));  
       
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 8));

        
        
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        numberaxis.setVerticalTickLabels(false);
       
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
        
        /*// 创建文件输出流
        File fos_jpg = new File("E://"+string+".jpg ");
        // 输出到哪个输出流
        ChartUtilities.saveChartAsJPEG(fos_jpg, chart, // 统计图表对象
                      700, // 宽
                      500 // 高
                      );
        */                  
	   
	} 

	public JFreeChart getChart(){
		return chart;
	}
	
	public abstract void creatDataSet();

    /** 
    * 获取一个演示用的简单数据集对象
    * @return 
    */ 

	public void setDataSet(LinkedHashMap<String, HashMap<String, Double>> dataMap){
//		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Iterator<Entry<String, HashMap<String, Double>>> it =dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, Double>> entry1 = it.next();
			
			for (Map.Entry<String, Double> entry2 :entry1.getValue().entrySet() ) {			
				//System.out.println("key= " + entry2.getKey() + " and value= " + entry2.getValue());
				dataset.addValue(entry2.getValue(), entry2.getKey(), entry1.getKey());
			}		
		}	
		
		for (int i = 0; i < dataset.getRowCount(); i++) {
			Comparable rowKey = dataset.getRowKey(i);
			boolean flag1 = true;//是否都为1
			boolean flag0 = true;//是否都为0
			for (int j = 0; j < dataset.getColumnCount(); j++) {
				Number number = dataset.getValue(rowKey, dataset.getColumnKey(j));
				if (number != null) {
					if(number.doubleValue() != 100){
						flag1 = false;
					}
					if(number.doubleValue() != 0){
						flag0 = false;
					}
				}				
			}
			if(flag1||flag0){				
				dataset.removeRow(i);
				i--;
			}			
		}
	}
	
    private  CategoryDataset getDataSet() { 
        return dataset; 
    } 
}

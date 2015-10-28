package cn.seu.edu.integrabilityevaluator.chart;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ReturnStatement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.seu.edu.integrabilityevaluator.dbconnect.ExtensibilityInfoConnector;
import cn.seu.edu.integrabilityevaluator.dbconnect.ProjectConnector;

public abstract class BarChart {
	private DefaultCategoryDataset dataset = null;
	private String title;
	
	public BarChart(){
		dataset = new DefaultCategoryDataset();
		title = "指示图";
	}
	
	public BarChart(String title){
		dataset = new DefaultCategoryDataset();
		this.title = title;
		
	}
	
	public JFreeChart createChart() throws IOException{ 
	    
        CategoryDataset dataset = getDataSet(); 
        JFreeChart chart = ChartFactory.createBarChart( 
                           title, // 图表标题
                           "包名", // 目录轴的显示标签
                           "比例%", // 数值轴的显示标签
                            dataset, // 数据集
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                            true,  // 是否显示图例(对于简单的柱状图必须是 false)
                            false, // 是否生成工具
                            false  // 是否生成 URL 链接
                            );
        //中文乱码
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 16));  
       
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 8));

        
        
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        numberaxis.setVerticalTickLabels(false);
       
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	                       
	   return chart;
	} 

	
	public abstract void creatDataSet(String projectName);

    /** 
    * 获取一个演示用的简单数据集对象
    * @return 
    */ 

	public void setDataSet(LinkedHashMap<String, HashMap<String, Double>> dataMap){		
		Iterator<Entry<String, HashMap<String, Double>>> it = dataMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, Double>> entry1 = it.next();
			
			for (Map.Entry<String, Double> entry2 :entry1.getValue().entrySet() ) {
				System.out.println("key= " + entry1.getKey() + " and value= " + entry2.getKey()+entry2.getValue());
				dataset.addValue(entry2.getValue(), entry1.getKey(), entry2.getKey());
			}
		
		}
		
	}

	
    private  CategoryDataset getDataSet() { 
       /* DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        dataset.addValue(10, "", "苹果"); 
        dataset.addValue(20, "", "梨子"); 
        dataset.addValue(30, "", "葡萄"); 
        dataset.addValue(40, "", "香蕉"); 
        dataset.addValue(50, "", "荔枝"); */
        return dataset; 
    } 

   
} 


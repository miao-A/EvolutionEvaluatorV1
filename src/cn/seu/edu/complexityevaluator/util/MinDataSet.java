﻿/* 作者 何磊
 * 日期 2015-7-1
 * 描述 这个类是用来查找画图的时候数据集中最小的数据
 * */
package cn.seu.edu.complexityevaluator.util;

import org.jfree.data.category.CategoryDataset;

public class MinDataSet {
	public double min(CategoryDataset categoryDataset){
		int num=categoryDataset.getColumnCount();
		double min=(Double) categoryDataset.getValue(0, 0);
		for(int i=0;i<num;i++){
			double j=(Double) categoryDataset.getValue(0, i);
			if(j<min){
				min=j;
			}
		}
		return min;
	}
}

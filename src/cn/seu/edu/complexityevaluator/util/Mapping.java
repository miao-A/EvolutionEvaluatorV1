/* 作者 何磊
 * 日期 2015-6-23
 * 版本 1.0
 * 描述：主要是简化几个图表中的数字和全路径的映射
 * 
 * */
package cn.seu.edu.complexityevaluator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import cn.seu.edu.complexityevaluator.chart.classChart.mccChart.SeriesClassMccChart;

public class Mapping {
	public void classMapped(Map map, String chartName, Table table) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = (Entry<Integer, String>) it.next();
			// 拿到路径
			int num = entry.getKey();
			String  path = entry.getValue();
			String fileName = path.split("\\|")[0];
			String packName = path.split("\\|")[1];
			String className = path.split("\\|")[2];
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { chartName, num + "", fileName,
					packName, className });
		}
	}

	public void methodMapped(Map map, String chartName, Table table) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = (Entry<Integer, String>) it.next();
			int num = entry.getKey();
			String path = entry.getValue();
			String fileName = path.split("\\|")[0];
			String packName = path.split("\\|")[1];
			String className = path.split("\\|")[2];
			String methodName = path.split("\\|")[3];
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] { chartName, num + "", fileName,
					packName, className, methodName });
		}
	}
}

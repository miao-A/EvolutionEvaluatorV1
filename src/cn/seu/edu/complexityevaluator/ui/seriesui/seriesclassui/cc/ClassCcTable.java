﻿package cn.seu.edu.complexityevaluator.ui.seriesui.seriesclassui.cc;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
public class ClassCcTable extends ScrolledComposite {
	private static Table table;
	public static Table getTable() {
		return table;
	}
	private TableColumn chartName;
	private TableColumn chartNum;
	private TableColumn fileName;
	private TableColumn packName;
	private TableColumn className;
	public ClassCcTable(Composite parent, int style) {
		super(parent, style);

		
		this.setExpandHorizontal(true);
		this.setExpandVertical(true);

		table = new Table(this, SWT.FULL_SELECTION);
		table.setBounds(0, 0, 671, 388);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		chartName = new TableColumn(table, SWT.NONE);
		chartName.setWidth(112);
		chartName.setText("所属图表名");

		chartNum = new TableColumn(table, SWT.NONE);
		chartNum.setWidth(62);
		chartNum.setText("编号");

		fileName = new TableColumn(table, SWT.NONE);
		fileName.setWidth(135);
		fileName.setText("所属文件名");

		packName = new TableColumn(table, SWT.NONE);
		packName.setWidth(122);
		packName.setText("所属包名");

		className = new TableColumn(table, SWT.NONE);
		className.setWidth(175);
		className.setText("类名");
		this.setContent(table);
		this.setMinSize(table
				.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}
}

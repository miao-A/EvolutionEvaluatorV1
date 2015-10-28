package cn.seu.edu.complexityevaluator.ui.seriesui.seriesmethodui.halstead;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class MethodHsdTable extends ScrolledComposite {
	private static Table table;
	public static Table getTable() {
		return table;
	}

	private TableColumn chartName;
	private TableColumn chartNum;
	private TableColumn fileName;
	private TableColumn packName;
	private TableColumn className;
	private TableColumn methodName;
	public MethodHsdTable(Composite parent, int style) {
		super(parent, style);

		
		this.setExpandHorizontal(true);
		this.setExpandVertical(true);

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 702, 394);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		chartName = new TableColumn(table, SWT.NONE);
		chartName.setWidth(112);
		chartName.setText("所属图表名");

		chartNum = new TableColumn(table, SWT.NONE);
		chartNum.setWidth(62);
		chartNum.setText("编号");

		fileName = new TableColumn(table, SWT.NONE);
		fileName.setWidth(113);
		fileName.setText("所属文件名");

		packName = new TableColumn(table, SWT.NONE);
		packName.setWidth(122);
		packName.setText("所属包名");

		className = new TableColumn(table, SWT.NONE);
		className.setWidth(109);
		className.setText("类名");
		
		methodName = new TableColumn(table, SWT.NONE);
		methodName.setWidth(165);
		methodName.setText("方法名");
		
		this.setContent(table);
		this.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents submethoding of SWT components
	}
}
